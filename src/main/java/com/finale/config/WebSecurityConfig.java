package com.finale.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
        return http
                .cors(corsCustom -> corsCustom.configurationSource(request ->{
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); //React Server
                    corsConfig.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfig.setAllowedMethods(Collections.singletonList("*"));
                    corsConfig.setAllowCredentials(true);
                    corsConfig.setMaxAge(3600L);

                    return corsConfig;
                })) // cors 설정 끝
                .build();
    }
}
