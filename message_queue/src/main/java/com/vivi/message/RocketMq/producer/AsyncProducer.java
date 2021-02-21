package com.vivi.message.RocketMq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import java.util.concurrent.CountDownLatch;

/**
 * 2、异步发送消息
 * 异步传输常用于响应时间敏感的业务场景中
 */
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("39.106.148.147:9876");

        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 100;
        //根据消息数量实例化倒计时计算器
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0;i<messageCount;i++){
            try {
                final int index = i;
                //创建消息，并指定Topic，Tag和消息体
                Message msg = new Message("Jodie_topic_1023",
                        "TagA",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                //sendCallBack接收异步返回结果的回调
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                },10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        countDownLatch.await();
        producer.shutdown();

    }
}
