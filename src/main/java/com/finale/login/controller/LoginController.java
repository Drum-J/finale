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
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{type}")
    public void studentLogin(HttpServletResponse response, @PathVariable("type") String type) throws IOException {
        String uri = kakaoAPIService.redirectUri(type);
        response.sendRedirect(uri);
    }

    @ResponseBody
    @GetMapping("/callback")
    public String studentCallback(@RequestParam(name = "code") String code,
                                  @RequestParam(name = "state") String type,
                                  HttpServletResponse response) {
        try {
            KakaoUserInfo userInfo = kakaoAPIService.getAccessToken(code);
            String token;
            if (type.equals(STUDENT)) {
                token = loginService.loginStudent(userInfo);
            } else if (type.equals(COACH)) {
                token = loginService.loginCoach(userInfo);
            } else {
                return "ERROR";
            }

            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return "SUCCESS : " + token;
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
