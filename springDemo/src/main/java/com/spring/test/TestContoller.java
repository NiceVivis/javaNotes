package com.spring.test;

import com.spring.annotation.VController;
import com.spring.annotation.VRequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@VController
@VRequestMapping("/test")
public class TestContoller {

    @Autowired
    TestService service;

    @VRequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response){
        if (request.getParameter("username") == null){
            try {
                response.getWriter().write("param username is null");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            String paramName = request.getParameter("username");
            try {
                response.getWriter().write("param username is "+paramName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[INFO-req] New request param username-->" + paramName);
        }
    }

    @VRequestMapping("/listClassName")
    public void listClassName(HttpServletRequest request, HttpServletResponse response){
        String str = service.listClassName();
        System.out.println("Service----------=-=-=>" + str);
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
