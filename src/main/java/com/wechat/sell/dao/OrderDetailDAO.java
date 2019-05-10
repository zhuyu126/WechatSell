package com.wechat.sell.dao;

import com.wechat.sell.enity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDAO extends JpaRepository<OrderDetail,String> {
    List<OrderDetail>findByOrderId(String orderId);
}
