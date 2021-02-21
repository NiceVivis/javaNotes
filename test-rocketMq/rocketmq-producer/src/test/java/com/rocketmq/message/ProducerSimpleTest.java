package com.rocketmq.message;

import com.rocketmq.Model.OrderExt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerSimpleTest {

    @Autowired
    ProducerSimple producerSimple;

    @Test
    public void testSendSyncMsg(){
        producerSimple.sendSyncMsg("my-topic","发送第一条同步消息");
    }

    @Test
    public void testSendAsyncMsg(){
        producerSimple.sendAsyncMsg("my-topic","发送第一条异步消息");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendByJson(){
        OrderExt orderExt = new OrderExt();
        orderExt.setId("1");
        orderExt.setCreateTime(new Date());
        orderExt.setMoney(100L);
        orderExt.setTitle("消息");
        producerSimple.sendMsgByJson("my-topic-obj",orderExt);
        try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
         }
    }

    @Test
    public void sendMsgByJsonDely(){
        OrderExt orderExt = new OrderExt();
        orderExt.setId("1");
        orderExt.setCreateTime(new Date());
        orderExt.setMoney(100L);
        orderExt.setTitle("消息");
        producerSimple.sendMsgByJsonDely("my-topic-obj",orderExt);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
