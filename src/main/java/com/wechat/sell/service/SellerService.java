package com.wechat.sell.service;

import com.wechat.sell.enity.SellerInfo;

public interface SellerService {
    /**
     * 通过openid查询卖家信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
