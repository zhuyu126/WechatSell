package com.wechat.sell.service.impl;

import com.wechat.sell.dto.OrderDTO;
import com.wechat.sell.service.OrderService;
import com.wechat.sell.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private OrderService orderService;


    @Test
    public void orderStatus() {
        OrderDTO orderDTO = orderService.findOne( "1557553790691868906" );
        pushMessageService.orderStatus(orderDTO);
    }
}