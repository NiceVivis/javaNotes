package com.vivi.message.RocketMq;

import com.vivi.message.RocketMq.RocketProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class MQController {

    @Autowired
    private RocketProducer rocketProducer;

    @RequestMapping("/myFirstProducer")
    public String pushMsg(String msg){
        System.out.println("===="+msg);
        try {
            return rocketProducer.send("firstTopic","push",msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}
