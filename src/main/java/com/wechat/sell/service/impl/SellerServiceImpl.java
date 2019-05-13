package com.wechat.sell.service.impl;

import com.wechat.sell.dao.SellerInfoDAO;
import com.wechat.sell.enity.SellerInfo;
import com.wechat.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoDAO sellerInfoDAO;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoDAO.findByOpenid(openid);
    }
}
