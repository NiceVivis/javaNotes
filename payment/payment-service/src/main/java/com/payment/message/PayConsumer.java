package com.payment.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.payment.api.PayChannelService;
import com.payment.api.dto.AliConfigParam;
import com.payment.api.dto.PaymentResponseDTO;
import com.payment.api.dto.TradeStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "TP_PAYMENT_ORDER",consumerGroup = "CID_PAYMENT_CONSUMER")
public class PayConsumer implements RocketMQListener<MessageExt> {
    
    @Autowired
    private PayChannelService payChannelService;
    
    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();

        String jsonString = new String(body);
        //消息转成对象
        PaymentResponseDTO paymentResponseDTO = JSON.parseObject(jsonString, PaymentResponseDTO.class);
        String outTradeNo = paymentResponseDTO.getOutTradeNo();//订单号
        String content = (String) paymentResponseDTO.getContent();
        //content转成对象
        AliConfigParam aliConfigParam = JSON.parseObject(content, AliConfigParam.class);
        PaymentResponseDTO responseDTO = null;
        if ("ALIPAY_WAP".equals(paymentResponseDTO.getMsg())) {
            //调用支付宝订单接口查询订单状态
            responseDTO = payChannelService.queryPayOrderByAli(aliConfigParam, outTradeNo);
        }else if ("WX_JSAPI".equals(paymentResponseDTO.getMsg())){
            //调用微信的接口查询订单状态
        }

        //没有获取到订单结果，抛出异常，重新消费该消息
        if (TradeStatus.UNKNOWN.equals(responseDTO.getTradeState()) || TradeStatus.USERPAYING.equals(responseDTO.getTradeState())){
            throw new RuntimeException("支付状态未知，等待重试");
        }

        //重试次数达到一定的数量，不要重试，将消息记录到数据库，由单独的程序处理
        // 将订单状态，再次发到MQ

    }
}
