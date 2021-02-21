package com.vivi.basic.list;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ListDemo {

    @Test
    public void TwoList(){
    }

    public List<List<User>> init(){
        List<String> list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");
        List<String> list2 = new ArrayList();
        list2.add("3333");
        list2.add("4444");
        list2.add("5555");

        List<User> list = new ArrayList<>();
        List<User> list3 = new ArrayList<>();
        List<User> list4 = new ArrayList<>();

        list3.add(new User("1","2015",20,""));
        list3.add(new User("3","2014",25,""));
        list3.add(new User("2","2021",15,""));
        list3.add(new User("4","2020",20,""));

        list4.add(new User("2","16",30,""));
        list4.add(new User("1","16",25,""));
        list4.add(new User("3","222",15,""));
        list4.add(new User("4","19",20,""));

        list.add(new User("5","16",20,"",list3));
        list.add(new User("6","16",25,"",list4));

        List<List<User>> lists = new ArrayList<>();
        lists.add(list);
        return lists;
    }

    @Test
    public void sortList(){

        List<String> list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");
        List<String> list2 = new ArrayList();
        list2.add("3333");
        list2.add("4444");
        list2.add("5555");

        List<String> intersection = list1.stream().filter(item->list2.contains(item)).collect(Collectors.toList());

        List<User> list = new ArrayList<>();
        List<User> list3 = new ArrayList<>();
        List<User> list4 = new ArrayList<>();

        list3.add(new User("1","2015",20,""));
        list3.add(new User("3","2014",25,""));
        list3.add(new User("2","2021",15,""));
        list3.add(new User("4","2020",20,""));

        list4.add(new User("2","16",30,""));
        list4.add(new User("1","16",25,""));
        list4.add(new User("3","222",15,""));
        list4.add(new User("4","19",20,""));
        //List<User> users = list4.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());

        list.add(new User("5","16",20,"",list3));
        list.add(new User("6","16",25,"",list4));

        List<List<User>> lists = new ArrayList<>();
        lists.add(list);


        System.out.println(""+lists);

        list3.sort(Comparator.comparing(User::getName).reversed());
        System.out.println("list3:"+list3);

        System.out.println("lists"+lists);
        List<User> useList = new ArrayList<>();
        for (int i = 0;i<lists.size();i++){
            for (int k = 0;k<lists.get(i).size();k++) {
                for (int j = 0; j < lists.get(i).get(k).getUserList().size(); j++) {
                    useList.add(lists.get(i).get(k).getUserList().get(j));
                }
            }
        }

        System.out.println(useList);

        for (int i =0;i<useList.size();i++){
            for (int j = i+1;j<useList.size();j++) {
                if (useList.get(i).getId() == useList.get(j).getId()){
                    if (useList.get(i).getValue().equals(useList.get(j).getValue())) {
                        useList.get(j).setRemark("1");
                    }
                }
            }
        }


    }

}
