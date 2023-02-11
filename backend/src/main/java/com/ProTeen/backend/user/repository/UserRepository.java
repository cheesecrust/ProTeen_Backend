package com.ProTeen.backend.user.repository;


import org.springframework.data.jpa.repository.Query;
import com.ProTeen.backend.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUserId(String userId);
    Boolean existsByUserId(String userId);
    Boolean existsByNickname(String nickname);
    @Query(value = "select role from users where id = :id", nativeQuery = true)
    String getRoleById(String id);

}
