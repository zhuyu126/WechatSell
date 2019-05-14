package com.wechat.sell.exception;

import com.wechat.sell.enums.ResultEnum;
import lombok.Data;

@Data
public class SellException extends RuntimeException{

    private Integer code;
    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();
    }
    public SellException(Integer code,String message){
        super(message);
        this.code=code;
    }
}
