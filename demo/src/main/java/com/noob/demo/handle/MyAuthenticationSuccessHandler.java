package com.noob.demo.handle;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String utl;

    public MyAuthenticationSuccessHandler(String utl) {
        this.utl = utl;
    }

    // 认证成功后跳转
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        System.out.println(user.getUsername());
        // 输出 null
        System.out.println(user.getPassword());
        System.out.println(user.getAuthorities());

        response.sendRedirect(utl);
    }
}
