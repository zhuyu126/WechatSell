package com.wechat.sell.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wechat.sell.dto.OrderDTO;
import com.wechat.sell.enity.OrderDetail;
import com.wechat.sell.enums.ResultEnum;
import com.wechat.sell.exception.SellException;
import com.wechat.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson=new Gson();//处理前端传来的json格式的集合
        OrderDTO orderDTO=new OrderDTO();


        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList=new ArrayList<>();

        try{
            orderDetailList=gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}
                    .getType());

        }catch (Exception e){
            log.error("【对象转化】错误，String={}",orderForm.getItems());
            throw new SellException( ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

}
