package com.finale.jwt;

import com.finale.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse createMasterToken() {
        String accessToken = jwtProvider.createAccessToken(717L, "master-swagger", "MASTER");
        return ApiResponse.successResponse(accessToken);
    }

    @Operation(summary = "[Swagger] 토큰 생성 API-Sub", description = "SUB 권한을 가진 토큰을 생성합니다.")
    @GetMapping("/sub")
    public ApiResponse createSubToken() {
        String accessToken = jwtProvider.createAccessToken(717L, "sub-swagger", "SUB");
        return ApiResponse.successResponse(accessToken);
    }

    @Operation(summary = "[Swagger] 토큰 생성 API-Student", description = "STUDENT 권한을 가진 토큰을 생성합니다.")
    @GetMapping("/student")
    public ApiResponse createStudentToken() {
        String accessToken = jwtProvider.createAccessToken(717L, "student-swagger", "STUDENT");
        return ApiResponse.successResponse(accessToken);
    }
}
