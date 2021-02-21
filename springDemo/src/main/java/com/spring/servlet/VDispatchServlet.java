package com.spring.servlet;

import com.spring.annotation.VAutowired;
import com.spring.annotation.VController;
import com.spring.annotation.VRequestMapping;
import com.spring.annotation.VService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class VDispatchServlet extends HttpServlet {

    /**
     * 属性配置文件
     */
    private Properties properties = new Properties();

    private List<String> classNameList = new ArrayList<>();

    /**
     * IOC容器
     */
    Map<String,Object> iocMap = new HashMap<>();

    Map<String, Method> handlerMapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        this.doPost(request,response);
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1、加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2、扫描相关的类
        doScanner(properties.getProperty("scan-package"));

        //3、初始化IOC容器，将所有相关的类实例保存到IOC容器中
        doInstance();

        //4、依赖注入
        doAutowired();

        //5、初始化HandlerMapping
        initHandlerMapping();

        System.out.println("XSpring FrameWork is init.");

        //6、打印数据
        doTestPrintData();
    }

    /**
     * 1、加载配置文件
     */
    private void doLoadConfig(String contextConfig){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(contextConfig);

        try {
            properties.load(inputStream);
            System.out.println("property file has bean saved in contextCOnfig");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 2、扫描相关的类
     * @param scanPackage
     */
    private void doScanner(String scanPackage){
        URL resourcePath = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));
        if (resourcePath == null){
            return;
        }
        File classPath = new File(resourcePath.getFile());

        for (File file : classPath.listFiles()){
            if (file.isDirectory()){
                System.out.println("[INFO-2] {" + file.getName() + "} is a directory.");
                //子目录递归
                doScanner(scanPackage+"."+file);
            }else {
                if (!file.getName().endsWith(".class")){
                    System.out.println("[INFO-2] {" + file.getName() + "} is not a class file.");
                    continue;
                }
                String className = (scanPackage+"."+file.getName()).replace(".class","");
                //保存在内容
                classNameList.add(className);

                System.out.println("[INFO-2] {" + className + "} has been saved in classNameList.");
            }
        }
    }

    private String toLowerFirstCase(String className){
        char[] charArray = className.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }

    /**
     * 3、初始化IOC容器，将所有的相关的类实例保存到IOC容器
     */
    private void doInstance(){
        if (classNameList.isEmpty()){
            return;
        }
        try {
            for (String className:classNameList){
                 Class<?> clazz = Class.forName(className);
                 //clazz.isAnnotationPresent(VController.class),判断VController是否在clazz上，如果在则返回true，不在则返回false
                 if (clazz.isAnnotationPresent(VController.class)){
                     //getSimpleName 获取类的简写名称
                     String beanName = toLowerFirstCase(clazz.getSimpleName());
                     Object instance = clazz.newInstance();
                     iocMap.put(beanName,instance);
                     System.out.println("[INFO-3] {" + beanName + "} has been saved in iocMap.");
                 }else if (clazz.isAnnotationPresent(VService.class)){
                     String beanName = toLowerFirstCase(clazz.getSimpleName());

                     // 如果注解包含自定义名称
                     VService vService = clazz.getAnnotation(VService.class);
                     if (!"".equals(vService.value())) {
                         beanName = vService.value();
                     }
                     Object instance = clazz.newInstance();
                     iocMap.put(beanName,instance);
                     System.out.println("[INFO-3] {" + beanName + "} has been saved in iocMap.");

                     for (Class<?> i:clazz.getInterfaces()){
                         if (iocMap.containsKey(i.getName())){
                             throw new Exception("The Bean Name Is Exist.");
                         }
                         iocMap.put(i.getName(),instance);
                         System.out.printf("[INFO-3] {" + i.getName() + "} has been saved in iocMap.");
                     }
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 4、依赖注入
     */
    private void doAutowired(){
        if (iocMap.isEmpty()){
            return;
        }
        for (Map.Entry<String,Object> entry:iocMap.entrySet()){
            Field[] fields = (Field[]) entry.getValue();
            for (Field field:fields){
                if (!field.isAnnotationPresent(VAutowired.class)){
                    continue;
                }
                System.out.println("[INFO-4] Existence XAutowired.");

                // 获取注解对应的类
                VAutowired vAutowired = field.getAnnotation(VAutowired.class);
                String beanName = vAutowired.value().trim();

                // 获取VAutowired 注解的值
                if ("".equals(beanName)){
                    System.out.println("[INFO] xAutowired.value() is null");
                }

                //只要加了注解，都要加载，不管private还是protect
                field.setAccessible(true);

                try {
                    field.set(entry.getValue(),iocMap.get(beanName));
                    System.out.println("[INFO-4] field set {" + entry.getValue() + "} - {" + iocMap.get(beanName) + "}.");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void initHandlerMapping(){
        if (iocMap.isEmpty()){
            return;
        }
        for (Map.Entry<String,Object> entry:iocMap.entrySet()){
            Class<?> clazz = entry.getValue().getClass();

            if (clazz.isAnnotationPresent(VController.class)){
                continue;
            }

            String baseUrl = "";

            if (clazz.isAnnotationPresent(VRequestMapping.class)){
                VRequestMapping vRequestMapping = clazz.getAnnotation(VRequestMapping.class);
                baseUrl = vRequestMapping.value();
            }

            for (Method method:clazz.getMethods()){
                if (!method.isAnnotationPresent(VRequestMapping.class)){
                    continue;
                }
                VRequestMapping vRequestMapping = method.getAnnotation(VRequestMapping.class);
                String url = ("/"+baseUrl+"/"+vRequestMapping.value()).replaceAll("/+","/");
                handlerMapping.put(url,method);
                System.out.println("[INFO-5] handlerMapping put {" + url + "} - {" + method + "}.");
            }
        }
    }

    /**
     * 6、打印数据
     */
    private void doTestPrintData() {

        System.out.println("[INFO-6]----data------------------------");

        System.out.println("contextConfig.propertyNames()-->" + properties.propertyNames());

        System.out.println("[classNameList]-->");
        for (String str : classNameList) {
            System.out.println(str);
        }

        System.out.println("[iocMap]-->");
        for (Map.Entry<String, Object> entry : iocMap.entrySet()) {
            System.out.println(entry);
        }

        System.out.println("[handlerMapping]-->");
        for (Map.Entry<String, Method> entry : handlerMapping.entrySet()) {
            System.out.println(entry);
        }

        System.out.println("[INFO-6]----done-----------------------");

        System.out.println("====启动成功====");
        System.out.println("测试地址：http://localhost:8080/test/query?username=xiaopengwei");
        System.out.println("测试地址：http://localhost:8080/test/listClassName");
    }









}
