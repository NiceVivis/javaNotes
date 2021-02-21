package com.payment.api;

import com.payment.api.dto.AliConfigParam;
import com.payment.api.dto.AlipayBean;
import com.payment.api.dto.PaymentResponseDTO;

/**
 * 与第三方支付渠道进行交付
 */
public interface PayChannelService {

     PaymentResponseDTO createPayOrderByAliWAP(AliConfigParam aliConfigParam, AlipayBean alipayBean);

        /**
         * 查询支付订单状态
         * @param aliConfigParam 支付渠道参数
         * @param outTradeNo 订单号
         * @return
         */
    PaymentResponseDTO queryPayOrderByAli(AliConfigParam aliConfigParam, String outTradeNo);
}
