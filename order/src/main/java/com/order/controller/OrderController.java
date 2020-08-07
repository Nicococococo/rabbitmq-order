package com.order.controller;

import com.alibaba.fastjson.JSON;
import com.order.dao.entity.OrderInfo;
import com.order.dao.mapper.OrderInfoMapper;
import com.order.utils.IdWorker;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private OrderInfoMapper orderInfoMapper;


    /**
     * 提交订单
     * @return
     */
    @RequestMapping("/submit")
    public Integer submitOrder() {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStatus(0);
        orderInfoMapper.insertSelective(orderInfo);

        rabbitTemplate.convertAndSend("ex.order", "order", orderInfo.getId());

        return orderInfo.getId();
    }

    /**
     * 支付
     * @return
     */
    @RequestMapping("/pay/{id}")
    public Integer pay(@PathVariable Integer id) {

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(id);

        //订单过期
        if(orderInfo.getStatus().equals(2)){
            //跳转用户历史账单中查看因付款超时而取消的订单
            return 2;
        }else{
            orderInfo.setStatus(1);
            orderInfoMapper.updateByPrimaryKey(orderInfo);
            //跳转到付款成功页面
            return 1;

        }
    }

    @RequestMapping("/orderList")
    public List<OrderInfo> orderList() {

        List<OrderInfo> orderInfos = orderInfoMapper.selectAll();

        return orderInfos;
    }

}
