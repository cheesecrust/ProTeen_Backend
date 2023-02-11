package com.ProTeen.backend.service;

import com.ProTeen.backend.user.model.UserEntity;
import com.ProTeen.backend.user.repository.UserRepository;
import com.ProTeen.backend.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void 유저_생성() throws Exception{
        UserEntity createUser = new UserEntity();

        createUser.setId("jack0810");
        createUser.setUserPassword("0810jack?!");
        createUser.setNickname("jack");

        UserEntity newUser = userService.create(createUser);
    }

}
