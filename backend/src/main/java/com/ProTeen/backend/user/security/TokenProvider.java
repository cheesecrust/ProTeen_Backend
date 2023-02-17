package com.ProTeen.backend.user.security;

import com.ProTeen.backend.user.dto.TokenDTO;
import com.ProTeen.backend.user.model.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenProvider {

    // 초단위
    private static final long RTK_EXPIRED_DURATION = 259200L;
    private static final long ATK_EXPIRED_DURATION = 3600L;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // private static final String SECRET_KEY = "NMA8JPctFuna59f5";
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String createAccessToken(String id) {
        // 기한은 지금부터 1시간으로 설정
        Date expiryDate = Date.from(
                Instant.now().plus(60, ChronoUnit.MINUTES)
        );

        log.info("id : " + id  + " 의 AccessToken 생성");

        // AccessToken 생성
        return Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기 위한 시크릿 키
                .signWith(key)
                // payload에 들어갈 내용
                .setSubject(id) //sub
                .setIssuer("ProTeen")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String createRefreshToken(String id) {
        // 기한은 지금부터 3일로 설정
        Date expiryDate = Date.from(
                Instant.now().plus(3, ChronoUnit.DAYS)
        );


        // RefreshToken 생성
        String refreshToken = Jwts.builder()
                .signWith(key)
                .setSubject(id)
                .setIssuer("ProTeen")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();

        log.info("id : " + id  + " 의 RefreshToken 생성");

        // redis 등록
        redisTemplate.opsForValue().set("RT: " + id, refreshToken, RTK_EXPIRED_DURATION, TimeUnit.SECONDS);


        return refreshToken;
    }

    public Claims validateAndGetUserPayload(String token) {
        // parseClaimJws 메서드가 Base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그중 우리는 userId가 필요하므로 getBody를 부른다.
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            log.info(claims.getExpiration().toString());

            return claims;

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("wrong JWT signature");
        } catch (ExpiredJwtException e) {
            log.info("expired JWT");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT");
        } catch (IllegalArgumentException e) {
            log.info("wrong JWT");
        }

        return null;
    }

    public ResponseEntity<?> reissue(TokenDTO tokenDTO) {
        // RefreshToken 검증
        Claims claims = validateAndGetUserPayload(tokenDTO.getRefreshToken());
        if (claims == null) {
            return ResponseEntity.badRequest().body("RefreshToken 정보가 유효하지 않습니다.");
        }
        // AccessToken 에서 id 가지고 와서 redis 에 해당 id로 만들어진 RefreshToken 접근
        String id = claims.getSubject();
        String refreshToken = (String)redisTemplate.opsForValue().get("RT: " + id);
        log.info("refreshToken : " + refreshToken);

        if (ObjectUtils.isEmpty(refreshToken)) {
            return ResponseEntity.badRequest().body("재로그인 해야합니다");
        }

        if (!refreshToken.equals(tokenDTO.getRefreshToken())) {
            return ResponseEntity.badRequest().body("RefreshToken 정보가 일치하지 않습니다");
        }

        // 새로운 토큰 생성
        String newAccessToken = createAccessToken(id);
        String newRefreshToken = createRefreshToken(id);
        TokenDTO newtokenDTO = TokenDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

        // RefreshToken Redis 업데이트
        redisTemplate.opsForValue().set("RT: " + id, newRefreshToken, RTK_EXPIRED_DURATION, TimeUnit.SECONDS);
        return ResponseEntity.ok().body(newtokenDTO);
    }


    public ResponseEntity<?> logout(TokenDTO tokenDTO) {
        // accessToken 검증
        Claims claims = validateAndGetUserPayload(tokenDTO.getAccessToken());
        if (claims == null) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
        // redis 에서 해당 id로 저장된 refreshToken 있으면 삭제
        String id = claims.getSubject();
        if (redisTemplate.opsForValue().get("RT: " + id) != null) {
            redisTemplate.delete("RT: " + id);
        }
        // 해당 accessToken redis blacklist 저장
        redisTemplate.opsForValue().set(tokenDTO.getAccessToken(), "logout", ATK_EXPIRED_DURATION, TimeUnit.SECONDS);
        return ResponseEntity.ok().body("로그아웃 되었습니다.");
    }

}
