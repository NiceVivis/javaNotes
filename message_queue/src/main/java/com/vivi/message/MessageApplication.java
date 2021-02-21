package com.vivi.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 一、消息中间有什么用
 *   1、系统解耦
 *   2、异步
 *   3、削锋
 *二、JMS
 *    java Message Service java消息服务。
 *    是一个java平台中关于面向消息中间件的API，用于在两个应用程序之间，或分布式系统中发送消息，
 *    进行异步通信
 *三、AMQP
 *   提供同一消息服务的应用层标准协议，遵循这个协议客户端与消息中间件可以传递消息。
 *
 *四、四种消息队列
 *   1、ActiveMQ完全支持JMS
 *   2、RabbitMQ是一个开源的AMQP实现，
 *   3、kafka是一种高吞吐量的分布式发布订阅消息系统
 */
@SpringBootApplication
public class MessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class);
    }
}
