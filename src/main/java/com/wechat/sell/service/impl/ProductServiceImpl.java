package com.wechat.sell.service.impl;

import com.wechat.sell.dao.ProductInfoDAO;
import com.wechat.sell.dto.CartDTO;
import com.wechat.sell.enity.ProductInfo;
import com.wechat.sell.enums.ProductStatusEnum;
import com.wechat.sell.enums.ResultEnum;
import com.wechat.sell.exception.SellException;
import com.wechat.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoDAO productInfoDAO;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoDAO.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDAO.findByProductStatus( ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDAO.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDAO.save(productInfo);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=productInfoDAO.findOne(cartDTO.getProductId());
            if(productInfo==null){
                //没有该商品
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //减库存
            Integer result=productInfo.getProductStock()-cartDTO.getProductQuantity();
            if(result<0){
                //商品库存不足
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoDAO.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=productInfoDAO.findOne(cartDTO.getProductId());
            if(productInfo==null){
                //没有该商品
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoDAO.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo=productInfoDAO.findOne(productId);
        if(productId==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoDAO.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo=productInfoDAO.findOne(productId);
        if(productId==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoDAO.save(productInfo);
    }


}
