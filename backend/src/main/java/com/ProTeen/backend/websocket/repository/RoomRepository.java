package com.ProTeen.backend.websocket.repository;

import com.ProTeen.backend.user.model.UserEntity;
import com.ProTeen.backend.websocket.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String> {

    @Override
    // @Query(value = "select * from room where is_open = true and cnt = 1", nativeQuery = true)
    List<Room> findAll();

    @Query(value = "select * from room where role = :role and is_open = true", nativeQuery = true)
    List<Room> getRoomByRole(String role);

    Boolean existsByHost(UserEntity user);

}