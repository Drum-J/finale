package com.finale.login.controller;

import com.finale.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    private static final String STUDENT = "student";
    private static final String COACH = "coach";

    @GetMapping("/student")
    public void studentLogin(HttpServletResponse response) throws IOException {
        String uri = loginService.redirectUri(STUDENT);
        response.sendRedirect(uri);
    }

    @GetMapping("/coach")
    public void coachLogin(HttpServletResponse response) throws IOException {
        String uri = loginService.redirectUri(COACH);
        response.sendRedirect(uri);
    }

    @GetMapping("/callback/student")
    public void studentCallback(@RequestParam(name = "code") String code, HttpServletResponse response) {
        loginService.getAccessToken(code, response, STUDENT);
    }

    @GetMapping("/callback/coach")
    public void coachCallback(@RequestParam(name = "code") String code, HttpServletResponse response) {
        loginService.getAccessToken(code, response, COACH);
    }
}
