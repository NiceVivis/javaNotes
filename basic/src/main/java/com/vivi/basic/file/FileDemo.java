package com.vivi.basic.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.vivi.basic.list.User;
import com.vivi.basic.sql.area.Province;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDemo {

    @Test
    public void appsettings(){
        String path = getClass().getClassLoader().getResource("test.json").getPath();
        //File jsonFile = ResourceUtils.getFile("classpath:test.json");
        System.out.printf("path:"+path);
        String jsonStr = FileUtils.resdJsonFile(path);
        System.out.printf("jsonStr:"+jsonStr);
        Object parse = JSON.parse(jsonStr);
        //PopWindowsDTO popWindowsDTO = JSONObject.parseObject(parse,PopWindowsDTO.class);
        System.out.printf("popWindowsDTO:"+parse);
    }

    @Test
    public void readProvince(){
        String path = getClass().getClassLoader().getResource("province.json").getPath();
        //File jsonFile = ResourceUtils.getFile("classpath:test.json");
        System.out.printf("path:"+path);
        String jsonStr = FileUtils.resdJsonFile(path);
        System.out.printf("jsonStr:"+jsonStr);
//        Object parse = JSON.parse(jsonStr);
//        System.out.printf("popWindowsDTO:"+parse);

        //Map<String, Province> messageMap = JSONObject.parseObject(JSON.toJSONString(jsonStr), HashMap.class);
        List<Province> provinces = JSON.parseObject(jsonStr,new TypeReference<List<Province>>(){});
        String insertStr = "INSERT INTO `common_dict`.`dict`(`CODE`,`CODE_VALUE`, `TYPE`, `TYPE_NAME`, `P_CODE`) VALUES";
        String strValue = "";
        for (Province province : provinces) {
            strValue+= "('"+province.getCode()+"','"+province.getName()+"','"+"10001003"+"','"+"10001003"+"','"+"000000"+"'"+"),";
        }

        System.out.printf(insertStr+strValue);
    }
}
