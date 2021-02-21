package com.vivi.message.RocketMq.ConsumerDemo;

/**
 * 顺序消息消费，带事务方式（应用可控制offset什么时候提交）
 */
public class FifoConsumer {
//    public static void main(String[] args) throws MQClientException {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");
//        consumer.setNamesrvAddr("127.0.0.1:9876");
//        /**
//         *  设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
//         *  如果非第一次启动，那么按照上次消费的位置继续消费
//         */
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        consumer.subscribe("TopicTest","TagA || TagC || TagD");
//        consumer.registerMessageListener(new MessageListenerOrderly() {
//            Random random = new Random();
//            @Override
//            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
//                consumeOrderlyContext.setAutoCommit(true);
//                for (MessageExt msg : list){
//                    //可以看到每个queue有唯一的consume线程来消费，订单对每个queue（分区）有序
//                    System.out.printf("consumeThread="+Thread.currentThread().getName()+"queueId="+msg.getQueueId());
//                }
//                try {
//                    //模拟业务逻辑处理中
//                    TimeUnit.SECONDS.sleep(random.nextInt(10));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return ConsumeOrderlyStatus.SUCCESS;
//            }
//        });
//        consumer.start();
//        System.out.printf("Consumer Started");
//    }
}
