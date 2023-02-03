package com.noob.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String index() {
        return "redirect:main.html";
    }

    @PostMapping("login")
    public String login() {
        System.out.println("执行登录方法");
        return "redirect:main.html";
    }

}
