package com.finale.login.controller;

import com.finale.common.ApiResponse;
import com.finale.common.CookieUtil;
import com.finale.login.dto.KakaoUserInfo;
import com.finale.login.service.KakaoAPIService;
import com.finale.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final KakaoAPIService kakaoAPIService;
    private final LoginService loginService;

    private static final String STUDENT = "student";
    private static final String COACH = "coach";

    @GetMapping("/{type}")
    public ApiResponse studentLogin(@PathVariable("type") String type) {
        return ApiResponse.successResponse(kakaoAPIService.redirectUri(type));
    }

    @ResponseBody
    @GetMapping("/callback")
    public ApiResponse studentCallback(@RequestParam(name = "code") String code,
                                  @RequestParam(name = "state") String type,
                                  HttpServletResponse response) throws Exception {
        KakaoUserInfo userInfo = kakaoAPIService.getAccessToken(code);
        String token;
        if (type.equals(STUDENT)) {
            token = loginService.loginStudent(userInfo);
        } else if (type.equals(COACH)) {
            token = loginService.loginCoach(userInfo);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ApiResponse.errorResponse("Type 값이 올바르지 않습니다.");
        }

        CookieUtil.addCookie(response, token);
        return ApiResponse.successResponse(token);
    }
}
