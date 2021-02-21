package com.vivi.basic.list;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * java新特性 ：Optional
 *  Optional可以包含null或者非null容器，其中最基本的两个操作就是isPresent()和get()
 *  isPresent()判断当前value是否为null
 *  get()返回value
 *  Optional.get() 前不事先用 isPresent() 检查值是否可用. 假如 Optional 不包含一个值, get() 将会抛出一个异常
 *
 */
public class OptionalDemo {

    @Test
    public void OptionTest(){
        //of方法通过工厂方法创建Optioanl类，创建对象时传入的参数不能为null，如果传入参数为null，则抛空指针异常
        Optional<String> optional = Optional.of("aa");

        //of和ofNullable的用法相似，但是可以接收参数为null的情况
        Optional empty = Optional.ofNullable(null);

        //Optional的过滤方法，返回的为Optional对象
        Optional<String> optionalS = optional.filter(s->s.startsWith("a"));
        System.out.println("optionalS1: "+optionalS);

        //Optional的map方法，返回的为Optional对象
        Optional<Integer> optionalMap = optional.map(s -> s.toUpperCase()).map(s -> s.length());
        System.out.println("optionalMap:"+optionalMap);

        //isPresent，如果value存在则返回true，否则返回false
        if (optional.isPresent()){
            System.out.println(optional.get());
        }

        //ifPresent 如果Optional实例有值则为其调用consumer，否则不做处理，
        // 如果Optional实例有值，调用ifPresent()可以接受接口段或lambda表达式
        optional.ifPresent((value) ->{
            System.out.println("value: "+value.length());
        });

        // orElse 如果有值则将其返回，否则返回指定的其它值。
        System.out.println(empty.orElse("空的"));

        Optional<Optional> str = Optional.ofNullable(empty);
        System.out.println("str"+str);

        String firstDate = "2020-09-04 00:00:00";
        String[] date = firstDate.split(" ");
        AtomicReference<String> value = new AtomicReference<>("");
        Optional.ofNullable(date).ifPresent(v -> value.set(date[0]));
        System.out.println("date = "+value);
    }
}
