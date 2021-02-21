package com.sso.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author yangwei
 * @date 2021/1/25 6:09 下午
 */
@Controller
public class LoginController {

    @PostMapping(value = "doLogin")
    public String doLogin(){
        return null;
    }
}
