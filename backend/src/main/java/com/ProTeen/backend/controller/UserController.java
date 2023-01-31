package com.ProTeen.backend.controller;

import com.ProTeen.backend.dto.ResponseDTO;
import com.ProTeen.backend.dto.UserDTO;
import com.ProTeen.backend.model.UserEntity;
import com.ProTeen.backend.service.UserService;
import com.ProTeen.backend.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/main/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDto) {
        log.info(userDto.toString());
        try {
            // 요청을 이용해 저장할 사용자 만들기
            UserEntity user = UserEntity.builder()
                    .userId(userDto.getUserId())
                    .nickname(userDto.getNickname())
                    .userPassword(passwordEncoder.encode(userDto.getPassword()))
                    .createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                            "yyyy-MM-dd HH:mm:ss"
                    )))
                    .role("ROLE_USER")
                    .build();

            // 서비스를 이용해 repository에 저장
            UserEntity registerdUser = userService.create(user);

            UserDTO responseUserDTO = UserDTO.builder()
                    .userId(registerdUser.getUserId())
                    .nickname(registerdUser.getNickname())
                    .id(registerdUser.getId())
                    .build();

            log.info(responseUserDTO.toString() + "  <---- 회원가입된 유저 정보");


            return ResponseEntity.ok().body(responseUserDTO);

        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/main/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDto) {
        UserEntity user = userService.getBycredentials(userDto.getUserId(), userDto.getPassword(), passwordEncoder);
        if (user != null) {
            // 토큰 생성
            final String token = tokenProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .userId(user.getUserId())
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .token(token)
                    .build();

            log.info(responseUserDTO.toString() + "   <------------토큰 발급 및 유저 정보");
            return ResponseEntity.ok().body(responseUserDTO);
        }
        else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login Failed")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> tokentest() {
        return ResponseEntity.ok("OKOKOKOK");
    }
}
