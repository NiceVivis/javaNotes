package com.vivi.message.RocketMq;

import lombok.Data;

@Data
public class OrderStep {

    private long orderId;

    private String desc;
}
