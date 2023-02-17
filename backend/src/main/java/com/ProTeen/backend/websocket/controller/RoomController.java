package com.ProTeen.backend.websocket.controller;

import com.ProTeen.backend.user.repository.UserRepository;
import com.ProTeen.backend.user.service.UserService;
import com.ProTeen.backend.websocket.dto.RoomDto;
import com.ProTeen.backend.websocket.entity.Room;
import com.ProTeen.backend.websocket.service.RoomService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;

    private String getRole() {
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        StringBuilder builder_role = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            builder_role.append(authority);
        }
        return builder_role.toString();
    }

    @GetMapping("/create")
    public ResponseEntity<?> createRoom(@AuthenticationPrincipal String user_id) {
        String role = getRole();
        try {
            if (!roomService.getRoomByHost(userService.getUserEntityById(user_id))) {
                Room room = roomService.create(user_id, role);
                RoomDto roomDto = RoomDto.builder()
                        .id(room.getId())
                        .createdAt(room.getCreatedAt())
                        .isOpen(room.isOpen())
                        .cnt(room.getCnt())
                        .host(room.getHost().getNickname())
                        .build();

                return ResponseEntity.ok().body(roomDto);
            }
            else {
                return ResponseEntity.badRequest().body("이미 생성한 채팅방이 있습니다");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/enter/{room_id}")
    public ResponseEntity<?> enterRoom(@PathVariable String room_id, HttpServletResponse response,
                                       @AuthenticationPrincipal String user_id) {
        try {
            Room room = roomService.findRoom(room_id);
            if (room.getCnt() < 2) {
                if (room.getParticipant() == null && !room.getHost().getId().equals(user_id)) {
                    room.setParticipant(user_id);
                }
                if (room.getCnt() == 1 && room.getHost().getId().equals(user_id)) {
                    return ResponseEntity.badRequest().body("이미 들어간 방입니다");
                }
                roomService.plusCnt(room);
                return ResponseEntity.ok().body("입장 성공");
            }
            else {
                return ResponseEntity.badRequest().body("방이 다 찼습니다");
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body("채팅방 찾기 실패");
        }
    }

    @GetMapping("/out/{room_id}")
    public ResponseEntity<?> outRoom(@PathVariable String room_id, @AuthenticationPrincipal String user_id
    , HttpServletResponse response) {
        try {
            log.info(user_id);
            Room room = roomService.findRoom(room_id);
            // 방 주인이 나가면 방 삭제
            log.info(room.getHost().getId());
            if (room.getHost().getId().equals(user_id)) {
                roomService.closeRoom(room);
                return ResponseEntity.ok().body("채팅방을 나가셨습니다");
            }
            roomService.minusCnt(room);
            return ResponseEntity.ok().body("채팅방을 나가셨습니다");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("채팅장 찾기 실패");
        }
    }

    // business
    @PreAuthorize("hasRole('ROLE_SUPER')")
    @GetMapping("/list")
    public ResponseEntity<?> roomList(Model model) {
        List<Room> roomList = roomService.getRoomList();
        log.info(roomList.toString());
        List<RoomDto> roomDtoList = roomList.stream().map(r -> new RoomDto(
                r.getId(),
                r.getCreatedAt(),
                r.isOpen(),
                r.getParticipant(),
                r.getHost().getNickname(),
                r.getCnt()
        )).toList();
        return ResponseEntity.ok().body(roomDtoList);
    }

    // 상담사는 유저(피상담자)가 만든 방만 조회(다른 상담사가 만든 방은 조회x)
    @PreAuthorize("hasRole('ROLE_COUNSELOR')")
    @GetMapping("/user/list")
    public ResponseEntity<?> userRoomList() {
        List<Room> roomList = roomService.getUserRoomList();
        List<RoomDto> roomDtoList = roomList.stream().map(room -> new RoomDto(
                room.getId(),
                room.getCreatedAt(),
                room.isOpen(),
                room.getHost().getNickname(),
                room.getParticipant(),
                room.getCnt()
        )).toList();
        return ResponseEntity.ok().body(roomDtoList);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/counselor/list")
    public ResponseEntity<?> counselorRoomList() {
        List<Room> roomList = roomService.getCounselorRoomList();
        List<RoomDto> roomDtoList = roomList.stream().map(room -> new RoomDto(
                room.getId(),
                room.getCreatedAt(),
                room.isOpen(),
                room.getHost().getNickname(),
                room.getParticipant(),
                room.getCnt()
        )).toList();
        return ResponseEntity.ok().body(roomDtoList);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.badRequest().body("권한이 없습니다");
    }
}
