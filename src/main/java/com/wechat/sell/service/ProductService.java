package com.wechat.sell.service;

import com.wechat.sell.enity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    /**
     * 查询在售商品
     * @return
     */
    List<ProductInfo>findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);
    //加库存

    //减库存

    //上架

    //下架
}
