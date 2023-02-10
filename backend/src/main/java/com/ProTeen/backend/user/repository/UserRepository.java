package com.ProTeen.backend.user.repository;

import com.ProTeen.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserId(String userId);
    Boolean existsByUserId(String userId);
    Boolean existsByNickname(String nickname);
    @Query(value = "select role from users where id = :id", nativeQuery = true)
    String getRoleById(String id);
}
