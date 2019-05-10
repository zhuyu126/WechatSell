package com.wechat.sell.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wechat.sell.VO.ResultVO;
import com.wechat.sell.convert.OrderForm2OrderDTOConverter;
import com.wechat.sell.dto.OrderDTO;
import com.wechat.sell.enity.OrderDetail;
import com.wechat.sell.form.OrderForm;
import com.wechat.sell.service.BuyerService;
import com.wechat.sell.service.OrderService;
import com.wechat.sell.utils.ResultUtil;
import com.wechat.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>>create(@Valid OrderForm orderForm,
                                              BindingResult bindingResult){
        if(bindingResult.hasErrors()){//有错误信息
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);

        }
        //前端传值，后端接受拼接订单信息
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail>orderDetailList=new ArrayList<>();
        Gson gson=new Gson();//处理前端传来的json格式的集合
        orderDetailList=gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDTO>>(){}.getType());
        orderDTO.setOrderDetailList(orderDetailList);
        //orderDTO.setOrderDetailList(orderForm.getItems());
        //OrderDTO orderDTO= OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
        }
        OrderDTO createResult=orderService.create(orderDTO);
        Map<String,String>map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>>list(@RequestParam("openid") String openid,
                                        @RequestParam(value = "page",defaultValue = "0")Integer page,
                                        @RequestParam(value = "size",defaultValue = "10")Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
        }
        PageRequest request=new PageRequest(page,size);
        Page<OrderDTO>orderDTOPage=orderService.findList(openid,request);
        return ResultUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO>detail(@RequestParam("openid")String openid,
                                    @RequestParam("orderId")String orderId){
        OrderDTO orderDTO=buyerService.findOrderOne(openid,orderId);
        return ResultUtil.success(orderDTO);
    }

    //取消订单
    @GetMapping("/cancel")
    public ResultVO<OrderDTO>cancel(@RequestParam("openid")String openid,
                                    @RequestParam("orderId")String orderId){
        buyerService.cancelOrder(openid,orderId);
        return ResultUtil.success();
    }
}
