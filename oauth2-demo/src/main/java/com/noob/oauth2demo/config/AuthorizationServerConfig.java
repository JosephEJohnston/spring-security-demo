package com.noob.oauth2demo.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 授权服务器配置
 */
@Configuration
public class AuthorizationServerConfig {
    public static final String CLIENT_ID = "registration-id";

    @Resource
    private PasswordEncoder passwordEncoder;

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrationRepository() {
        var clientRegistration = ClientRegistration.withRegistrationId(CLIENT_ID)
                //
                .authorizationUri("/**")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientId("admin")
                .clientSecret(passwordEncoder.encode("112233"))
                // 用于授权成功后跳转
                .redirectUri("http://www.baidu.com")
                // 配置申请的授权范围
                .scope("all")
                // 配置 grant_type，表示授权类型
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                //
                .tokenUri("/**")
                .build();
        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
    }

    /**
     * <a href="https://stackoverflow.com/questions/58982286/spring-security-5-replacement-for-oauth2resttemplate">spring-security-5-replacement-for-oauth2resttemplate</a>
     */
    @Bean
    public WebClient testWebClient(ReactiveClientRegistrationRepository clientRegistrationRepo) {
        var clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrationRepo);
        var clientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrationRepo, clientService);
        var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientManager);
        oauth.setDefaultClientRegistrationId(CLIENT_ID);

        return WebClient.builder()
                .filter(oauth)
                .build();
    }
}
