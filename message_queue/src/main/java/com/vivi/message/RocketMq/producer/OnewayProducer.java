package com.vivi.message.RocketMq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 *3、以单向模式发送消息
 * 单向传输用于要求中等可靠性的情况，如日志收集
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0;i<10;i++){
            Message msg = new Message("TopicTest","TagA",("Hello RocketMQ"+i).getBytes(
                    RemotingHelper.DEFAULT_CHARSET));
           //发送单向消息，没有任何返回结果
            producer.sendOneway(msg);
        }
        Thread.sleep(5000);
        producer.shutdown();
    }
    }
