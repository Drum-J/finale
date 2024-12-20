package com.finale.login.controller;

import com.finale.common.ApiResponse;
import com.finale.login.dto.IdTokenDTO;
import com.finale.login.dto.KakaoUserInfo;
import com.finale.login.dto.LoginResponseDTO;
import com.finale.login.service.KakaoAPIService;
import com.finale.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("카카오 로그인 진입 TYPE = {}", type);

        String redirectUri = kakaoAPIService.redirectUri(type);
        log.info("생성된 Redirect URL : {}",redirectUri);
        return ApiResponse.successResponse(redirectUri);
    }

    @ResponseBody
    @GetMapping("/callback")
    public ApiResponse studentCallback(@RequestParam(name = "code") String code,
                                  @RequestParam(name = "state") String type,
                                  HttpServletResponse response) throws Exception {
        log.info("카카로 CallBack Controller 진입");
        log.info("CODE = {} / state = {} ",code, type);

        KakaoUserInfo userInfo = kakaoAPIService.getAccessToken(code,type);
        IdTokenDTO dto;
        if (type.equals(STUDENT)) {
            dto = loginService.loginStudent(userInfo);
        } else if (type.equals(COACH)) {
            dto = loginService.loginCoach(userInfo);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ApiResponse.errorResponse("Type 값이 올바르지 않습니다.");
        }

        // CookieUtil.addCookie(response, token);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + dto.token());
        return ApiResponse.successResponse(new LoginResponseDTO(dto.id(),userInfo,dto.token()));
    }
}
