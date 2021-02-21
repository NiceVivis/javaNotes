package com.vivi.basic.http;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author yangwei
 * @date 2020/10/14 9:59 上午
 */
@Slf4j
public class HttpServletDemo {

    /**
     * json格式传参请求url，或者请求后header里的数据
     */
    @Test
    public void doPostJson() {
        JSONObject params = new JSONObject();
        params.put("username", "vworld");
        params.put("password","gScp7NrH44qT1qL4");

        // 创建连接池
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        // 声明呀一个字符串用来存储response
        String result;
        // 创建httppost对象
        HttpPost httpPost = new HttpPost("https://uat-crm.digitalvolvo.com/auth/login");
        // 给httppost对象设置json格式的参数
        StringEntity httpEntity = new StringEntity(params.toString(),"utf-8");
        // 设置请求格式
        httpPost.setHeader("Content-type","application/json");
        // 传参
        httpPost.setEntity(httpEntity);

        // 发送请求，并获取返回值
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            // 将response转成String并存储在result中
            Header[] authorizations = response.getHeaders("Authorization");
            String auth = null;
            for (Header authorization : authorizations) {
                auth = authorization.getValue();
            }

            System.out.printf(""+auth);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用post方法请求webService接口
     * @throws IOException
     */
    @Test
    public void sendMMS() throws IOException {
        HttpClient client=new HttpClient();
        PostMethod postMethod=new PostMethod("http://www.wemediacn.net/webservice/mmsservice.asmx/SendMMS");
        //3.设置请求参数
        postMethod.setParameter("mobile","18790447367");
        postMethod.setParameter("mmsid", "46478349");
        postMethod.setParameter("sToken", "7100120020448791");
        int code=client.executeMethod(postMethod);
        System.out.println("code"+code);
        //5. 获取结果
        String result=postMethod.getResponseBodyAsString();
        System.out.printf("result",result);
    }

    /**
     *
     */
    @Test
    public void doPost() {
        JSONObject params = new JSONObject();
        params.put("mobile", "18790447367");
        params.put("mmsid","46478349");
        params.put("sToken","7100120020448792");

        // 创建连接池
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        // 声明呀一个字符串用来存储response
        String result;
        // 创建httppost对象
        HttpPost httpPost = new HttpPost("http://www.wemediacn.net/webservice/mmsservice.asmx/SendMMS?mobile=18790447367&mmsid=46478349&sToken=7100120020448792");
        // 给httppost对象设置json格式的参数
        //StringEntity httpEntity = new StringEntity(params.toString(),"utf-8");
        // 传参
        //httpPost.setEntity(httpEntity);

        // 发送请求，并获取返回值
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            System.out.printf("response"+response);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                String re= EntityUtils.toString(response.getEntity());// 返回json格式：
                System.out.printf("re:"+re);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
            try {
                SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
                System.out.printf(""+f.parse("2020/10/15"));
            } catch (Exception var3) {
            }
    }
}
