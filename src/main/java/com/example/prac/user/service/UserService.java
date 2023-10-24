package com.example.prac.user.service;

import com.example.prac.user.dto.SignupRequestDto;
import com.example.prac.user.entity.User;
import com.example.prac.user.entity.UserRoleEnum;
import com.example.prac.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.token}")
    private String adminToken;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String password = passwordEncoder.encode(requestDto.getPassword());

        checkDuplicateEmail(requestDto.getEmail());

        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.getAdminToken() != null && requestDto.getAdminToken().equals(adminToken)) {
            role = UserRoleEnum.ADMIN;
        }

        userRepository.save(requestDto.toEntity(password, role));
    }

    @Transactional
    public User getUserInfo(long userId) {
        return findUserById(userId);
    }

    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }

    private User findUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
