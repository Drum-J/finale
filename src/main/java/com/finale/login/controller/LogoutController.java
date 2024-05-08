package com.finale.login.controller;

import com.finale.login.service.KakaoAPIService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    public String logoutFinale() {
        return "로그아웃 완료";
    }
}
