package com.ProTeen.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.ProTeen.backend.user.repository.UserRepository;
import com.ProTeen.backend.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity user) {
    
        if (user == null || user.getUserId() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String userId = user.getUserId();
        if (userRepository.existsByUserId(userId)) {
            log.warn("This userId already exists {}", userId);
            throw new RuntimeException("This userId already exists");
        }
        return userRepository.save(user);
    }


    public UserEntity getBycredentials(final String userId, final String userPassword, final PasswordEncoder encoder) {
        final UserEntity originalUser = userRepository.findByUserId(userId);
        // matches 메서드를 이용해 패스워드가 같은지 확인
        if (originalUser != null && encoder.matches(userPassword, originalUser.getUserPassword())) {
            return originalUser;
        }
        return null;
    }


    public Collection<? extends GrantedAuthority> getAuthorities(String id) {
        String role = userRepository.getRoleById(id);
        log.info("role : " + role + " userId : " + id);
        Collection<SimpleGrantedAuthority> authority = new ArrayList<>();
        authority.add(new SimpleGrantedAuthority(role));
        return authority;
    }


    public boolean userIdDuplicated(String userID) {
        return userRepository.existsByUserId(userID);
    }

    public boolean nicknameDuplicated(String nickname) {
        return userRepository.existsByNickname(nickname);
    }



}
