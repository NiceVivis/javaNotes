package com.vivi.basic;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yangwei
 * @date 2020/10/21 9:28 下午
 */
public class SignatureUtil {
    private final static String CLIENT_ID = "6097638c230ad7480419e24b489d1bbbc781";
    private final static String SECRET_KEY = "a0c2d307c86f1143514817a458852709497f";
    private final static String PATH_LOTTERY = "/openapi/volvo-online-activity/api/services/app/createDriveTestLogs.json";

    public static void main(String[] args) {
        String timestamp = "" + System.currentTimeMillis();
        //String timestamp = "1603439155535";
        String nonce = "123456";
        String signature = sign(SECRET_KEY, timestamp, nonce, PATH_LOTTERY, getMap());
        System.out.println("clientId:" + CLIENT_ID);
        System.out.println("timestamp:" + timestamp);
        System.out.println("nonce:" + nonce);
        System.out.println("signature:" + signature);
    }

    public static Map<String, String> getMap() {
        // get请求带的参数需要放到里面，post参数不需要
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("mobile", "15102908320");
        queryParamsMap.put("callState", "1");
        return queryParamsMap;
    }

    public static String sign(String clientSecret, String timestamp, String nonce, String path, Map<String, String> queryParamsMap) {
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
        String encrypt = Base64.encodeBase64String(aes.encrypt(message));
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

