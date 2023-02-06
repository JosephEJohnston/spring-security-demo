package com.noob.demo.config;

import com.noob.demo.handle.MyAccessDeniedHandler;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
public class SecurityConfig {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PersistentTokenRepository persistentTokenRepository;

    private WebExpressionAuthorizationManager getWebExpressionAuthorizationManager(final String expression) {
        final var expressionHandler = new DefaultHttpSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        final var authorizationManager = new WebExpressionAuthorizationManager(expression);
        authorizationManager.setExpressionHandler(expressionHandler);
        return authorizationManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 表单提交
        httpSecurity.formLogin()
                // 参数必须和表单一致
                //.usernameParameter("username123")
                //.passwordParameter("password123")

                // 当发现 /login 时认为是登录，必须和表单提交地址一样，去执行 UserDetailsServiceImpl#loadUserByUsername
                .loginProcessingUrl("/login")

                // 自定义登录页面
                //.loginPage("/login.html")
                .loginPage("/showLogin")

                // 登录成功后跳转页面，必须是 Post 请求
                .successForwardUrl("/toMain")

                // 自定义认证成功跳转
                // 不能和 successForwardUrl 共存
                //.successHandler(new MyAuthenticationSuccessHandler("https://www.baidu.com"))

                // 登录失败后跳转页面，Post 请求
                .failureForwardUrl("/toError");

                // 不能和 failureForwardUrl 共存
                //.failureHandler(new MyAuthenticationFailureHandler("/error.html"));

        // 授权认证
        httpSecurity.authorizeHttpRequests(
                (auth) -> auth
                        // login.html 即 error.html 不需要被认证
                        .requestMatchers("/login.html", "/error.html", "/toError", "/showLogin").permitAll()

                        .requestMatchers("/js/**", "/css/**", "/image/**").permitAll()

                        // 允许匿名访问，请求进入拦截链中，略类似 permitAll
                        //.anyRequest().anonymous()

                        // 拒绝所有对应访问请求
                        //.anyRequest().denyAll()

                        // 完全认证，勾选记住我也不能给你访问，只能一步步通过用户名密码登录后才可访问
                        //.anyRequest().fullyAuthenticated()

                        // “记住我”，所有勾选了记住我的用户才可进行访问
                        //.anyRequest().rememberMe()

                        // 访问对应资源需要对应权限，区分大小写
                        //.requestMatchers("/main1.html").hasAuthority("admin")
                        //.requestMatchers("/main1.html").hasAnyAuthority("admin", "normal")

                        // 访问对应资源需要角色，区分大小写
                        //.requestMatchers("/main1.html").hasRole("abc")
                        //.requestMatchers("/main1.html").hasAnyRole("abc", "def")

                        //.anyRequest().access(hasIpAddress("127.0.0.1"))

                        /*
                         * 这些 hasAuthority，denyAll 等等方法，底层都是 access
                         */
                        //.requestMatchers("").access(AuthenticatedAuthorizationManager.fullyAuthenticated())

                        //.requestMatchers("/**").access(getWebExpressionAuthorizationManager("@webSecurity.check(authentication,request)")));

                        // 自定义方法权限控制
                        //.anyRequest().access(new WebExpressionAuthorizationManager("@myServiceImpl.hasPermission(request, authentication)")))

                        // 所有请求都必须被认证
                        .anyRequest().authenticated());
                // .httpBasic(Customizer.withDefaults());

        // 关闭 csrf 防护
        /*
         * CSRF(Cross-site request forgery) 跨站请求伪造
         *      通过伪造用户请求访问受信任站点的非法请求访问
         * 跨域：只要网络协议，ip 地址，端口中任何一个不相同就是跨域请求
         * 在跨域的情况下，session id 可能被第三方恶意劫持
         *      通过这个 session id 向服务端发起请求时，服务端会认为这个请求是合法的
         */
        // 启动后，要求请求参数携带 _csrf 参数，参数值为 token
        // httpSecurity.csrf().disable();

        // 异常处理
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);

        // RememberMe 功能
        httpSecurity.rememberMe()
                // 失效时间，单位秒
                .tokenValiditySeconds(60)
                // 设置参数
                //.rememberMeParameter("")
                // 自定义登录逻辑
                .userDetailsService(userDetailsService)
                // 持久层对象
                .tokenRepository(persistentTokenRepository);

        // 退出登录
        httpSecurity.logout()
                // 内部实际调用 LogoutHandler
                //.addLogoutHandler()

                // 自定义退出接口名
                //.logoutUrl("/user/logout")

                // 退出登录跳转页面
                .logoutSuccessUrl("/login.html");

        return httpSecurity.build();
    }

    private static AuthorizationManager<RequestAuthorizationContext> hasIpAddress(String ipAddress) {
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(ipAddress);
        return (authentication, context) -> {
            HttpServletRequest request = context.getRequest();
            return new AuthorizationDecision(ipAddressMatcher.matches(request));
        };
    }
}
