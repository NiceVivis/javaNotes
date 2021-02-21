package com.vivi.basic.spring;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class UserTest {

    /**
     * 通过反射在UserController获取UserService   IOC
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        UserController userController = new UserController();

        Class<? extends UserController> clazz = userController.getClass();
         //创建对象
        UserService userService = new UserService();
        System.out.println(userService);

        //获取所有 属性
        Field declaredField = clazz.getDeclaredField("userService");
        //属性为private时，要设置为true
        declaredField.setAccessible(true);
        //只有通过方法才能够设置具体的属性值
        String name = declaredField.getName();

        //拼接方法的名称   UserService
        name =name.substring(0,1).toUpperCase()+name.substring(1,name.length());

        String serMethodName = "set"+name;

        //通过方法注入属性的对象
        Method method = clazz.getMethod(serMethodName,UserService.class);

        //反射
        method.invoke(userController,userService);
        System.out.println(userController.getUserService());

    }

    /**
     * AutoWired注解   IOC
     */
    @Test
    public void test2(){
        UserController userController = new UserController();
        Class<? extends UserController> aClass = userController.getClass();
        UserService userService = new UserService();

        //获取所有的属性值
        Stream.of(aClass.getDeclaredFields()).forEach(field -> {
            String name = field.getName();
            AutoWired annotation = field.getAnnotation(AutoWired.class);
            if (annotation !=null){
                field.setAccessible(true);

                //获取属性的类型
                Class<?> type = field.getType();
                Object o = null;
                try {
                    //得到对象
                    o = type.newInstance();
                    field.set(userController,o);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(userController.getUserService());
    }
}
