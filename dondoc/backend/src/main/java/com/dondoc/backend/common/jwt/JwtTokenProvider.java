package com.dondoc.backend.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import java.util.*;

@Slf4j
@Component
public class JwtTokenProvider {

    // access key
    @Value("${jwt.access}")
    private String ACCESS_KEY;

    // refresh key
    @Value("${jwt.refresh}")
    private String REFRESH_KEY;

    // AccessToken 만료 시간
    @Value("${jwt.accessTokenExpiredTime}")
    private long ACCESS_TIME;

    // RefreshToken 만료 시간
    @Value("${jwt.refreshTokenExpiredTime}")
    private long REFRESH_TIME;

    // 토큰 생성("타입 값에 따라 access, refresh 구분)
    public String createToken(TokenDto tokenDto, long time, String key){
        // Claims 해시 맵 생성
        Map<String, String> claims = new HashMap<>();

         // Claims에 정보 추가
        claims.put("name", tokenDto.getName());
        claims.put("username", tokenDto.getPhoneNumber());

        String token = Jwts.builder()
                .setClaims(claims) // Claim
                .setSubject(tokenDto.getUserId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis())) // 생성일자
                .setExpiration(new Date(System.currentTimeMillis() + time)) // 만료 일자
                .signWith(getSecretKey(key), SignatureAlgorithm.HS256) //
                .compact();

        return token;
    }

    public String createAccessToken(TokenDto tokenDto){
        return createToken(tokenDto, ACCESS_TIME, ACCESS_KEY);
    }

    public Cookie createRefreshToken(TokenDto tokenDto){
        Cookie refreshToken  = new Cookie("refreshToken", createToken(tokenDto, REFRESH_TIME, REFRESH_KEY));
        refreshToken.setPath("/");
        refreshToken.setMaxAge((int)REFRESH_TIME);
        return refreshToken;
    }

    // 토큰에서 클레임 추출
    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey(ACCESS_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

    }

    // refreshToken 에서 Claim 가져오기
    public Claims getRefreshClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey(REFRESH_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰에서 유저정보 출력
    public Claims getPhoneNumber(String token){
        return getClaims(token);
    }

    // 토큰 만료 여부 확인
    public boolean isTokenExpired(String token){
        // 토큰에서 만료시간 출력
        final Date expiration = getClaims(token).getExpiration();
        // 유효기간이 남았으면 true, 안 남았으면 false
        return expiration.before(new Date());
    }

    public boolean isRefreshTokenExpired(String token){
        try{
            // 토큰에서 만료시간 출력
            final Date expiration = getRefreshClaims(token).getExpiration();
            // 유효기간이 남았으면 true, 안 남았으면 false
            return expiration.before(new Date());
        }catch(Exception e){
            return false;
        }
    }

    // 토큰의 유효성 검사
    public boolean isTokenValid(String token, String phoneNumber){
        // 토큰에서 유저 정보 추출
        final String tokenPhoneNumber = getClaims(token).getSubject();
        // 토큰에서 추출한 유저와 입력한 유저의 정보가 일치하는지 확인, 토큰의 만료 여부 파악
        return (tokenPhoneNumber.equals(phoneNumber) && !isTokenExpired(token));
    }


    private SecretKey getSecretKey(String key){
        // 키 디코딩
        byte[] keyBytes = Base64.getDecoder().decode(key);
        // 복호화 키 생성
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
