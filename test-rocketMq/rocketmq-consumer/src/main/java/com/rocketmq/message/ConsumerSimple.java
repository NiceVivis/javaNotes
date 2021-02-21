package com.rocketmq.message;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "my-topic",consumerGroup = "demo-producer-group")
public class ConsumerSimple implements RocketMQListener<String> {

    /**
     * 获取消息
     * @param msg
     */
    @Override
    public void onMessage(String msg) {
        //此方法被调用表示接收到消息，msg形参就是消息内容
        System.out.println(msg);
    }

}
