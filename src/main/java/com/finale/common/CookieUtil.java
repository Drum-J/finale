package com.finale.common;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String value) {
        ResponseCookie responseCookie = ResponseCookie.from("accessToken",value)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                //.secure(true) https 에서만 쿠키를 사용하도록 설정
                .domain("localhost")
                .maxAge(60 * 60 * 6) // 6시간
                .build();

        response.addHeader(SET_COOKIE,responseCookie.toString());
    }
}
