package com.ProTeen.backend.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String nickname;
    private String password;
    private String id;
}
