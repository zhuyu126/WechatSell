package com.wechat.sell.dao;

import com.wechat.sell.enity.OrderDetail;
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
public class OrderDetailDAOTest {

    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Test
    public void saveTest(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("1234567890");
        orderDetail.setOrderId("1111123");
        orderDetail.setProductId("1111112");
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductName("槐花饮品");
        orderDetail.setProductPrice(new BigDecimal(11));
        orderDetail.setProductQuantity(10);

        OrderDetail result=orderDetailDAO.save(orderDetail);

        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail>orderDetailList=orderDetailDAO.findByOrderId("1111123");
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}