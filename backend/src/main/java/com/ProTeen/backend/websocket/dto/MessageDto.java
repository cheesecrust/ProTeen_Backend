package com.ProTeen.backend.websocket.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {
    private String userNickname;
    private String roomId;
    private String message;
    private String createdAt;
}
