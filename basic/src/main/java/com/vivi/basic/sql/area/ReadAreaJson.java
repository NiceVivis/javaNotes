package com.vivi.basic.sql.area;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.vivi.basic.file.Area;
import com.vivi.basic.file.FileUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author yangwei
 * @date 2021/1/22 1:51 下午
 */
public class ReadAreaJson {

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
        List<Area> areas = JSON.parseObject(jsonStr,new TypeReference<List<Area>>(){});
        String insertStr = "INSERT INTO `common_dict`.`dict`(`CODE`,`CODE_VALUE`, `TYPE`, `TYPE_NAME`, `P_CODE`) VALUES";
        String strValue = "";
        for (Area area : areas) {
            strValue+= "('"+area.getCode()+"','"+area.getName()+"','"+"10001003"+"','"+"10001003"+"','"+"000000"+"'"+"),";
        }

        System.out.printf(insertStr+strValue);
    }

    @Test
    public void readCity(){
        String path = getClass().getClassLoader().getResource("city.json").getPath();
        String jsonStr = FileUtils.resdJsonFile(path);

        //Map<String, Province> messageMap = JSONObject.parseObject(JSON.toJSONString(jsonStr), HashMap.class);
        List<Area> areas = JSON.parseObject(jsonStr,new TypeReference<List<Area>>(){});
        String insertStr = "INSERT INTO `common_dict`.`dict`(`CODE`,`CODE_VALUE`, `TYPE`, `TYPE_NAME`, `P_CODE`) VALUES";
        String strValue = "";
        for (Area area : areas) {
            strValue+= "('"+area.getCode()+"','"+area.getName()+"','"+"10001003"+"','"+"10001003"+"','"+area.getProvince()+"0000"+"'"+"),";
        }

        System.out.printf(insertStr+strValue);
    }

    @Test
    public void readArea(){
        String path = getClass().getClassLoader().getResource("area.json").getPath();
        String jsonStr = FileUtils.resdJsonFile(path);

        //Map<String, Province> messageMap = JSONObject.parseObject(JSON.toJSONString(jsonStr), HashMap.class);
        List<Area> areas = JSON.parseObject(jsonStr,new TypeReference<List<Area>>(){});
        String insertStr = "INSERT INTO `common_dict`.`dict`(`CODE`,`CODE_VALUE`, `TYPE`, `TYPE_NAME`, `P_CODE`) VALUES";
        String strValue = "";
        for (Area area : areas) {
            strValue+= "('"+area.getCode()+"','"+area.getName()+"','"+"10001003"+"','"+"10001003"+"','"+area.getProvince()+"0000"+"'"+"),";
        }

        System.out.printf(insertStr+strValue);
    }

    @Test
    public void readAreas(){
        String path = getClass().getClassLoader().getResource("areas.json").getPath();
        String jsonStr = FileUtils.resdJsonFile(path);

        //Map<String, Province> messageMap = JSONObject.parseObject(JSON.toJSONString(jsonStr), HashMap.class);
        List<Areas> areas = JSON.parseObject(jsonStr,new TypeReference<List<Areas>>(){});
        String insertStr = "INSERT INTO `common_dict`.`dict`(`CODE`,`CODE_VALUE`, `TYPE`, `TYPE_NAME`, `P_CODE`) VALUES";
        String strValue = "";
        String strCity = "";
        String strArea = "";
        for (Areas area : areas) {
            strValue+= "('"+area.getCode()+"','"+area.getName()+"','"+"10001003"+"','"+"10001003"+"','"+"000000"+"'"+"),";

            String city = "";
            String area1 = "";
            for (Areas ar:area.getCityList()){
                city += "('"+ar.getCode()+"','"+ar.getName()+"','"+"10001003"+"','"+"10001003"+"','"+area.getCode()+"'"+"),";


                for (Areas ars:ar.getAreaList()){

                    area1 += "('"+ars.getCode()+"','"+ars.getName()+"','"+"10001003"+"','"+"10001003"+"','"+ar.getCode()+"'"+"),";

                }
            }

            strCity += city;

            strArea += area1;
        }


        //System.out.printf(insertStr+strValue);
        //System.out.printf(insertStr+strCity);
        System.out.printf(insertStr+strArea);
    }
}
