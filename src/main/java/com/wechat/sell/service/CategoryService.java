package com.wechat.sell.service;

import com.wechat.sell.dao.ProductCategoryDAO;
import com.wechat.sell.enity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory>findByCategoryType(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
