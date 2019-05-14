package com.wechat.sell.dao;

import com.wechat.sell.enity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDAOTest {

    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Test
    public void findOneTest(){
        ProductCategory productCategory=productCategoryDAO.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("男生最爱", 4);
        ProductCategory result = productCategoryDAO.save(productCategory);
        Assert.assertNotNull(result);
        //Assert.assertNotEquals(null, result);
    }

    @Test
    @Transactional//完全回滚
    public void updateTest() {
        ProductCategory productCategory = productCategoryDAO.findById(1).orElse(null);
        productCategory.setCategoryName("男生最爱");
        productCategoryDAO.save(productCategory);

    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(2,3,4);

        List<ProductCategory> result = productCategoryDAO.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());
    }
}