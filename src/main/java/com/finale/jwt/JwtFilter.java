package com.finale.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.getTokenFromHeader(request);

        if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
            log.info("JwtFilter 조건 통과");
            jwtProvider.setAuthentication(jwtProvider.getTokenInfo(token));
            response.setHeader(HttpHeaders.AUTHORIZATION,"Bearer " + token);
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/login") ||
                request.getServletPath().startsWith("/logout");
    }
}
