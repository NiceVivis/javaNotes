package com.vivi.message.RocketMq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 1、同步发送消息
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        //声明并初始化一个producer
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        //设置NameServer地址，多个地址之间用;
        producer.setNamesrvAddr("39.106.148.147:9876");
        //启动
        producer.start();
        //发送消息到Topic为TopicTest，tag为TagA，消息内容为(Hello RockrtMQ)+i的值
        for (int i =0;i<100;i++){
            Message msg = new Message("SynTopicTest","TagA",("Hello RockrtMQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //调用的是同步send方法，会有返回值
            SendResult sendResult = producer.send(msg);
            //打印返回结果
            System.out.printf("%s%n",sendResult);
        }
        producer.shutdown();
    }
}
