package com.ProTeen.backend.user.controller;

import com.ProTeen.backend.user.dto.TokenDTO;
import com.ProTeen.backend.user.dto.UserDTO;
import com.ProTeen.backend.user.security.TokenProvider;
import com.ProTeen.backend.community.dto.ResponseDTO;
import com.ProTeen.backend.user.model.UserEntity;
import com.ProTeen.backend.user.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequestMapping("main")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
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
            return ResponseEntity.badRequest().body("회원가입 실패");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDto) {
        if(userDto.getUserId() == null){
            return ResponseEntity.badRequest().body("userId can't be null.");
        }

        if(userDto.getPassword() == null){
            return ResponseEntity.badRequest().body("password can't be null.");
        }

        UserEntity user = userService.getBycredentials(userDto.getUserId(), userDto.getPassword(), passwordEncoder);

        if (user != null) {
            // 토큰 생성
            final String accessToken = tokenProvider.createAccessToken(user.getId());
            final String refreshToken = tokenProvider.createRefreshToken(user.getId());
            final UserDTO responseUserDTO = UserDTO.builder()
                    .userId(user.getUserId())
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            log.info(responseUserDTO.toString() + "   <------------토큰 발급 및 유저 정보");


            return ResponseEntity.ok().body(responseUserDTO);
        }
        else {
            return ResponseEntity.badRequest().body("아이디나 비밀번호가 틀립니다");
        }
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenDTO tokenDTO) {
        if (tokenDTO.getAccessToken() == null) {
            return ResponseEntity.badRequest().body("AccessToken is null");
        }
        if (tokenDTO.getRefreshToken() == null) {
            return ResponseEntity.badRequest().body("RefreshToken is null");
        }
        return tokenProvider.reissue(tokenDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenDTO tokenDTO) {
        if (tokenDTO.getAccessToken() == null) {
            return ResponseEntity.badRequest().body("AccessToken is null");
        }
        if (tokenDTO.getRefreshToken() == null) {
            return ResponseEntity.badRequest().body("RefreshToken is null");
        }
        return tokenProvider.logout(tokenDTO);
    }

    @GetMapping("/isDuplicatedId")
    public ResponseEntity<?> idDuplicated(@RequestParam("userId") String userId) {
        if (userService.userIdDuplicated(userId)) {
            return ResponseEntity.ok().body("중복되는 아이디입니다");
        }
        else {
            return ResponseEntity.ok().body("사용가능한 아이디입니다");
        }
    }

    @GetMapping("/isDuplicatedNickname")
    public ResponseEntity<?> nicknameDuplicated(@RequestParam("nickname") String nickname) {
        if (userService.nicknameDuplicated(nickname)) {
            return ResponseEntity.ok().body("중복되는 닉네임입니다");
        }
        else {
            return ResponseEntity.ok().body("사용가능한 닉네임입니다");
        }
    }

}
