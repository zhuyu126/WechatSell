package com.wechat.sell.utils;

public class MathUtil {

    private static final Double MONEY_PANGE=0.01;
    /**
     * 比较两个金额是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1,Double d2){
        Double result = Math.abs( d1 - d2 );
        if(result<MONEY_PANGE){
            return true;
        }else {
            return false;
        }
    }
}
