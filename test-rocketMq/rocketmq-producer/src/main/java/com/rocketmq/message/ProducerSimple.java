package com.rocketmq.message;

import com.alibaba.fastjson.JSONObject;
import com.rocketmq.Model.OrderExt;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProducerSimple {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    /**
     *     发送同步消息，syncSend会有返回值。
     */
    public void sendSyncMsg(String topic,String msg){
        rocketMQTemplate.syncSend(topic,msg);
    }

    /**
     * 发送异步消息，syncSend执行后不会有返回值，需要通过回调方法获取执行的结果
     * @param topic
     * @param msg
     */
    public void sendAsyncMsg(String topic,String msg){
        rocketMQTemplate.syncSend(topic, new SendCallback() {
            //消息成功的回调
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }

            //　消息失败的回调
            @Override
            public void onException(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

    /**
     * 发送单向消息
     * @param topic
     * @param msg
     */
    public void sendOneWayMsg(String topic,String msg){
        rocketMQTemplate.sendOneWay(topic,msg);
    }

    /**
     * 发送对象
     * @param topic
     * @param orderExt
     */
    public void sendMsgByJson(String topic, OrderExt orderExt){
        //将对象转换成JSON，有返回值，是同步方法
        rocketMQTemplate.convertAndSend(topic,orderExt);
    }

    /**
     * 发送延迟消息（同步）
     * @param topic
     * @param orderExt
     */
    public void sendMsgByJsonDely(String topic,OrderExt orderExt){
        //String destination, Message<?> message, long timeout(发送消息的超时时间，毫秒), int delayLevel(8个等级)
        Message<OrderExt> message = MessageBuilder.withPayload(orderExt).build();

        rocketMQTemplate.syncSend(topic,message,1000,3);

        System.out.printf("send msg :%s",orderExt);
    }

    /**
     * 发送延迟异步消息
     * @param topic
     * @param orderExt
     * @throws RemotingException
     * @throws MQClientException
     * @throws InterruptedException
     */
    public void sendAsyncMsgByJsonDely(String topic,OrderExt orderExt) throws RemotingException, MQClientException, InterruptedException {

        //消息内容转换成json
        org.apache.rocketmq.common.message.Message message = new org.apache.rocketmq.common.message.Message(
                topic, JSONObject.toJSONBytes(orderExt));
        //延迟等级
        message.setDelayTimeLevel(3);

        rocketMQTemplate.getProducer().send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

}
