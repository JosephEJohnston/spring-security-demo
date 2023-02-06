package com.noob.demo.service;

import jakarta.annotation.Resource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查询数据库判断用户名是否存在
        // 如果不存在则抛出 UsernameNotFoundException 异常
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        // 2. 把查询出来的密码（注册时已经加密过）进行解析，或者直接把密码放入构造方法
        String password = passwordEncoder.encode("123");

        // 带 ROLE_ 的是角色，否则是权限
        return new User(username, password, AuthorityUtils
                .commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc"));
    }
}
