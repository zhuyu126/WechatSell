package com.wechat.sell.service.impl;

import com.wechat.sell.dto.OrderDTO;
import com.wechat.sell.service.OrderService;
import com.wechat.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO=orderService.findOne("1557553790691868906");
        payService.create(orderDTO);
    }
    @Test
    public void refund() {
        OrderDTO orderDTO=orderService.findOne("1557553790691868906");

        payService.refund(orderDTO);
    }
}