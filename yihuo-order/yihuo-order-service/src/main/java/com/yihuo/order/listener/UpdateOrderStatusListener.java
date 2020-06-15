package com.yihuo.order.listener;


import com.yihuo.order.mapper.OrderStatusMapper;
import com.yihuo.order.pojo.OrderStatus;
import com.yihuo.order.service.OrderService;
import com.yihuo.order.service.OrderStatusService;
import com.yihuo.order.vo.OrderStatusMessage;
import com.yihuo.utils.JsonUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: 98050
 * @Time: 2018-12-10 23:12
 * @Feature: 自动修改订单状态：自动确认收货，自动评价
 */
@Component
public class UpdateOrderStatusListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStatusService orderStatusService;


    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "yihuo.order.delay.ttl.queue",durable = "true"), //队列持久化
            exchange = @Exchange(
                    value = "yihuo.amq.direct",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"yihuo_ttl_orderStatus"}
    ))
    public void listenOrderDelayMessage(byte[] str){
        OrderStatusMessage orderStatusMessage = JsonUtils.parse(new String(str), OrderStatusMessage.class);
        if (orderStatusMessage == null){
            return;
        }
        int type = orderStatusMessage.getType();

        if (type == 1){
            //自动确认收货，时间为7天

            //1.查询当前订单状态
            int status = orderService.queryOrderStatusById(orderStatusMessage.getOrderId()).getStatus();
            int nowStatus = 4;
            if (status + 1 == nowStatus){
                //2.修改订单状态
                updateOrderStatusDelay(orderStatusMessage.getOrderId(), nowStatus);

            }
        }else {
            //自动好评，时间为5天
            //1.查询当前订单状态
            int status = orderService.queryOrderStatusById(orderStatusMessage.getOrderId()).getStatus();
            int nowStatus = 6;
            if (status + 2 != nowStatus){
                return;
            }
            //2.修改订单状态
            updateOrderStatusDelay(orderStatusMessage.getOrderId(), nowStatus);
            //3.发送评论消息

        }
    }




    private void updateOrderStatusDelay(Long orderId, int nowStatus) {

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus(nowStatus);
        if (nowStatus == 4){
            orderStatus.setEndTime(new Date());
        }
        if (nowStatus == 6){
            orderStatus.setCommentTime(new Date());
        }

        this.orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }


}
