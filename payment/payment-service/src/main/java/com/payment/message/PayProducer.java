package com.payment.message;

import com.payment.api.dto.AliConfigParam;
import com.payment.api.dto.PaymentResponseDTO;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class PayProducer {

    //订单结果查询主题
    private static final String TOPIC_ORDER = "TP_PAYMENT_ORDER";

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    //发送消息 查询支付宝订单状态
    public void payOrderNotice(PaymentResponseDTO notice) {
        //发送延迟消息
        Message<PaymentResponseDTO> build = MessageBuilder.withPayload(notice).build();
        //延迟第三级发送(延迟10s)
        rocketMQTemplate.syncSend(TOPIC_ORDER,build,1000,3);
    }
}
