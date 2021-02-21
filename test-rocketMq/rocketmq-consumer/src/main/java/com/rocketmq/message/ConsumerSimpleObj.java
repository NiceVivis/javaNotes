package com.rocketmq.message;

import com.rocketmq.model.OrderExt;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "my-topic-obj",consumerGroup = "demo-producer-group-obj")
public class ConsumerSimpleObj implements RocketMQListener<MessageExt> {

    /**
     * 获取对象消息
     * @param messageExt
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();
        String jsonString = new String(body);
        System.out.println(jsonString);

        //取出当前重试次数
        int sumTimes = messageExt.getReconsumeTimes();

        //当大于一定次数后将消息写入数据库，由单独的成功或者人工处理
        if (sumTimes > 2){

        }
    }

}
