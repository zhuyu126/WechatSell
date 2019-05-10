package com.wechat.sell.enums;

public enum OrderStatus {
    NEW(0,"新订单"),
    FINISHED(1,"已完成订单"),
    CANCEL(2,"已取消订单");
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    OrderStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
