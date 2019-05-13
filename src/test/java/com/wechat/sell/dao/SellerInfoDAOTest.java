package com.wechat.sell.dao;

import com.wechat.sell.enity.SellerInfo;
import com.wechat.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDAOTest {
    @Autowired
    private SellerInfoDAO sellerInfoDAO;

    @Test
    public void save(){
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo result = sellerInfoDAO.save(sellerInfo);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByOpenid() {
        SellerInfo result = sellerInfoDAO.findByOpenid("abc");
        Assert.assertEquals("abc",result.getOpenid());

    }
}