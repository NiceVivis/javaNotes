package com.vivi.message.RocketMq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 发送延时消息
 * 1、验证
 * 你将会看到消息的消费比存储时间晚10s
 * 2、延时消息的使用场景
 * 比如电商里，提交了一个订单就可以发送一个延时消息，1h后去检查这个订单的状态，如果还是未付款就取消订单释放库存
 * 3、延时消息的使用限制
 * private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
 * 现在rocketMq并不支持任意时间的延迟，需要设置几个固定的延时等级，从1s到2h分别对应着等级1到18消息失败会进入延时消息队列
 */
public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception{
        //实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer("ExampleConsumer");
        //启动生产者
        producer.start();
        int totalMessagesToSend = 100;
        for (int i = 0;i<totalMessagesToSend;i++){
            Message message = new Message("TestTopic",("Hello scheduled messge"+i).getBytes());
            //设置延时等级3，这个消息将在10s之后发送（现在只支持固定的几个时间）
            message.setDelayTimeLevel(3);
            //发送消息
            producer.send(message);
        }
        //关闭生产者
        producer.shutdown();
    }
}
