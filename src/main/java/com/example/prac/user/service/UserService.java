package com.example.prac.user.service;

import com.example.prac.user.dto.SignupRequestDto;
import com.example.prac.user.entity.User;
import com.example.prac.user.entity.UserRoleEnum;
import com.example.prac.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static final String adminToken = "ADMIN";

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.getAdminToken() != null && requestDto.getAdminToken().equals(adminToken)) {
            role = UserRoleEnum.ADMIN;
        }

        userRepository.save(requestDto.toEntity(requestDto.getPassword(), role));
    }

    @Transactional
    public User getUserInfo(long userId) {
        return findUser(userId);
    }

    private User findUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
