package com.vivi.basic.list.CarConfigParam;

import com.alibaba.fastjson.JSON;
import com.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class CarConfigParamDemo {
    public static void main(String[] args) {
        List<List<CarConfigParam>> lists = new ArrayList<>();
        for (int i = 1;i<3;i++){
            List<CarConfigParam> carConfigParams = JsonInit.init();
            lists.add(carConfigParams);
        }
        System.out.printf(""+lists);
        System.out.printf("lists"+ JSON.toJSONString(queryConfigParamList(lists)));
//        lists = JsonInit.inits();
//        System.out.printf(""+lists);
//
//        System.out.println("--------------------");
//
//        System.out.println("lists:"+queryConfigParamList(lists));
    }

    /**
     * 几组List<CarConfigParam>数据中subParamList 子参数列表中value相同的，则将valueRemark赋值为1
     * @param lists
     * @return
     */
    public static List<List<CarConfigParam>> queryConfigParamList(List<List<CarConfigParam>> lists){
        List<CarConfigParam> carConfigParamList = new ArrayList<>();

        //将几组List<CarConfigParam>数据中subParamList取出，放到carConfigParamList中
        lists.stream().forEach(list->{
            list.stream().forEach(carConfigParam -> {
                carConfigParam.getSubParamList().stream().forEach(param->{
                    carConfigParamList.add(param);
                });
            });
        });

        //比较subParamList中的value的值是否相同，相同则赋值为1
        for (int i =0;i<carConfigParamList.size();i++){
            for (int j = i+1;j<carConfigParamList.size();j++) {
                if (carConfigParamList.get(i) !=null) {
                    if (carConfigParamList.get(i).getParamCode().equals(carConfigParamList.get(j).getParamCode())) {
                        System.out.println("equals: "+Objects.equals(carConfigParamList.get(i).getParamValue(),carConfigParamList.get(j).getParamValue()));
                        System.out.println("发动机"+carConfigParamList.get(i).getParamValue()+"，"+carConfigParamList.get(j).getParamValue());
                        if (Objects.equals(carConfigParamList.get(i).getParamValue(),carConfigParamList.get(j).getParamValue())){
                            carConfigParamList.get(i).setValueRemark("1");
                        }
                    }
                }
            }
        }
        return  lists;
    }

    /**
     * 几组List<CarConfigParam>数据中subParamList 子参数列表中value相同的，则将valueRemark赋值为1
     * 将List<List<CarConfigParam>> 转换成List<CarConfigParam>，subParamList中相同的paramCode合并在一起
     * @param lists
     * @return
     */
    public static List<CarConfigParamVO> queryConfigParamVOList(List<List<CarConfigParam>> lists) {

        List<CarConfigParam> carConfigParamList = new ArrayList<>();
        //将几组List<CarConfigParam>数据中subParamList取出，放到carConfigParamList中
        lists.stream().forEach(list->{
            list.stream().forEach(carConfigParam -> {
                carConfigParam.getSubParamList().stream().forEach(param->{
                    carConfigParamList.add(param);
                });
            });
        });

        //比较subParamList中的value的值是否相同，相同则赋值为1
        for (int i =0;i<carConfigParamList.size();i++){
            for (int j = i+1;j<carConfigParamList.size();j++) {
                if (carConfigParamList.get(i) !=null) {
                    if (carConfigParamList.get(i).getParamCode().equals(carConfigParamList.get(j).getParamCode())) {
                        if (Objects.equals(carConfigParamList.get(i).getParamValue(),carConfigParamList.get(j).getParamValue())){
                            carConfigParamList.get(j).setValueRemark("1");
                        }
                    }
                }
            }
        }

        //log.info("carConfigParamList:{}", JSON.toJSONString(carConfigParamList));
        //将ParentParamCode+ParamCode+ParamName相同的分组
        Map<String,List<CarConfigParam>> temp = carConfigParamList.stream().collect(Collectors.groupingBy(carConfig->
                carConfig.getParentParamCode()+"-"+carConfig.getParamCode()+"-"+carConfig.getParamName()));

        //将CarConfigParam转换成CarConfigParamVO
        List<CarConfigParamVO> carConfig = new ArrayList<>();
        List<CarConfigParam> carConfigParamVOS = lists.get(0);
        carConfigParamVOS.stream().forEach(list->{
            list.setSubParamList(null);
            CarConfigParamVO carConfigParamVO = CommonUtil.conventObj(list,CarConfigParamVO.class);
            carConfig.add(carConfigParamVO);
        });

        //比较Map中的value为ist<CarConfigParam>的parentParamCode和carConfig中的paramCode，相同的则放在一个list中
        List<CarConfigParamVO> configParamVOList = new ArrayList<>();
        for (CarConfigParamVO carConfigParam:carConfig) {
            List<List<CarConfigParam>> paramList = new ArrayList<>();
            for (String key : temp.keySet()) {
                String split = key.split("-")[0];
                if (split.equals(carConfigParam.getParamCode())){
                    paramList.add(temp.get(key));
                }
            }
            carConfigParam.setSubParamList(paramList);
            configParamVOList.add(carConfigParam);
        }

        //log.info("carConfigParamVOS2:{}",JSON.toJSONString(configParamVOList));

        return configParamVOList;
    }

    /**
     * 几组List<CarConfigParam>数据中subParamList 子参数列表中value相同的，则将valueRemark赋值为1
     * 将List<List<CarConfigParam>> 转换成List<CarConfigParam>，subParamList中相同的paramCode合并在一起
     * 并进行排序
     * @param lists
     * @return
     */
    public static List<CarConfigParamVO> queryConfigParamVOList2(List<List<CarConfigParam>> lists) {

        List<CarConfigParam> carConfigParamList = new ArrayList<>();
        //将几组List<CarConfigParam>数据中subParamList取出，放到carConfigParamList中
        lists.stream().forEach(list->{
            list.stream().forEach(carConfigParam -> {
                carConfigParam.getSubParamList().stream().forEach(param->{
                    carConfigParamList.add(param);
                });
            });
        });

        //比较subParamList中的value的值是否相同，相同则赋值为1，把外层的置为1
        for (int i = 0; i < carConfigParamList.size(); i++) {
            int k = 0;
            for (int j = i + 1; j < carConfigParamList.size(); j++) {
                if (carConfigParamList.get(i) != null) {
                    if (carConfigParamList.get(i).getParamCode().equals(carConfigParamList.get(j).getParamCode())) {
                        if (Objects.equals(carConfigParamList.get(i).getParamValue(), carConfigParamList.get(j).getParamValue())) {
                            k++;
                        }
                    }
                }
            }

            if ( k == lists.size() - 1){
                carConfigParamList.get(i).setIsMerge("1");
            }
        }

        //log.info("carConfigParamList:{}", JSON.toJSONString(carConfigParamList));
        //将ParentParamCode+ParamCode+ParamName相同的分组
        Map<String,List<CarConfigParam>> temp = carConfigParamList.stream().collect(Collectors.groupingBy(carConfig->
                carConfig.getParentParamCode()+"-"+carConfig.getParamCode()+"-"+carConfig.getParamName()));

        //将CarConfigParam转换成CarConfigParamVO
        List<CarConfigParamVO> carConfig = new ArrayList<>();
        List<CarConfigParam> carConfigParamVOS = lists.get(0);
        carConfigParamVOS.stream().forEach(list->{
            list.setSubParamList(null);
            CarConfigParamVO carConfigParamVO = CommonUtil.conventObj(list,CarConfigParamVO.class);
            carConfig.add(carConfigParamVO);
        });

        //比较Map中的value为ist<CarConfigParam>的parentParamCode和carConfig中的paramCode，相同的则放在一个list中
        List<CarConfigParamVO> configParamVOList = new ArrayList<>();
        for (CarConfigParamVO carConfigParam:carConfig) {
            List<List<CarConfigParam>> paramList = new ArrayList<>();
            for (String key : temp.keySet()) {
                String split = key.split("-")[0];
                if (split.equals(carConfigParam.getParamCode())){
                    paramList.add(temp.get(key));
                }
            }
            carConfigParam.setSubParamList(paramList);
            configParamVOList.add(carConfigParam);
        }

        //根据多层list中的字段进行比较
        configParamVOList
                .stream()
                .map(CarConfigParamVO::getSubParamList)
                .forEach(list -> list.sort((o1, o2) -> {
                    if (o1.isEmpty()) {
                        return -1;
                    } else if (o2.isEmpty()) {
                        return 1;
                    }
                    return orMax(o1.get(0).getSortNum()) - orMax(o2.get(0).getSortNum());
                }));

        //log.info("carConfigParamVOS2:{}",JSON.toJSONString(configParamVOList));

        return configParamVOList;
    }

    private static int orMax(Integer n) {
        if (n == null) {
            return Integer.MAX_VALUE;
        }
        return n;
    }
}
