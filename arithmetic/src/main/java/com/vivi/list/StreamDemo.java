package com.vivi.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamDemo {
    public static void main(String[] args) {
        User user1 = new User(1,"liu","11111");
        User user2 = new User(2,"qi","11112");
        User user3 = new User(3,"song","11113");
        User user4 = new User(4,"yang","11114");
        User user5 = new User(5,"han","11115");

        User user6 = new User();

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);

        Map map = new HashMap();
        for (User user:userList){
            map.put(user.getId(),user);
        }
        System.out.printf(""+map);

        List<User> vehicleCarVOList = userList.stream().sorted(Comparator.comparing(User::getId).reversed()
                .thenComparing(Comparator.comparing(User::getPhone)))
                //.map(item -> getVehicleCarVO(memberId, item.getVinCode(), item.getHasMain(), item))
                .filter(item -> item != null)
                .collect(Collectors.toList());
        System.out.println("vehicleCarVOList"+vehicleCarVOList);

        List<User> list = userList.stream().
                sorted(Comparator.comparing(User::getId).reversed())   // 按Id倒序排列
                .collect(Collectors.toList());

        System.out.println(list);

        //list
        list = userList.stream().
                sorted(Comparator.comparing(User::getId).reversed()   // 按Id倒序排列
                .thenComparing(Comparator.comparing(User::getId)))
                .filter(item -> item !=null)
                .collect(Collectors.toList());

        User user7 = new User(11,"lily","111234");
        String str = Optional.ofNullable(user7).map(User::getName).orElse("");
        System.out.println(str);

        User user = new User();
        //user.setPhone(ConvertUtil.getFieldWhenNotEmpty(userList.get(0),u ->u.getPhone()));
        System.out.println(user);
        String seriesCode = "2";

    }
}
