package com.order.listener;

import com.alibaba.fastjson.JSON;
import com.order.dao.entity.OrderInfo;
import com.order.dao.mapper.OrderInfoMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class TimeoutOrderListener {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @RabbitListener(queues = "q.order.dlx")
    public void onMessage(String orderId)  {

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(Integer.valueOf(orderId));

        //未支付
        if(orderInfo.getStatus().equals(0)) {
            orderInfo.setStatus(2);
            orderInfoMapper.updateByPrimaryKey(orderInfo);
        }

    }

}

