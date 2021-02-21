package com.vivi.message.RocketMq.producer;

import com.vivi.message.RocketMq.OrderStep;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息有序的是可以按照消息的发送顺序来消费（FIFO），Rocket可以严格的保证消息有序，可以分为分区有序或者全局有序。
 * 顺序消费的原理：
 * 在默认情况下消息发送会采取Round Robin轮询方法把消息发送到不同的quenue（分区队列）;而消费消息的时候从多个queue上拉取消息，
 * 这种情况发送和消费是不能保证顺序。但是如果控制发送的顺序消息只依次发送到同一个queue中，消息的时候只从这个queue上依次拉取，
 * 则就保证了顺序。当发送和消费参与的queue只有一个，则是全局有序；如果多个queue参与，则为分区有序，即相对每个queue，消息
 * 都是有序的。
 * eg：我们用订单分区有序的示例，一个订单的顺序流程是：创建、付款、推送、完成。订单号相同的消息会被先后发送到同一个队列中，
 * 消费时，同一个orderId获取到的肯定是同一个队列
 */
public class FifoProducer {

//    public static void main(String[] args) throws Exception {
//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        producer.start();
//        String[] tags = new String[]{"TagA","TagC","TagD"};
//
//        List<OrderStep> orderList = new FifoProducer().buildOrders();
//
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateStr = sdf.format(date);
//
//        for (int i=0;i<10;i++){
//            //加上时间前缀
//            String body = dateStr+"Hello RocketMQ"+orderList.get(i);
//            Message msg = new Message("TopicTest",tags[i%tags.length],"KEY"+i,body.getBytes());
//            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
//                @Override
//                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
//                    Long id = (Long) o;   //根据订单id选择发送queue
//                    long index = id % list.size();
//                    return list.get((int) index);
//                }
//            },orderList.get(i).getOrderId());//订单Id
//            System.out.printf(String.format("SendResult status:%s,queueId:%d,body:%s",
//                    sendResult.getSendStatus(),
//                    sendResult.getMessageQueue().getQueueId()),
//                    body);
//        }
//    }
//
//    private List<OrderStep> buildOrders() {
//        List<OrderStep> orderList = new ArrayList<>();
//
//        OrderStep orderDemo = new OrderStep();
//        orderDemo.setOrderId(15103111039L);
//        orderDemo.setDesc("创建");
//        orderList.add(orderDemo);
//
//        orderDemo = new OrderStep();
//        orderDemo.setOrderId(15103111065L);
//        orderDemo.setDesc("创建");
//        orderList.add(orderDemo);
//
//        orderDemo = new OrderStep();
//        orderDemo.setOrderId(15103111039L);
//        orderDemo.setDesc("付款");
//        orderList.add(orderDemo);
//
//        orderDemo = new OrderStep();
//        orderDemo.setOrderId(15103117235L);
//        orderDemo.setDesc("创建");
//        orderList.add(orderDemo);
//
//        orderDemo = new OrderStep();
//        orderDemo.setOrderId(15103111065L);
//        orderDemo.setDesc("付款");
//        orderList.add(orderDemo);
//
//        orderDemo = new OrderStep();
//        orderDemo.setOrderId(15103117235L);
//        orderDemo.setDesc("付款");
//        orderList.add(orderDemo);
//
//        orderDemo = new OrderStep();
//        orderDemo.setOrderId(15103111065L);
//        orderDemo.setDesc("完成");
//        orderList.add(orderDemo);
//
//        orderDemo = new OrderStep();
//        orderDemo.setOrderId(15103111039L);
//        orderDemo.setDesc("推送");
//        orderList.add(orderDemo);
//
//        orderDemo = new OrderStep();
//        orderDemo.setOrderId(15103117235L);
//        orderDemo.setDesc("完成");
//        orderList.add(orderDemo);
//
//        orderDemo = new OrderStep();
//        orderDemo.setOrderId(15103111039L);
//        orderDemo.setDesc("完成");
//        orderList.add(orderDemo);
//
//        return orderList;
//    }
}
