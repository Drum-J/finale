package com.finale.login.dto;

public record LoginResponseDTO(Long userId, KakaoUserInfo userInfo, String token) {
}
