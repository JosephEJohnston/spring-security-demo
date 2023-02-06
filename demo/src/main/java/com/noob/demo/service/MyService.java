package com.noob.demo.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface MyService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
