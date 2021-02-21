package com.vivi.basic.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.vivi.basic.list.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDemo {
    public static void main(String[] args) {
        String str1 = "[{\"id\":\"1\",\"name\":\"mark\",\"value\":10},{\"id\":\"2\",\"name\":\"mark\",\"value\":10}]";

        String str2= "{\"id\":\"1\",\"name\":\"mark\",\"value\":\"10\"}";

        String str3 = "{\"id\":\"1\",\"aid\":\"123456\",\"bussinesscode\":\"11\",\"userinfo\":{\"brand\":\"SVW7189\",\"enumber\":\"20\",\"owner\":\"lily\",\"birthday\":\"1997-01-03\",\"sex\":\"0\",\"regdate\":\"2017-05-01\",\"code\":\"LV163574\"},\"fenddate\":\"2020-12-25 15:00:00\",\"startdate\":\"2019-12-25 15:00:00\",\"price\":\"500.00\",\"discount\":\"0\",\"equList\":[{\"eqname\":\"mark\",\"insuranceList\":[]}],\"inInfo\":{\"email\":\"\",\"type\":\"0\",\"ownermobile\":\"13805600435\",\"ownername\":\"王恩平\"},\"insuredate\":\"\",\"insurerid\":\"1003633\",\"insurername\":\"中华衡阳支公司\",\"itemList\":[{\"price\":\"1000.00\",\"prodname\":\"demo\",\"prodno\":\"10302\"},{\"price\":\"2000.00\",\"prodname\":\"demo2\",\"prodno\":\"10303\"}]}";

        User user = new User("1","mark",20,"");
        User user1 = new User("2","lily",30,"");
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);

        //String,List对象，对象转换成JSON格式
        JSONObject jsonObject1 = JSONObject.parseObject(JSONObject.toJSONString(user));
        String jsonObject2 = JSON.toJSONString(users);
        String jsonObject3 = JsonUtil.getJSON(user);
        String jsonObject4 = JsonUtil.listToJson(users);
        System.out.println("对象转换成json格式："+jsonObject1);
        System.out.println("json格式转成string："+jsonObject2);
        System.out.println("json格式转成string："+jsonObject3);
        System.out.println("json格式转成string："+jsonObject4);

        //JSONObject jsonObjectList = JSONObject.parseObject(JSONObject.toJSONString(users));
        //System.out.println("jsonObjectList:"+jsonObjectList);

        //json字符串与javaBean之间的转换推荐使用 TypeReference<T> 这个类，使用泛型可以更加清晰
        User userObject = JSON.parseObject(str2,new TypeReference<User>(){}) ;
        User userObject1= JSON.parseObject(str2,User.class);
        User userObeject2 = JsonUtil.getObject(str2,User.class);
        System.out.println("JSON格式转换成对象1:"+userObject);
        System.out.println("JSON格式转换成对象2:"+userObject1);
        System.out.println("JSON格式转换成对象3:"+userObeject2);

        //json格式转换成Map格式
        Map<String, User> messageMap = JSONObject.parseObject(JSON.toJSONString(jsonObject1), HashMap.class);
        System.out.println("json格式转换成Map格式1:"+messageMap);
        //将json格式转换成List对象
        List<User> userList = JSONObject.parseArray(JSON.toJSONString(users), User.class);
        List<User> userList1 = JSON.parseObject(str1,new TypeReference<List<User>>(){});
        List<User> userList2 = JsonUtil.jsonToList(str1,User.class);
        //List<User> jsonList = JSONObject.parseArray(jsonObject3,User.class);
        System.out.println("json格式转换成List对象:"+userList);
        System.out.println("json格式转换成List对象1:"+userList1);
        System.out.println("json格式转换成List对象2:"+userList2);


        //list 转换成json格式
        String str = JSON.toJSON(users).toString();
        System.out.println("list 转换成json格式:"+str);
    }
}
