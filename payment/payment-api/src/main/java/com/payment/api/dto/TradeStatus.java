package com.payment.api.dto;

public enum TradeStatus {
    SUCCESS, //交易成功
    FAILED,  //交易失败
    UNKNOWN,  //交易状态未知
    USERPAYING, //交易新建，等待支付
    REVOKED,   //交易已撤销
}
