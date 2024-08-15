package com.finale.config;

import com.finale.filter.CoachAccessFilter;
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
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
        return http
                .cors(corsCustom -> corsCustom.configurationSource(request -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(
                            List.of("http://localhost:3000"
                                    ,"https://localhost:3000"
                                    ,"https://finale-web.vercel.app"
                                    ,"https://finale-api.shop")); //React Server
                    corsConfig.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfig.setAllowedMethods(Collections.singletonList("*"));
                    corsConfig.setAllowCredentials(true);
                    corsConfig.setMaxAge(3600L);
                    corsConfig.setExposedHeaders(List.of(SET_COOKIE, AUTHORIZATION));

                    return corsConfig;
                })) // cors 설정 끝
                .csrf(AbstractHttpConfigurer::disable) // csrf disable
                .formLogin(AbstractHttpConfigurer::disable) // formLogin disable
                .sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세선 상태 변경
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/api/coach/depositConfirm/**",
                                "/api/coach/updateRole/**").hasAnyAuthority("MASTER")
                        .requestMatchers(
                                "/api/coach/**",
                                "/api/schedule/enrollment/**",
                                "/api/schedule/restLesson/**",
                                "/api/upload/**").hasAnyAuthority("MASTER", "SUB")
                        .requestMatchers("/api/student/**").hasAnyAuthority("STUDENT")
                        .anyRequest().permitAll() // 우선 모든 접근 허용으로 설정
                )
                .addFilterBefore(new JwtFilter(jwtProvider), BasicAuthenticationFilter.class)
                //.addFilterAfter(new CoachAccessFilter(), JwtFilter.class)
                .build();
    }
}
