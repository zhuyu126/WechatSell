package com.wechat.sell.controller;

import com.wechat.sell.VO.ResultVO;
import com.wechat.sell.convert.OrderForm2OrderDTOConverter;
import com.wechat.sell.dto.OrderDTO;
import com.wechat.sell.enums.ResultEnum;
import com.wechat.sell.exception.SellException;
import com.wechat.sell.form.OrderForm;
import com.wechat.sell.service.BuyerService;
import com.wechat.sell.service.OrderService;
import com.wechat.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
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
            throw new SellException( ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //前端传值，后端接受拼接订单信息
        OrderDTO orderDTO= OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult=orderService.create(orderDTO);

        Map<String,String>map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>>list(@RequestParam("openid") String openid,
                                        @RequestParam(value = "page",defaultValue = "0")Integer page,
                                        @RequestParam(value = "size",defaultValue = "10")Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request=new PageRequest(page,size);
        Page<OrderDTO>orderDTOPage=orderService.findList(openid,request);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO>detail(@RequestParam("openid")String openid,
                                    @RequestParam("orderId")String orderId){
        //不安全做法存在横向越权，需要改进
        //OrderDTO orderDTO=orderService.findOne(orderId);
        //用户判断，杜绝横向越权
        OrderDTO orderDTO=buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @GetMapping("/cancel")
    public ResultVO<OrderDTO>cancel(@RequestParam("openid")String openid,
                                    @RequestParam("orderId")String orderId){
        //不安全做法存在横向越权，需要改进
//        OrderDTO orderDTO=orderService.findOne(orderId);
//        orderService.cancel(orderDTO);
        //用户判断，杜绝横向越权
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
