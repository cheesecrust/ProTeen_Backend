package com.ProTeen.backend.websocket.controller;

import com.ProTeen.backend.user.repository.UserRepository;
import com.ProTeen.backend.websocket.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations template;

    @MessageMapping("/message")
    public void message(@Payload MessageDto messageDto) {
        messageDto.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss"
        )));
        template.convertAndSend("/sub/room/" + messageDto.getRoomId(), messageDto);
    }

    @MessageMapping("/join")
    public void join(@Payload MessageDto messageDto) {
        messageDto.setMessage(messageDto.getUserNickname() + "님이 입장하셨습니다");
        messageDto.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss"
        )));
        template.convertAndSend("/sub/room/" + messageDto.getRoomId(), messageDto);
    }

    @MessageMapping("/out")
    public void out(@Payload MessageDto messageDto) {
        messageDto.setMessage(messageDto.getUserNickname() + "님이 퇴장하셨습니다");
        messageDto.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss"
        )));
        template.convertAndSend("/sub/room/" + messageDto.getRoomId(), messageDto);
    }
}
