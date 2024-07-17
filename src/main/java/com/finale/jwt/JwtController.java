package com.finale.jwt;

import com.finale.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt/create")
public class JwtController {

    private final JwtProvider jwtProvider;

    @Operation(summary = "[Swagger] 토큰 생성 API-Master", description = "MASTER 권한을 가진 토큰을 생성합니다.")
    @GetMapping("/master")
    public ApiResponse createMasterToken(HttpServletResponse response) {
        String accessToken = jwtProvider.createAccessToken(2L, "헤드코치", "MASTER");
        //CookieUtil.addCookie(response, accessToken);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        return ApiResponse.successResponse(accessToken);
    }

    @Operation(summary = "[Swagger] 토큰 생성 API-Sub", description = "SUB 권한을 가진 토큰을 생성합니다.")
    @GetMapping("/sub")
    public ApiResponse createSubToken(HttpServletResponse response) {
        String accessToken = jwtProvider.createAccessToken(1L, "승호", "SUB");
        //CookieUtil.addCookie(response, accessToken);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        return ApiResponse.successResponse(accessToken);
    }

    @Operation(summary = "[Swagger] 토큰 생성 API-Student", description = "STUDENT 권한을 가진 토큰을 생성합니다.")
    @GetMapping("/student")
    public ApiResponse createStudentToken(HttpServletResponse response) {
        String accessToken = jwtProvider.createAccessToken(1L, "승호", "STUDENT");
        //CookieUtil.addCookie(response, accessToken);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        return ApiResponse.successResponse(accessToken);
    }
}
