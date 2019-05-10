package com.wechat.sell.service.impl;

import com.wechat.sell.dao.OrderDetailDAO;
import com.wechat.sell.dao.OrderMasterDAO;
import com.wechat.sell.dto.CartDTO;
import com.wechat.sell.dto.OrderDTO;
import com.wechat.sell.enity.OrderDetail;
import com.wechat.sell.enity.OrderMaster;
import com.wechat.sell.enity.ProductInfo;
import com.wechat.sell.enums.OrderStatus;
import com.wechat.sell.enums.PayStatus;
import com.wechat.sell.service.OrderService;
import com.wechat.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private OrderMasterDAO orderMasterDAO;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount=new BigDecimal(0);
        String orderId="";
        //1、查询商品(数量、价格)
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo=productService.findOne(orderDetail.getProductId());
            if(productInfo==null){
                //商品不存在
            }
            //2、计算订单价格
            orderAmount=productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            //订单详情
            orderDetail.setDetailId("");
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailDAO.save(orderDetail);
        }

        //3、写入订单数据
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("");
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus( OrderStatus.NEW.getCode());
        orderMaster.setPayStatus( PayStatus.WAIT.getCode());
        orderMasterDAO.save(orderMaster);
        //4、扣库存
        List<CartDTO>cartDTOList=orderDTO.getOrderDetailList().stream().map(e->new CartDTO(e.getProductId(),e.getProductQuantity())).collect( Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster=orderMasterDAO.findOne(orderId);
        if(orderMaster==null){
            //不存在
        }
        List<OrderDetail>orderDetailList=orderDetailDAO.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            //商品不存在
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster>orderMasterPage=orderMasterDAO.findByBuyerOpenid(buyerOpenid, pageable);
        //List<OrderDTO>orderDTOList=OrderMaster2OrderDTOConverter.convert();
        List<OrderDTO>orderDTOList=orderMasterPage.getContent().stream().map(e->new OrderDTO()).collect( Collectors.toList());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatus.NEW.getCode())){
            //订单状态不可改
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatus.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDAO.save( orderMaster);
        if(updateResult==null){
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
        }
        //返回库存
        //库存要加
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
        }
        List<CartDTO>cartDTOList=orderDTO.getOrderDetailList().stream().map(e->new CartDTO(e.getProductId(),e.getProductQuantity())).collect( Collectors.toList());
        //加库存
        productService.increaseStock(cartDTOList);

        //如果已支付, 需要退款
        if(orderDTO.getPayStatus().equals(PayStatus.SUCCESS)){
            //todo 退款
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finished(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatus.NEW.getCode())){
            //订单状态不可改
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatus.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDAO.save( orderMaster);
        if(updateResult==null){
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        if (!orderDTO.getOrderStatus().equals(OrderStatus.NEW.getCode())){
            //订单状态不可改
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
        }
        if (!orderDTO.getPayStatus().equals(PayStatus.WAIT.getCode())){
            //订单状态不可改
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
        }
        //修改支付状态
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setPayStatus(PayStatus.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDAO.save( orderMaster);
        if(updateResult==null){
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
        }

        return orderDTO;
    }
}
