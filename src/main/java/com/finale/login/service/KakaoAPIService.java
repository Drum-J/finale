package com.finale.login.service;

import com.finale.login.dto.KakaoUserInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Slf4j
@Service
public class KakaoAPIService {
    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri.student}")
    private String redirectStudent;

    @Value("${kakao.redirect-uri.coach}")
    private String redirectCoach;

    @Value("${kakao.content-type}")
    private String contentType;

    @Value("${kakao.authorization-grant-type}")
    private String grantType;

    @Value("${kakao.authorization-uri}")
    private String authorizationUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    public String redirectUri(String type) {
        String redirectUri;
        if (type.equals("student")) {
            redirectUri = redirectStudent;
        } else {
            redirectUri = redirectCoach;
        }

        return UriComponentsBuilder
                .fromUriString(authorizationUri)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", encode(redirectUri, UTF_8))
                .build()
                .toString();
    }

    public KakaoUserInfo getAccessToken(String code, HttpServletResponse response, String type) throws IOException {
        String redirectUri;
        if (type.equals("student")) {
            redirectUri = redirectStudent;
        } else {
            redirectUri = redirectCoach;
        }

        String uri = UriComponentsBuilder
                .fromUriString(tokenUri)
                .queryParam("grant_type", grantType)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .queryParam("client_secret", clientSecret)
                .build()
                .toString();

        RestClient restClient = RestClient.builder()
                .baseUrl(uri)
                .defaultHeader(CONTENT_TYPE, contentType)
                .build();

        String body = restClient.post()
                .retrieve()
                .onStatus(HttpStatusCode::isError,((request, resp) -> {
                    throw new IOException("카카오 서버와 통신 중 에러가 발생했습니다.");
                }))
                .body(String.class);

        log.info("RestClient Body = {}",body);

        JSONObject json = new JSONObject(body);
        String accessToken = json.getString("access_token");

        log.info("카카오 AccessToken = {}", accessToken);

        KakaoUserInfo userInfo = getUserInfo(accessToken);
        log.info("카카오 유저 정보 = {}", userInfo);

        return userInfo;
    }

    private KakaoUserInfo getUserInfo(String accessToken) throws IOException {

        String uri = UriComponentsBuilder
                .fromUriString(userInfoUri)
                .queryParam("property_keys", "[\"kakao_account.email\",\"kakao_account.name\",\"kakao_account.phone_number\"]")
                .build()
                .toString();

        RestClient restClient = RestClient.builder()
                .baseUrl(uri)
                .defaultHeader(CONTENT_TYPE,contentType)
                .defaultHeader(AUTHORIZATION, "Bearer " + accessToken)
                .build();

        String body = restClient.get()
                .retrieve()
                .onStatus(HttpStatusCode::isError,((request, response) -> {
                    throw new IOException("카카오 서버와 통신 중 에러가 발생했습니다.");
                }))
                .body(String.class);

        log.info("RestClient Body.UserInfo = {}",body);

        JSONObject json = new JSONObject(body);
        String email = json.getJSONObject("kakao_account").getString("email");
        String name = json.getJSONObject("kakao_account").getString("name");
        String phoneNumber = json.getJSONObject("kakao_account").getString("phone_number");

        return new KakaoUserInfo(name, email, phoneNumber);
    }
}
