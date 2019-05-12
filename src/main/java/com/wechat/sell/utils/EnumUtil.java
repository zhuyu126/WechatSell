package com.wechat.sell.utils;

import com.wechat.sell.enums.CodeEnum;
import com.wechat.sell.enums.OrderStatus;

public class EnumUtil {
    public static <T extends CodeEnum>T getByCode(Integer code, Class<T> enumClass){
        for(T each:enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
