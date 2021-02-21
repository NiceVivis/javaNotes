package com.vivi.message.RocketMq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.UnsupportedEncodingException;

@Component
public class RocketProducer {

    /**
     * 生产者的组名
     */
    @Value("${apache.rocketmq.producer.producerGroup}")
    private String producerGroup;
    /**
     * Nameserver地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    private DefaultMQProducer defaultMQProducer;

    public void DefaultMQProducer(){
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.setVipChannelEnabled(false);
        try {
            defaultMQProducer.start();
            System.out.printf("producer启动了");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public String send(String topic,String tags,String body) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message(topic,tags,body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        SendResult result = defaultMQProducer.send(message);
        System.out.println("发送响应：MSgId:"+result.getMsgId()+"发送状态"+result.getSendStatus());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgId",result.getMsgId());
        jsonObject.put("sendStatus",result.getSendStatus());
        stopWatch.stop();
        return jsonObject.toJSONString();
    }
}
