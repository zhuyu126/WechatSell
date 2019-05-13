package com.wechat.sell.dao;

import com.wechat.sell.enity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoDAO extends JpaRepository<SellerInfo,String> {
    SellerInfo findByOpenid(String openid);
}
