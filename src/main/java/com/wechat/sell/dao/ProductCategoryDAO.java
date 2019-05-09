package com.wechat.sell.dao;

import com.wechat.sell.enity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory,Integer> {
    List<ProductCategory>findByCategoryTypeIn(List<Integer> categoryTypeList);
}
