package com.finale.config;

import com.finale.jwt.JwtFilter;
import com.finale.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtProvider jwtProvider;

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
                    corsConfig.setExposedHeaders(List.of(AUTHORIZATION));

                    return corsConfig;
                })) // cors 설정 끝
                .csrf(AbstractHttpConfigurer::disable) // csrf disable
                .formLogin(AbstractHttpConfigurer::disable) // formLogin disable
                .sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세선 상태 변경
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/lesson/depositConfirm/**").hasAnyAuthority("MASTER")
                        .requestMatchers("/api/lesson/**").hasAnyAuthority("MASTER","SUB")
                        .anyRequest().permitAll() // 우선 모든 접근 허용으로 설정
                )
                .addFilterBefore(new JwtFilter(jwtProvider), BasicAuthenticationFilter.class)
                .build();
    }
}