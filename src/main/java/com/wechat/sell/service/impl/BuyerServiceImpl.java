package com.wechat.sell.service.impl;

import com.wechat.sell.dto.OrderDTO;
import com.wechat.sell.service.BuyerService;
import com.wechat.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;
    //用户验证
    private OrderDTO checkOrderOwner(String openid,String orderId){
        OrderDTO orderDTO=orderService.findOne(orderId);
        if(orderDTO==null){
            return null;
        }
        //判断订单所属
        if(!orderDTO.getBuyerOpenid().equals(openid)){
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
        }
        return orderDTO;
    }


    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO=checkOrderOwner(openid,orderId);
        if(orderDTO==null){
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
        }
        return orderService.cancel(orderDTO);
    }
}
