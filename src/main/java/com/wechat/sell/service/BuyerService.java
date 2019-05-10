package com.wechat.sell.service;

import com.wechat.sell.dto.OrderDTO;

public interface BuyerService {
    OrderDTO findOrderOne(String openid,String orderId);

    OrderDTO cancelOrder(String openid,String orderId);
}
