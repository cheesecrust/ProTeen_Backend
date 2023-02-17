package com.ProTeen.backend.user.config;


import com.ProTeen.backend.user.handler.CustomAuthenticationEntryPoint;
import com.ProTeen.backend.user.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
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


//        http.cors()
//                .and()
//                .httpBasic().disable() // basic 인증 disable
//                // 세션 기반이 아님을 선언
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/main/**").permitAll()
//                .requestMatchers(HttpMethod.GET).permitAll()
//                .requestMatchers("/diagnosis/question/**").permitAll()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .requestMatchers("/h2-console/**", "/").permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .anyRequest()
//                .authenticated()
//                .and().cors();

        http.cors()
                .and()
                .httpBasic().disable() // basic 인증 disable
                // 세션 기반이 아님을 선언
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/main/reissue", "/main/logout").hasRole("USER")
                .requestMatchers("/room/**").hasAnyRole("USER", "COUNSELOR", "SUPER")
                .requestMatchers("/board/post", "/board/put/**", "board/delete/**").hasRole("USER")
                .requestMatchers("/comment/**").hasRole("USER")
                .requestMatchers("/diagnosis/**").hasRole("USER")
                .requestMatchers("/diagnosis/question/**").permitAll()
                .requestMatchers("/feedback/**").hasRole("USER")
                .anyRequest()
                .permitAll()
                .and().cors()
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS", "PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
