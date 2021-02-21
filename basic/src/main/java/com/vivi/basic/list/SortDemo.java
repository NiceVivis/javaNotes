package com.vivi.basic.list;

import com.vivi.basic.list.Student;
import com.vivi.basic.list.User;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortDemo {

    /**
     * 获取list中相同name，最新日期的记录
     */
    @Test
    public void comparingTest(){
        List<Cars> cars = new ArrayList<>();
        Cars car1 = new Cars("1","CC7700",10,"2020");
        Cars car2 = new Cars("2","GG5588",10,"2021");
        Cars car3 = new Cars("3","VV9900",10,"2021");
        Cars car4 = new Cars("4","SS9955",10,"2021");
        Cars car5 = new Cars("5","CC7700",10,"2021");
        Cars car6 = new Cars("6","CC7700",10,"2018");
        Cars car7 = new Cars("7","VV9900",10,"2016");
        Cars car8 = new Cars("8","GG5588",10,"2016");
        Cars car9 = new Cars("9","VV9900",10,"2020");
        Cars car10 = new Cars("10","SS9955",10,"2015");
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        cars.add(car6);
        cars.add(car7);
        cars.add(car8);
        cars.add(car9);
        cars.add(car10);

        Map<String, List<Cars>> collect = cars.stream()
                .sorted(Comparator.comparing(Cars::getYear).reversed())
                .collect(Collectors.groupingBy(Cars::getName));

        List<Cars> carsList = new ArrayList<>();
        for (String key:collect.keySet()){
            carsList.add(collect.get(key).get(0));
        }

        System.out.printf("carsList = "+carsList);

    }


    @Test
    public void sortTest(){
        Student student1 = new Student("1","2",90,4,new User("1","2",10,"11"),null,"");
        Student student2 = new Student("2","3",840,2,new User("4","2",10,"11"),null,"");
        Student student3 = new Student("3","4",80,1,new User("3","2",10,"11"),null,"");
        Student student4 = new Student("4","7",90,3,new User("2","2",10,"11"),null,"");
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);


        // 根据对象的子对象中的字段排序 1
        students.stream().forEach(student -> student.setUserId(student.getUser().getId()));
        List<Student> list = students.stream().sorted(Comparator.comparing(Student::getUserId).reversed()).collect(Collectors.toList());
        System.out.println("排序后："+list);
        students.sort(Comparator.comparing(Student::getUserId).reversed());
        System.out.println("排序后："+students);

        // 根据对象的子对象中的字段排序 2
        List<Student> studentList = students.stream()
                .sorted(Comparator.comparing((Function<Student,String>)student -> student.getUser().getId()).reversed())
                .collect(Collectors.toList());

        // 根据对象的子对象中的字段排序 排序字段值为空，空值排在前面
        List<Student> studentList1 = students.stream()
                .sorted(Comparator.comparing((Function<Student,String>)student -> student.getUser().getId(),Comparator.nullsLast(String::compareTo)).reversed())
                .collect(Collectors.toList());

        // 根据对象的子对象中的字段排序 排序字段值为空，空值排在后面
        List<Student> studentList2 = students.stream()
                .sorted(Comparator.comparing((Function<Student,String>)student -> student.getUser().getId(),Comparator.nullsFirst(String::compareTo)).reversed())
                .collect(Collectors.toList());
        System.out.println("排序后："+studentList);

        User user1 = new User("1","2",10,"11");
        User user2 = new User("4","2",8,"11");
        User user3 = new User("3","2",10,"11");
        User user4 = new User("2","2",20,"11");
        User user5 = new User("","2",20,"11");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);

        //排序1  lambda

//        List<User> users = userList.stream().sorted(Comparator.comparing(User::getId,Comparator.nullsLast(String::compareTo)).reversed())
//                .collect(Collectors.toList());  //比较字段为空时，可以用Comparator.nullsLast(String::compareTo)  对该为努力了的

        List<User> users = userList.stream().sorted(Comparator.comparing(User::getId).reversed())
                .collect(Collectors.toList());
        System.out.println("lambda 排序后："+users);

        // 排序2 compare内部方法
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                //降序，
                return o2.getId().compareTo(o1.getId());
            }
        });

        System.out.println("Collections 排序后："+userList);

        // 排序3 compare内部方法 简写
        Collections.sort(userList, (o1, o2) -> {
            //降序，
            return o2.getId().compareTo(o1.getId());
        });

        // 排序4 compare内部方法
        Collections.sort(userList, (o1, o2) -> o2.getId().compareTo(o1.getId()));

        System.out.println("compareTo 排序后："+userList);

    }

}
