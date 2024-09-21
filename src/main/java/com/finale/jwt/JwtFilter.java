package com.finale.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finale.common.ApiResponse;
import com.finale.exception.JwtTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtProvider.getTokenFromHeader(request);

            if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
                log.info("JwtFilter 조건 통과");
                jwtProvider.setAuthentication(jwtProvider.getTokenInfo(token));
                response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            }
            filterChain.doFilter(request, response);
        } catch (JwtTokenException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ApiResponse apiResponse = ApiResponse.unauthorizedResponse(e.getMessage());
            response.getWriter().write(
                    new ObjectMapper()
                            .registerModule(new JavaTimeModule())
                            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                            .writeValueAsString(apiResponse)
            );
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/login") ||
                request.getServletPath().startsWith("/logout") ||
                request.getServletPath().startsWith("/jwt") ||
                request.getServletPath().startsWith("/api/location/list") ||
                request.getServletPath().startsWith("/api/lesson")
                ;
    }
}
