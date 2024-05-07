package com.finale.login.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
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

    public void getAccessToken(String code, HttpServletResponse response, String type) {
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

        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, contentType);

        // RestTemplate 대신 RestClient 로 바꿔보기
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String token = restTemplate
                .exchange(
                        uri,
                        HttpMethod.POST,
                        httpEntity,
                        String.class)
                .getBody();

        JSONObject json = new JSONObject(token);

        String accessToken = json.getString("access_token");

        log.info("카카오 AccessToken = {}", accessToken);

        getUserInfo(accessToken, response);
        //이걸 공통 KakaoUserInfo 객체로 만들어서 리턴 후 type 값 활용해서 로그인 처리 하는게 좋을 듯
    }

    private void getUserInfo(String accessToken, HttpServletResponse response) {

        String uri = UriComponentsBuilder
                .fromUriString(userInfoUri)
                .queryParam("property_keys", "[\"kakao_account.profile\",\"kakao_account.email\"]")
                .build()
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, contentType);
        headers.add(AUTHORIZATION, "Bearer " + accessToken);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String userInfo = restTemplate
                .exchange(
                        uri,
                        HttpMethod.GET,
                        httpEntity,
                        String.class)
                .getBody();

        JSONObject json = new JSONObject(userInfo);
        String email = json.getJSONObject("kakao_account").getString("email");

        JSONObject profile = json.getJSONObject("kakao_account").getJSONObject("profile");
        String nickname = profile.getString("nickname");
        String profileImageUrl = profile.getString("profile_image_url");

        log.info("카카오 유저 정보 = {} / {} / {}", email, nickname, profileImageUrl);

        // 카카오 유저 정보 객체로 만들어서 return;
    }
}
