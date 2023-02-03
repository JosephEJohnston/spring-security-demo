package com.noob.demo.handle;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 设置响应状态码
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("Content-Type", "application/json;charset=utf-8");

        PrintWriter writer = response.getWriter();

        writer.write("{\"status\":\"error\", \"msg\":\"权限不足，请联系管理员\"}");
        writer.flush();
        writer.close();
    }
}
