package com.finale.login.controller;

import com.finale.login.dto.KakaoUserInfo;
import com.finale.login.service.KakaoAPIService;
import com.finale.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final KakaoAPIService kakaoAPIService;
    private final LoginService loginService;

    private static final String STUDENT = "student";
    private static final String COACH = "coach";

    @GetMapping("/student")
    public void studentLogin(HttpServletResponse response) throws IOException {
        String uri = kakaoAPIService.redirectUri(STUDENT);
        response.sendRedirect(uri);
    }

    @GetMapping("/coach")
    public void coachLogin(HttpServletResponse response) throws IOException {
        String uri = kakaoAPIService.redirectUri(COACH);
        response.sendRedirect(uri);
    }

    @ResponseBody
    @GetMapping("/callback/student")
    public String studentCallback(@RequestParam(name = "code") String code, HttpServletResponse response) throws IOException {
        try {
            KakaoUserInfo studentInfo = kakaoAPIService.getAccessToken(code, response, STUDENT);
            String token = loginService.loginStudent(studentInfo);
            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return "SUCCESS : " + token;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @ResponseBody
    @GetMapping("/callback/coach")
    public String coachCallback(@RequestParam(name = "code") String code, HttpServletResponse response) throws IOException {
        try {
            KakaoUserInfo coachInfo = kakaoAPIService.getAccessToken(code, response, COACH);
            String token = loginService.loginCoach(coachInfo);
            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return "SUCCESS : " + token;
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
