package com.vivi.basic.list.CarConfigParam;

import java.util.ArrayList;
import java.util.List;

public class JsonInit {

    public static void main(String[] args) {
        init();
    }

    public static List<CarConfigParam> init(){
        List<CarConfigParam> carConfigParams = new ArrayList<>();

        CarConfigParam carConfigParam1 = new CarConfigParam();
        carConfigParam1.setParamCode("1001");
        carConfigParam1.setParamName("发动机");
        carConfigParam1.setParamLevel(1);
        carConfigParam1.setRemark("");
        carConfigParam1.setParentParamCode("");
        carConfigParam1.setValueRemark("");
        carConfigParam1.setValueRemark("");

        CarConfigParam Param1 = new CarConfigParam();
        Param1.setParamCode("100101");
        Param1.setParamName("发动机类型");
        Param1.setParamValue("Drive-E T4 涡轮增压汽油发动机");
        Param1.setParamLevel(2);
        Param1.setRemark("");
        Param1.setParentParamCode("1001");
        Param1.setValueRemark("");
        Param1.setValueRemark("");

        CarConfigParam Param2 = new CarConfigParam();
        Param2.setParamCode("100102");
        Param2.setParamName("最大功率(kW/hp/rpm)");
        Param2.setParamValue("140/190/5000");
        Param2.setParamLevel(2);
        Param2.setRemark("");
        Param2.setParentParamCode("1001");
        Param2.setValueRemark("");
        Param2.setValueRemark("");

        CarConfigParam Param3 = new CarConfigParam();
        Param3.setParamCode("100902");
        Param3.setParamName("CZIS主动式座舱预净化系统");
        Param3.setParamLevel(2);
        Param3.setRemark("");
        Param3.setParentParamCode("1001");
        Param3.setValueRemark("");
        Param3.setValueRemark("");

        CarConfigParam Param4 = new CarConfigParam();
        Param4.setParamCode("100102");
        Param4.setParamName("最大扭矩(Nm/rpm)");
        Param4.setParamLevel(2);
        Param4.setRemark("");
        Param4.setParentParamCode("1001");
        Param4.setValueRemark("");
        Param4.setValueRemark("");

        CarConfigParam Param5 = new CarConfigParam();
        Param5.setParamCode("100903");
        Param5.setParamName("座舱远程净化系统");
        Param5.setParamLevel(2);
        Param5.setRemark("");
        Param5.setParentParamCode("1001");
        Param5.setValueRemark("");
        Param5.setValueRemark("");

        CarConfigParam Param6 = new CarConfigParam();
        Param6.setParamCode("101403");
        Param6.setParamName("前风挡玻璃清洗喷嘴加热");
        Param6.setParamLevel(2);
        Param6.setRemark("");
        Param6.setParentParamCode("1001");
        Param6.setValueRemark("");
        Param6.setValueRemark("");

        CarConfigParam Param7 = new CarConfigParam();
        Param7.setParamCode("101404");
        Param7.setParamName("方向盘加热");
        Param7.setParamLevel(2);
        Param7.setRemark("");
        Param7.setParentParamCode("1001");
        Param7.setValueRemark("");
        Param7.setValueRemark("");

        List<CarConfigParam> list1 = new ArrayList<>();
        list1.add(Param1);
        list1.add(Param2);
        list1.add(Param3);
        list1.add(Param4);
        list1.add(Param5);
        list1.add(Param6);
        list1.add(Param7);

        carConfigParam1.setSubParamList(list1);


        CarConfigParam carConfigParam2 = new CarConfigParam();
        carConfigParam2.setParamCode("1002");
        carConfigParam2.setParamName("综合性能");
        carConfigParam2.setParamLevel(1);
        carConfigParam2.setRemark("");
        carConfigParam2.setParentParamCode("");
        carConfigParam2.setValueRemark("");
        carConfigParam2.setValueRemark("");

        CarConfigParam Param11 = new CarConfigParam();
        Param11.setParamCode("100201");
        Param11.setParamName("最高速度(km/h)");
        Param11.setParamLevel(2);
        Param11.setRemark("");
        Param11.setParentParamCode("1002");
        Param11.setValueRemark("");
        Param11.setValueRemark("");

        CarConfigParam Param21 = new CarConfigParam();
        Param21.setParamCode("100102");
        Param21.setParamName("0-100km/h加速时间(s)");
        Param21.setParamLevel(2);
        Param21.setRemark("");
        Param21.setParentParamCode("1002");
        Param21.setValueRemark("");
        Param21.setValueRemark("");

        CarConfigParam Param31 = new CarConfigParam();
        Param31.setParamCode("100903");
        Param31.setParamName("综合油耗(L/100km)");
        Param31.setParamLevel(2);
        Param31.setRemark("此综合油耗按国标标准规定的综合工况测得，实际油耗受行驶条件影响，可能与此油耗存在差异");
        Param31.setParentParamCode("1002");
        Param31.setValueRemark("");
        Param31.setValueRemark("");

        List<CarConfigParam> list2 = new ArrayList<>();
        list2.add(Param11);
        list2.add(Param21);
        list2.add(Param31);

        carConfigParam2.setSubParamList(list2);

        CarConfigParam carConfigParam3 = new CarConfigParam();
        carConfigParam3.setParamCode("1003");
        carConfigParam3.setParamName("底盘和悬架");
        carConfigParam3.setParamLevel(1);
        carConfigParam3.setRemark("");
        carConfigParam3.setParentParamCode("");
        carConfigParam3.setValueRemark("");
        carConfigParam3.setValueRemark("");

        CarConfigParam Param12 = new CarConfigParam();
        Param12.setParamCode("100301");
        Param12.setParamName("变速器");
        Param12.setParamLevel(2);
        Param12.setRemark("");
        Param12.setParentParamCode("1003");
        Param12.setValueRemark("");
        Param12.setValueRemark("");

        CarConfigParam Param22 = new CarConfigParam();
        Param22.setParamCode("100302");
        Param22.setParamName("驱动模式");
        Param22.setParamLevel(2);
        Param22.setRemark("");
        Param22.setParentParamCode("1003");
        Param22.setValueRemark("");
        Param22.setValueRemark("");

        CarConfigParam Param32 = new CarConfigParam();
        Param32.setParamCode("100303");
        Param32.setParamName("底盘");
        Param32.setParamLevel(2);
        Param32.setRemark("");
        Param32.setParentParamCode("1003");
        Param32.setValueRemark("");
        Param32.setValueRemark("");

        CarConfigParam Param42 = new CarConfigParam();
        Param42.setParamCode("100304");
        Param42.setParamName("悬挂");
        Param42.setParamLevel(2);
        Param42.setRemark("");
        Param42.setParentParamCode("1003");
        Param42.setValueRemark("");
        Param42.setValueRemark("");

        CarConfigParam Param52 = new CarConfigParam();
        Param52.setParamCode("100305");
        Param52.setParamName("4C自适应底盘");
        Param52.setParamLevel(2);
        Param52.setRemark("");
        Param52.setParentParamCode("1003");
        Param52.setValueRemark("");
        Param52.setValueRemark("");

        CarConfigParam Param62 = new CarConfigParam();
        Param62.setParamCode("100306");
        Param62.setParamName("轮胎规格");
        Param62.setParamLevel(2);
        Param62.setRemark("");
        Param62.setParentParamCode("1003");
        Param62.setValueRemark("");
        Param62.setValueRemark("");

        CarConfigParam Param72 = new CarConfigParam();
        Param72.setParamCode("100307");
        Param72.setParamName("备胎");
        Param72.setParamLevel(2);
        Param72.setRemark("");
        Param72.setParentParamCode("1003");
        Param72.setValueRemark("");
        Param72.setValueRemark("");

        List<CarConfigParam> list3 = new ArrayList<>();
        list3.add(Param12);
        list3.add(Param22);
        list3.add(Param32);
        list3.add(Param42);
        list3.add(Param52);
        list3.add(Param62);
        list3.add(Param72);

        carConfigParam3.setSubParamList(list3);


        CarConfigParam carConfigParam4 = new CarConfigParam();
        carConfigParam4.setParamCode("1004");
        carConfigParam4.setParamName("整车尺寸");
        carConfigParam4.setParamLevel(1);
        carConfigParam4.setRemark("");
        carConfigParam4.setParentParamCode("");
        carConfigParam4.setValueRemark("");
        carConfigParam4.setValueRemark("");

        CarConfigParam Param13 = new CarConfigParam();
        Param13.setParamCode("100401");
        Param13.setParamName("长(mm)/宽(mm)/高(mm)");
        Param13.setParamLevel(2);
        Param13.setRemark("");
        Param13.setParentParamCode("1004");
        Param13.setValueRemark("");
        Param13.setValueRemark("");

        CarConfigParam Param23 = new CarConfigParam();
        Param23.setParamCode("100402");
        Param23.setParamName("轴距(mm)");
        Param23.setParamLevel(2);
        Param23.setRemark("");
        Param23.setParentParamCode("1004");
        Param23.setValueRemark("");
        Param23.setValueRemark("");

        CarConfigParam Param33 = new CarConfigParam();
        Param33.setParamCode("100403");
        Param33.setParamName("后备箱容积(L)");
        Param33.setParamLevel(2);
        Param33.setRemark("");
        Param33.setParentParamCode("1004");
        Param33.setValueRemark("");
        Param33.setValueRemark("");

        CarConfigParam Param43 = new CarConfigParam();
        Param43.setParamCode("100404");
        Param43.setParamName("离地间隙(mm)");
        Param43.setParamLevel(2);
        Param43.setRemark("");
        Param43.setParentParamCode("1004");
        Param43.setValueRemark("");
        Param43.setValueRemark("");

        List<CarConfigParam> list4 = new ArrayList<>();
        list4.add(Param13);
        list4.add(Param23);
        list4.add(Param33);
        list4.add(Param43);

        carConfigParam4.setSubParamList(list4);


        CarConfigParam carConfigParam5 = new CarConfigParam();
        carConfigParam5.setParamCode("1005");
        carConfigParam5.setParamName("IntelliSafe智能安全");
        carConfigParam5.setParamLevel(1);
        carConfigParam5.setRemark("");
        carConfigParam5.setParentParamCode("");
        carConfigParam5.setValueRemark("");
        carConfigParam5.setValueRemark("");

        CarConfigParam Param53 = new CarConfigParam();
        Param53.setParamCode("100501");
        Param53.setParamName("标准智能安全系统");
        Param53.setParamLevel(2);
        Param53.setRemark("");
        Param53.setParentParamCode("1005");
        Param53.setValueRemark("");
        Param53.setValueRemark("");

        List<CarConfigParam> list5 = new ArrayList<>();
        list5.add(Param53);

        carConfigParam5.setSubParamList(list5);

        CarConfigParam carConfigParam6 = new CarConfigParam();
        carConfigParam6.setParamCode("1006");
        carConfigParam6.setParamName("停车辅助系统");
        carConfigParam6.setParamLevel(1);
        carConfigParam6.setRemark("");
        carConfigParam6.setParentParamCode("");
        carConfigParam6.setValueRemark("");
        carConfigParam6.setValueRemark("");

        CarConfigParam Param14 = new CarConfigParam();
        Param14.setParamCode("100601");
        Param14.setParamName("长(mm)/宽(mm)/高(mm)");
        Param14.setParamLevel(2);
        Param14.setRemark("");
        Param14.setParentParamCode("1006");
        Param14.setValueRemark("");
        Param14.setValueRemark("");

        CarConfigParam Param24 = new CarConfigParam();
        Param24.setParamCode("100602");
        Param24.setParamName("轴距(mm)");
        Param24.setParamLevel(2);
        Param24.setRemark("");
        Param24.setParentParamCode("1006");
        Param24.setValueRemark("");
        Param24.setValueRemark("");

        CarConfigParam Param34 = new CarConfigParam();
        Param34.setParamCode("100603");
        Param34.setParamName("后备箱容积(L)");
        Param34.setParamLevel(2);
        Param34.setRemark("");
        Param34.setParentParamCode("1006");
        Param34.setValueRemark("");
        Param34.setValueRemark("");

        CarConfigParam Param44 = new CarConfigParam();
        Param44.setParamCode("100404");
        Param44.setParamName("离地间隙(mm)");
        Param44.setParamLevel(2);
        Param44.setRemark("");
        Param44.setParentParamCode("1006");
        Param44.setValueRemark("");
        Param44.setValueRemark("");

        List<CarConfigParam> list6 = new ArrayList<>();
        list6.add(Param14);
        list6.add(Param24);
        list6.add(Param34);
        list6.add(Param44);

        carConfigParam6.setSubParamList(list6);

        CarConfigParam carConfigParam7 = new CarConfigParam();
        carConfigParam7.setParamCode("1007");
        carConfigParam7.setParamName("外饰");
        carConfigParam7.setParamLevel(1);
        carConfigParam7.setRemark("");
        carConfigParam7.setParentParamCode("");
        carConfigParam7.setValueRemark("");
        carConfigParam7.setValueRemark("");

        CarConfigParam Param15 = new CarConfigParam();
        Param15.setParamCode("100701");
        Param15.setParamName("电动全景天窗");
        Param15.setParamLevel(2);
        Param15.setRemark("");
        Param15.setParentParamCode("1007");
        Param15.setValueRemark("");
        Param15.setValueRemark("");

        CarConfigParam Param25 = new CarConfigParam();
        Param25.setParamCode("100702");
        Param25.setParamName("亮银色车顶行李架");
        Param25.setParamLevel(2);
        Param25.setRemark("");
        Param25.setParentParamCode("1007");
        Param25.setValueRemark("");
        Param25.setValueRemark("");

        CarConfigParam Param35 = new CarConfigParam();
        Param35.setParamCode("100703");
        Param35.setParamName("豪华风格前格栅");
        Param35.setParamLevel(2);
        Param35.setRemark("");
        Param35.setParentParamCode("1007");
        Param35.setValueRemark("");
        Param35.setValueRemark("");

        CarConfigParam Param45 = new CarConfigParam();
        Param45.setParamCode("100704");
        Param45.setParamName("亮银色侧窗边框");
        Param45.setParamLevel(2);
        Param45.setRemark("");
        Param45.setParentParamCode("1007");
        Param45.setValueRemark("");
        Param45.setValueRemark("");

        List<CarConfigParam> list7 = new ArrayList<>();
        list7.add(Param15);
        list7.add(Param25);
        list7.add(Param35);
        list7.add(Param45);

        carConfigParam7.setSubParamList(list7);


        CarConfigParam carConfigParam8 = new CarConfigParam();
        carConfigParam8.setParamCode("1008");
        carConfigParam8.setParamName("轮毂");
        carConfigParam8.setParamLevel(1);
        carConfigParam8.setRemark("");
        carConfigParam8.setParentParamCode("");
        carConfigParam8.setValueRemark("");
        carConfigParam8.setValueRemark("");

        CarConfigParam Param16 = new CarConfigParam();
        Param16.setParamCode("100801");
        Param16.setParamName("17英寸 豪华版 铝合金轮毂");
        Param16.setParamLevel(2);
        Param16.setRemark("");
        Param16.setParentParamCode("1008");
        Param16.setValueRemark("");
        Param16.setValueRemark("");

        CarConfigParam Param26 = new CarConfigParam();
        Param26.setParamCode("100802");
        Param26.setParamName("18英寸 豪华版 铝合金轮毂");
        Param26.setParamLevel(2);
        Param26.setRemark("");
        Param26.setParentParamCode("1008");
        Param26.setValueRemark("");
        Param26.setValueRemark("");

        CarConfigParam Param36 = new CarConfigParam();
        Param36.setParamCode("100803");
        Param36.setParamName("18英寸 运动版 铝合金轮毂");
        Param36.setParamLevel(2);
        Param36.setRemark("");
        Param36.setParentParamCode("1008");
        Param36.setValueRemark("");
        Param36.setValueRemark("");

        CarConfigParam Param46 = new CarConfigParam();
        Param46.setParamCode("100804");
        Param46.setParamName("19英寸 运动版 铝合金轮毂");
        Param46.setParamLevel(2);
        Param46.setRemark("");
        Param46.setParentParamCode("1008");
        Param46.setValueRemark("");
        Param46.setValueRemark("");

        List<CarConfigParam> list8 = new ArrayList<>();
        list8.add(Param16);
        list8.add(Param26);
        list8.add(Param36);
        list8.add(Param46);

        carConfigParam8.setSubParamList(list8);


        CarConfigParam carConfigParam9 = new CarConfigParam();
        carConfigParam1.setParamCode("1009");
        carConfigParam1.setParamName("CLEANZONE®清洁驾驶舱");
        carConfigParam1.setParamLevel(1);
        carConfigParam1.setRemark("");
        carConfigParam1.setParentParamCode("");
        carConfigParam1.setValueRemark("");
        carConfigParam1.setValueRemark("");

        CarConfigParam carConfigParam10 = new CarConfigParam();
        carConfigParam10.setParamCode("1010");
        carConfigParam10.setParamName("Sensus互联及信息娱乐系统");
        carConfigParam10.setParamLevel(1);
        carConfigParam10.setRemark("");
        carConfigParam10.setParentParamCode("");
        carConfigParam10.setValueRemark("");
        carConfigParam10.setValueRemark("");

        CarConfigParam Param29 = new CarConfigParam();
        Param29.setParamCode("101001");
        Param29.setParamName("Volvo On Call 随车管家（含3年服务、远程车辆控制等）");
        Param29.setParamLevel(2);
        Param29.setRemark("");
        Param29.setParentParamCode("1010");
        Param29.setValueRemark("");
        Param29.setValueRemark("");

        CarConfigParam Param39 = new CarConfigParam();
        Param39.setParamCode("101002");
        Param39.setParamName("专业级导航（带道路标志信息）");
        Param39.setParamLevel(2);
        Param39.setRemark("");
        Param39.setParentParamCode("1010");
        Param39.setValueRemark("");
        Param39.setValueRemark("");

        CarConfigParam Param49 = new CarConfigParam();
        Param49.setParamCode("101003");
        Param49.setParamName("8英寸液晶数字仪表盘");
        Param49.setParamLevel(2);
        Param49.setRemark("");
        Param49.setParentParamCode("1010");
        Param49.setValueRemark("");
        Param49.setValueRemark("");

        List<CarConfigParam> list10 = new ArrayList<>();
        list10.add(Param29);
        list10.add(Param39);
        list10.add(Param49);

        carConfigParam10.setSubParamList(list10);

        CarConfigParam carConfigParam11 = new CarConfigParam();
        carConfigParam1.setParamCode("1011");
        carConfigParam1.setParamName("内饰");
        carConfigParam1.setParamLevel(1);
        carConfigParam1.setRemark("");
        carConfigParam1.setParentParamCode("");
        carConfigParam1.setValueRemark("");
        carConfigParam1.setValueRemark("");

        CarConfigParam Param02 = new CarConfigParam();
        Param02.setParamCode("101101");
        Param02.setParamName("无钥匙启动");
        Param02.setParamLevel(2);
        Param02.setRemark("");
        Param02.setParentParamCode("1011");
        Param02.setValueRemark("");
        Param02.setValueRemark("");

        CarConfigParam Param03 = new CarConfigParam();
        Param03.setParamCode("101102");
        Param03.setParamName("真皮包裹遥控钥匙");
        Param03.setParamLevel(2);
        Param03.setRemark("");
        Param03.setParentParamCode("1011");
        Param03.setValueRemark("");
        Param03.setValueRemark("");

        CarConfigParam Param04 = new CarConfigParam();
        Param04.setParamCode("101103");
        Param04.setParamName("皮革包覆中控台");
        Param04.setParamLevel(2);
        Param04.setRemark("");
        Param04.setParentParamCode("1011");
        Param04.setValueRemark("");
        Param04.setValueRemark("");

        List<CarConfigParam> list11 = new ArrayList<>();
        list11.add(Param02);
        list11.add(Param03);
        list11.add(Param04);

        carConfigParam11.setSubParamList(list11);

        CarConfigParam carConfigParam12 = new CarConfigParam();
        carConfigParam12.setParamCode("1012");
        carConfigParam12.setParamName("车身颜色");
        carConfigParam12.setParamLevel(1);
        carConfigParam12.setRemark("");
        carConfigParam12.setParentParamCode("");
        carConfigParam12.setValueRemark("");
        carConfigParam12.setValueRemark("");

        CarConfigParam Param20 = new CarConfigParam();
        Param20.setParamCode("101201");
        Param20.setParamName("18英寸 豪华版 铝合金轮毂");
        Param20.setParamLevel(2);
        Param20.setRemark("");
        Param20.setParentParamCode("1012");
        Param20.setValueRemark("");
        Param20.setValueRemark("");

        CarConfigParam Param30 = new CarConfigParam();
        Param30.setParamCode("101202");
        Param30.setParamName("18英寸 运动版 铝合金轮毂");
        Param30.setParamLevel(2);
        Param30.setRemark("");
        Param30.setParentParamCode("1012");
        Param30.setValueRemark("");
        Param36.setValueRemark("");

        CarConfigParam Param40 = new CarConfigParam();
        Param40.setParamCode("101203");
        Param40.setParamName("19英寸 运动版 铝合金轮毂");
        Param40.setParamLevel(2);
        Param40.setRemark("");
        Param40.setParentParamCode("1012");
        Param40.setValueRemark("");
        Param40.setValueRemark("");

        List<CarConfigParam> list12 = new ArrayList<>();
        list12.add(Param20);
        list12.add(Param30);
        list12.add(Param40);

        carConfigParam12.setSubParamList(list12);

        carConfigParams.add(carConfigParam1);
        carConfigParams.add(carConfigParam2);
        carConfigParams.add(carConfigParam3);
        carConfigParams.add(carConfigParam4);
        carConfigParams.add(carConfigParam5);
        carConfigParams.add(carConfigParam6);
        carConfigParams.add(carConfigParam7);
        carConfigParams.add(carConfigParam8);
        carConfigParams.add(carConfigParam9);
        carConfigParams.add(carConfigParam10);
        carConfigParams.add(carConfigParam11);
        carConfigParams.add(carConfigParam12);

        //System.out.println("carConfigParams:"+ JSON.toJSONString(carConfigParams));
        return carConfigParams;
    }
    public static List<List<CarConfigParam>> inits(){
        List<List<CarConfigParam>> lists = new ArrayList<>();
        List<CarConfigParam> carConfigParams1 = new ArrayList<>();

        CarConfigParam carConfigParam1 = new CarConfigParam();
        carConfigParam1.setParamCode("1001");
        carConfigParam1.setParamName("发动机");
        carConfigParam1.setParamLevel(1);
        carConfigParam1.setRemark("");
        carConfigParam1.setParentParamCode("");
        carConfigParam1.setValueRemark("");
        carConfigParam1.setValueRemark("");


        CarConfigParam Param1 = new CarConfigParam();
        Param1.setParamCode("100101");
        Param1.setParamName("发动机类型");
        Param1.setParamValue("Drive-E T4 涡轮增压汽油发动机");
        Param1.setParamLevel(2);
        Param1.setRemark("");
        Param1.setParentParamCode("1001");
        Param1.setValueRemark("");
        Param1.setValueRemark("");

        List<CarConfigParam> list1 = new ArrayList<>();
        list1.add(Param1);

        carConfigParam1.setSubParamList(list1);
        carConfigParams1.add(carConfigParam1);

        lists.add(carConfigParams1);

        List<CarConfigParam> carConfigParams2 = new ArrayList<>();
        CarConfigParam carConfigParam2 = new CarConfigParam();
        carConfigParam2.setParamCode("1001");
        carConfigParam2.setParamName("发动机");
        carConfigParam2.setParamLevel(1);
        carConfigParam2.setRemark("");
        carConfigParam2.setParentParamCode("");
        carConfigParam2.setValueRemark("");
        carConfigParam2.setValueRemark("");

        CarConfigParam Param2 = new CarConfigParam();
        Param2.setParamCode("100101");
        Param2.setParamName("发动机类型");
        Param2.setParamValue("Drive-E T5 涡轮增压汽油发动机");
        Param2.setParamLevel(2);
        Param2.setRemark("");
        Param2.setParentParamCode("1001");
        Param2.setValueRemark("");
        Param2.setValueRemark("");

        List<CarConfigParam> list2 = new ArrayList<>();
        list2.add(Param2);

        carConfigParam2.setSubParamList(list2);
        carConfigParams2.add(carConfigParam2);

        lists.add(carConfigParams2);

        return lists;
    }
}
