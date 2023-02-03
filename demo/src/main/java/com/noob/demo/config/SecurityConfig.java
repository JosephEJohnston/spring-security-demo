package com.noob.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 表单提交
        httpSecurity.formLogin()
                // 当发现 /login 时认为是登录，必须和表单提交地址一样，去执行 UserDetailsServiceImpl#loadUserByUsername
                .loginProcessingUrl("/login")
                // 自定义登录页面
                .loginPage("/login.html")
                // 登录成功后跳转页面，必须是 Post 请求
                .successForwardUrl("/toMain")
                // 登录失败后跳转页面，Post 请求
                .failureForwardUrl("/toError");

        // 授权认证
        httpSecurity.authorizeHttpRequests(
                (auth) -> auth
                        // login.html 即 error.html 不需要被认证
                        .requestMatchers("/login.html", "/error.html", "/toError").permitAll()
                        // 所有请求都必须被认证
                        .anyRequest().authenticated());
                // .httpBasic(Customizer.withDefaults());

        // 关闭 csrf 防护
        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }
}
