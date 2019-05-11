package com.wechat.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW(0,"新订单"),
    FINISHED(1,"已完成订单"),
    CANCEL(2,"已取消订单"),
    ;
    private Integer code;
    private String msg;


    OrderStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
