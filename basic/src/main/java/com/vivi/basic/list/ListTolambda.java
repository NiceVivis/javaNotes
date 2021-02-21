package com.vivi.basic.list;

import com.utils.CommonUtil;
import com.vivi.basic.basic.Goods;
import com.vivi.basic.list.CarConfigParam.CarConfigParamVO;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class ListTolambda {

    public static void main(String[] args) {
        Goods goods1 = new Goods(1,"衣服","","1",100,"10010","vivi");
        Goods goods2 = new Goods(2,"衣服","白色","1",100,"10011","lily");
        Goods goods3 = new Goods(3,"裤子","黑","1",300,"10011","lily");
        Goods goods4 = new Goods(4,"衬衫","小码","1",50,"10013","liu");

        Users users1 = new Users("10010","vivi","18676565435","nv");
        Users users2 = new Users("10011","lily","18676565434","nv");
        Users users3 = new Users("10012","hua","18676565490","nv");

        Users users4 = new Users("10011","lily","18676565434","nv");
        Users users5 = new Users("10012","hua","18676565490","nv");

        List<Users> usersList1 = new ArrayList<>();
        usersList1.add(users1);
        usersList1.add(users2);
        usersList1.add(users3);

        List<Users> usersList2 = new ArrayList<>();
        usersList2.add(users4);
        usersList2.add(users5);

        List<Goods> goods = new ArrayList<>();
        goods.add(goods1);
        goods.add(goods2);
        goods.add(goods3);
        goods.add(goods4);

        //System.out.println(JSONObject.toJSONString(TwoToList(goods,usersList1)));
        //System.out.println(listToMap(goods));
        //System.out.println(filter(usersList1,usersList2));
        System.out.println(ListObjectToString(usersList1));
    }

    /**
     * List转换成Map
     * @param goods
     * @return
     */
    public static Map<String, Goods> listToMap(List<Goods> goods){
        Map<String,Goods> listMap = goods.stream().collect(Collectors.toMap(x->x.getUserId(), x->x,(oldVal, newVal) -> newVal));
        return listMap;
    }

    /**
     * 两个list根据Id求交集
     * @param goods
     * @param users
     * @return
     */
    public static List<Goods> TwoToList(List<Goods> goods,List<Users> users) {
        Map<String, Users> listMap = users.stream().collect(Collectors.toMap(x -> x.getUserId(), x -> x, (oldVal, newVal) -> newVal));
        List<Goods> goodsList = goods.stream().map(item -> {
            Goods good = CommonUtil.conventObj(item, Goods.class);
            if (listMap.containsKey(item.getUserId())) {
                return good;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return goodsList;
    }

    /**
     * 筛选listA和listB的交集
     * @param usersA
     * @param usersB
     * @return
     */
    public static List<Users> filter(List<Users> usersA,List<Users> usersB){
        return usersA.stream().filter(item->usersB.contains(item)).collect(Collectors.toList());
    }

    /**
     * 根据List对象的实体对象中的某个字段生成新的list，并用set去重
     * @param users
     * @return
     */
    public static Set ListObjectToString(List<Users> users){
        List<String> stringList = users.stream().map(o->o.getUserId()).collect(Collectors.toList());
        Set set = new HashSet(stringList);
        return set;
    }

    public static List<Student> compareNestedClass(List<Student> students){
        students.stream()
                .map(Student::getStudent)
                .forEach(list -> list.sort((o1,o2)->{
                    if (o1.isEmpty()){
                        return -1;
                    }else if (o2.isEmpty()){
                        return 1;
                    }
                    return orMax(o1.get(0).getSortNum()) - orMax(o2.get(0).getSortNum());
                }));

        return students;
    }


    private static int orMax(Integer n) {
        if (n == null) {
            return Integer.MAX_VALUE;
        }
        return n;
    }

    public static List list(){
        Map<String,User> userMap = new HashMap<>();
        return null;
    }

    @Data
    private static class CarModelInfo {

        private String year;

        private Double price;

    }
}
