/**
 * 比较核心的类：
 * <p>{@link org.springframework.security.core.userdetails.UserDetailsService}</p>
 * <p>{@link org.springframework.security.core.userdetails.User}</p>
 * <p>{@link org.springframework.security.core.userdetails.UserDetails}</p>
 * <p>{@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}</p>
 * <p>{@link org.springframework.security.core.Authentication}</p>
 *
 * <p>
 *     成功后跳转：
 *     <p>{@link org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler}</p>
 *     <p>{@link org.springframework.security.web.authentication.AuthenticationSuccessHandler}</p>
 * </p>
 */

package com.noob.demo;

/*
 * Oauth2 认证：
 * Oauth2 提供了用户认证（开放资源授权）的标准
 * 见图：static/Oauth2.png
 *
 * 授权模式：
 *  1. 授权码模式，最复杂，最安全，用得最多
 *      见图：static/授权码模式.png
 *  2. 简化授权模式
 *      见图：static/简化授权模式.png
 *  3. 密码模式
 *      见图：static/密码模式.png
 *  4. 客户端模式
 *      见图：static/客户端模式.png
 *
 * 刷新令牌：
 *  见图：static/刷新令牌.png
 *
 * 授权服务器：
 *  static/授权服务器.png
 *
 * 架构：
 *  static/SpringOauth2架构.png
 */
