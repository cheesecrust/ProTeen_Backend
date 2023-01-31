package com.ProTeen.backend.service;


import com.ProTeen.backend.model.UserEntity;
import com.ProTeen.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

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

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 추상화된 인터페이스인 UserDetails를 리턴해야 됨. User -> UserDetails 작업 필요
        UserEntity user = userRepository.findByUserId(userId);
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> authority = new ArrayList<>();
                authority.add(new SimpleGrantedAuthority(user.getRole()));
                return authority;
            }

            @Override
            public String getPassword() {
                return user.getUserPassword();
            }

            @Override
            public String getUsername() {
                return user.getUserId();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

        };

        return userDetails;
    }
}
