package com.noob.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String index() {
        return "redirect:main.html";
    }

    //@Secured("ROLE_abc")
    @PreAuthorize("hasRole('abc')") // 允许 ROLE_ 开头，配置类不允许 ROLE_ 开头
    @PostMapping("/toMain")
    public String toMain() {
        return "redirect:main.html";
    }

    @PostMapping("/toError")
    public String toError() {
        return "redirect:error.html";
    }
}
