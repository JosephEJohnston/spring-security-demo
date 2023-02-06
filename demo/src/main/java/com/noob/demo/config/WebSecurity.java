package com.noob.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * not working
 */
@Service
public class WebSecurity {
    public Boolean check(Authentication authentication, HttpServletRequest request) {
        return true;
    }
}
