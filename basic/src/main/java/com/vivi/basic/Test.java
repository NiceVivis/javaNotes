package com.vivi.basic;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import cn.hutool.core.codec.Base64;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yangwei
 * @date 2020/10/21 8:09 下午
 */
public class Test {

    public static void main(String[] args) {
        String client_secret = "a0c2d307c86f1143514817a458852709497f";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = UUID.randomUUID().toString();
        String path = "/openapi/volvo-online-activity/api/services/app/createDriveTestLogs.json";
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("mobile", "18793447673");
        queryParamsMap.put("callState", "2");
        String signature = sign(client_secret, timestamp, nonce, path, queryParamsMap);
        System.out.println("signature:" + signature);
    }

    public static String sign(String clientSecret, String timestamp, String nonce, String path, Map<String, String> queryParamsMap) {
        // sort
        List<String> queryList = queryParamsMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.toList());
        System.out.println("origin query string: " + queryList);
        Collections.sort(queryList);
        String queryStrSorted = queryList.stream().collect(Collectors.joining("&"));
        System.out.println("sorted query string: " + queryStrSorted);


        // message
        String message = path + queryStrSorted;
        System.out.println("message:" + message);


        // encrypt 对client_secret用sha256摘要得到的结果的前32个字符(256bit)作为aes加密的key
        // 测试地址 http://tool.chacuo.net/cryptaes
        String aesKey = StringUtils.left(DigestUtil.sha256Hex(clientSecret), 32);
        System.out.println("aes key:" + aesKey);
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, aesKey.getBytes());
        String encrypt = Base64.encode(aes.encrypt(message));
        System.out.println("encrypt:" + encrypt);


        // signature
        ArrayList<String> listToSign = Lists.newArrayList(clientSecret, timestamp, nonce, encrypt);
        Collections.sort(listToSign);
        System.out.println("listToSign sorted: " + listToSign);
        String strToSign = listToSign.stream().collect(Collectors.joining(""));
        System.out.println("strToSign:" + strToSign);
        String signature = DigestUtil.sha256Hex(strToSign);

        return signature;
    }
}
