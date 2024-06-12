package com.finale.jwt;

import com.finale.exception.JwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class JwtProvider {

    //private static final Long EXPIRATION = 1000L * 60 * 60 * 6;
    private static final Long EXPIRATION = 1000L * 60;
    private static final Long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24;
    private static final String BEARER = "Bearer ";
    private final SecretKey SECRET_KEY;

    public JwtProvider(@Value("${custom.jwt.secretKey}") String jwtKey) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(jwtKey.getBytes(UTF_8));
    }

    public String createAccessToken(Long id, String name, String role) {
        Claims claims = Jwts.claims()
                .add("id",id)
                .add("name",name)
                .add("role", role)
                .build();

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + EXPIRATION);

        // 스프링에 인증 추가
        setAuthentication(claims);

        return Jwts.builder()
                .claims(claims)
                .issuer("FINALE")
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    // JWT 쿠키에서 가져오기
    public String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    // Cookie 설정 완료 전까지 header 토큰 사용
    public String getTokenFromHeader(HttpServletRequest request) {
        String headerValue = request.getHeader(AUTHORIZATION);

        if (headerValue == null) {
            log.info("======== 헤더가 비어있습니다. ========");
            return null;
        }

        if (!headerValue.startsWith(BEARER)) {
            log.info("잘못된 토큰 정보입니다.");
            return null;
        }

        // 'Authorization Bearer '에 담겨있는 토큰을 가져온다
        return headerValue.substring(BEARER.length());
    }

    // JWT 예외 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e){
            throw new JwtTokenException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtTokenException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtTokenException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new JwtTokenException("JWT 토큰이 잘못되었습니다.");
        } catch (Exception e) {
            throw new JwtTokenException("JWT 예외 발생");
        }
    }


    public Claims getTokenInfo(String token) {
        return Jwts
                .parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public void setAuthentication(Claims claims) {
        log.info("==== 스프링 시큐리티 컨텍스트 세팅 ====");

        String name = claims.get("name").toString();
        String role = claims.get("role").toString();

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(name, null, AuthorityUtils.createAuthorityList(role));

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
