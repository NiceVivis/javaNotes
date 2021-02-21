package com.sso.client.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 * @date 2021/1/25 6:16 下午
 */
public class HelloController {

    /**
     * 无需登录就可访问
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }


    @GetMapping(value = "/employees")
    public String employees(Model model, HttpSession session) {
        List<String> emps = new ArrayList<>();

        Object loginUser = session.getAttribute("loginUser");

        if (loginUser == null) {
            //没登录,跳转到服务器登录
        } else {
            emps.add("张三");
            emps.add("李四");
        }
        model.addAttribute("emps",emps);
        return "employees";
    }
}
