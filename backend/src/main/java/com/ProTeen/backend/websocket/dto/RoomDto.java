package com.ProTeen.backend.websocket.dto;


import com.ProTeen.backend.websocket.entity.Room;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private String id;
    private String createdAt;
    private boolean isOpen;
    private String host;
    private String participant;
    private int cnt;
}
