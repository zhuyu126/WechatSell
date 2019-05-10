package com.wechat.sell.enums;

public enum PayStatus {
    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功");
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

    PayStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
