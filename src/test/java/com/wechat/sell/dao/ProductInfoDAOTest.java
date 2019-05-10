package com.wechat.sell.dao;

import com.wechat.sell.enity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDAOTest {

    @Autowired
    private ProductInfoDAO productInfoDAO;

    @Test
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(4));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好喝的粥");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo result=productInfoDAO.save(productInfo);
        Assert.assertNotNull(result);


    }


    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = productInfoDAO.findByProductStatus(0);
        Assert.assertNotEquals(0, productInfoList.size());
    }
}