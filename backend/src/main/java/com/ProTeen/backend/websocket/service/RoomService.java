package com.ProTeen.backend.websocket.service;

import com.ProTeen.backend.user.model.UserEntity;
import com.ProTeen.backend.user.repository.UserRepository;
import com.ProTeen.backend.websocket.entity.Room;
import com.ProTeen.backend.websocket.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    public Room create(String user_id, String role) {
        Room room = Room.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                )))
                .isOpen(true)
                .cnt(0)
                .host(userRepository.findById(user_id).orElse(null))
                .role(role)
                .build();
        return roomRepository.save(room);
    }

    public void plusCnt(Room room) {
        room.setCnt(room.getCnt() + 1);
        if (room.getCnt() == 2) {
            room.setOpen(false);
        }
        roomRepository.save(room);
    }

    public Room findRoom(String id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> getRoomList() {
        return roomRepository.findAll();
    }

    public List<Room> getUserRoomList() {
        return roomRepository.getRoomByRole("ROLE_USER");
    }

    public List<Room> getCounselorRoomList() {
        return roomRepository.getRoomByRole("ROLE_COUNSELOR");
    }

    public void closeRoom(Room room) {
        roomRepository.deleteById(room.getId());
    }

    public void minusCnt(Room room) {
        room.setCnt(room.getCnt() - 1);
        room.setParticipant(null);
        roomRepository.save(room);
    }

    public boolean getRoomByHost(UserEntity user) {
        return roomRepository.existsByHost(user);
    }
}