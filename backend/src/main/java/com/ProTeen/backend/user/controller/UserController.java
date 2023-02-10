package com.ProTeen.backend.user.controller;

import com.ProTeen.backend.user.dto.ResponseDTO;
import com.ProTeen.backend.user.dto.UserDto;
import com.ProTeen.backend.user.entity.User;
import com.ProTeen.backend.user.service.UserService;
import com.ProTeen.backend.user.security.TokenProvider;
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
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        log.info(userDto.toString());
        try {
            // 요청을 이용해 저장할 사용자 만들기
            User user = User.builder()
                    .userId(userDto.getUserId())
                    .nickname(userDto.getNickname())
                    .userPassword(passwordEncoder.encode(userDto.getPassword()))
                    .createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                            "yyyy-MM-dd HH:mm:ss"
                    )))
                    .role("ROLE_USER")
                    .build();

            // 서비스를 이용해 repository에 저장
            User registerdUser = userService.create(user);

            UserDto responseUserDto = UserDto.builder()
                    .userId(registerdUser.getUserId())
                    .nickname(registerdUser.getNickname())
                    .id(registerdUser.getId())
                    .build();

            log.info(responseUserDto.toString() + "  <---- 회원가입된 유저 정보");


            return ResponseEntity.ok().body(responseUserDto);

        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/main/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        User user = userService.getBycredentials(userDto.getUserId(), userDto.getPassword(), passwordEncoder);
        if (user != null) {
            // 토큰 생성
            final String token = tokenProvider.create(user);
            final UserDto responseUserDTO = UserDto.builder()
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

    // ROLE_USER을 가진 사용자만 ok가 뜸 (권한 테스트용)
    @GetMapping("/user/enter")
    public ResponseEntity<String> enter() {
        return ResponseEntity.ok("OKOKOKOK");
    }

}
