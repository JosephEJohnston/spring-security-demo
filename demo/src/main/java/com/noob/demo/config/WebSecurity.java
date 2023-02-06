package com.noob.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 没用。
 */
@Service
public class WebSecurity {
    public Boolean checkUserId(Authentication authentication, HttpServletRequest request) {
        return false;
    }
}