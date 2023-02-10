package com.ProTeen.backend.user.config;

import com.ProTeen.backend.user.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
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
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // h2-console 쓰기 위한 설정
        http.authorizeHttpRequests()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .permitAll()
                .and().csrf()
                .disable().headers().addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));


        http.cors()
                .and()
                .httpBasic().disable() // basic 인증 disable
                // 세션 기반이 아님을 선언
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 임시로 해놓은 권한설정임 나중에 필요에 맞게 고쳐쓰기
                .authorizeHttpRequests()
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/**").permitAll();
//                    .anyRequest().authenticated();


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
