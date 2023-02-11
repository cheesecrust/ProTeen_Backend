package com.ProTeen.backend.user.config;

import com.ProTeen.backend.user.config.auth.CustomOAuth2UserService;
import com.ProTeen.backend.user.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .httpBasic().disable() // basic 인증 disable
                // 세션 기반이 아님을 선언
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/main/**").permitAll()
                .requestMatchers(HttpMethod.GET).permitAll()
                .requestMatchers("oauth/**").permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated() // 이외의 경로는 인증
                .and()
                .csrf()
                .disable().headers().addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
//                .and()
//                .oauth2Login()
//                .userInfoEndpoint() // OAuth2 로그인 성공 후 가져올 설정들
//                .userService(customOAuth2UserService); // 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시


        // filter 등록
        // 매 요청마다
        // CorsFilter 실행한 후에
        // jwtAuthenticationFilter 실행
        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );

        return http.build();
    }
}