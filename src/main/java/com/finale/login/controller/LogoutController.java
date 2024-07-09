package com.finale.login.controller;

import com.finale.common.ApiResponse;
import com.finale.login.service.KakaoAPIService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/logout")
public class LogoutController {

    private final KakaoAPIService kakaoAPIService;

    @GetMapping("/withKakao")
    public void logout(HttpServletResponse response) throws IOException {
        String uri = kakaoAPIService.logoutWithKakao();
        response.sendRedirect(uri);
    }


    @GetMapping("/finale")
    public ApiResponse logoutFinale() {
        log.info("=== 로그아웃 처리 ===");
        SecurityContextHolder.clearContext();
        return ApiResponse.successResponse("로그아웃 완료");
    }
}
