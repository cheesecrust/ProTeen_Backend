package com.ProTeen.backend.user.security;

import com.ProTeen.backend.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
@NoArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterchain) throws ServletException, IOException {
        try {
            // 요청에서 토큰 가져오기
            String token = parseBearerToken(request);
            log.info("Filter is running...." + request.getRequestURL());
            // 토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능
            if (token != null && !token.equalsIgnoreCase("null")) {
                // 토큰의 payload(claims) 가져오기. 위조된 경우에 예외 처리됨
                Claims claims = tokenProvider.validateAndGetUserPayload(token);

                String id = claims.getSubject();
                Date expireDate = claims.getExpiration();
                if (expireDate.before(new Date())) {
                    throw new Exception();
                }
                log.info("Authenticated user ID : " + id + "   <-- 로그인 된 유저입니다.");

                Collection<? extends GrantedAuthority> authority = userService.getAuthorities(id);

                // 인증 완료. SecurityContextHolder에 등록해야 인증된 사용자라고 생각함.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        id, // 인증된 사용자의 정보. 문자열이 아니어도 아무것이나 넣을 수 있음. 보통
                        // UserDetails라는 오브젝트를 넣긴함
                        null,
                        authority
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);

            }else{
                System.out.println("No token");
            }

        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        filterchain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // HTTP 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
