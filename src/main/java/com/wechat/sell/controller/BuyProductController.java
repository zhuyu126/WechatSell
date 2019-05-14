package com.wechat.sell.controller;

import com.wechat.sell.VO.ProductInfoVO;
import com.wechat.sell.VO.ProductVO;
import com.wechat.sell.VO.ResultVO;
import com.wechat.sell.enity.ProductCategory;
import com.wechat.sell.enity.ProductInfo;
import com.wechat.sell.service.CategoryService;
import com.wechat.sell.service.ProductService;
import com.wechat.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyProductController {

    @Autowired
    private ProductService productService ;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key = "#sellerId",condition = "#sellerId.length()>3",unless = "#result.getCode()!=0")
    public ResultVO list(String sellerId){

        //1、查询所有上架商品
        List<ProductInfo> productInfoList=productService.findUpAll();
        //2、查询类目(一次查询)
        //传统模式
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for (ProductInfo productInfo : productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //lambda+collections(精简代码)
        List<Integer> categoryTypeList = productInfoList.stream().map(e->e.getCategoryType()).collect( Collectors.toList());

        List<ProductCategory>productCategoryList=categoryService.findByCategoryTypeIn(categoryTypeList);

        //3、数据拼装
        List<ProductVO> productVOList=new ArrayList<>();
        for(ProductCategory productCategory:productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO>productInfoVOList=new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        //ResultVO resultVO=new ResultVO();
        //ProductVO productVO=new ProductVO();
        //ProductInfoVO productInfoVO=new ProductInfoVO();
        //resultVO.setData(productVOList);
        //productVO.setProductInfoVOList(Arrays.asList(productInfoVO));

        //resultVO.setCode(0);
        //resultVO.setMsg("成功");

        return ResultVOUtil.success(productVOList);
    }
}
