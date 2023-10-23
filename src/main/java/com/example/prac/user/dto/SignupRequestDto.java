package com.example.prac.user.dto;

import com.example.prac.user.entity.User;
import com.example.prac.user.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignupRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9\\d!@#$%^&*+-=_]{8,}$"
            ,message = "영대소문자/숫자/특수문자를 포함한 8자 이상으로 비밀번호를 구성해야 합니다.")
    private String password;

    private String adminToken;

    public User toEntity(String encodedPassword, UserRoleEnum role) {
        return User.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .role(role)
                .build();
    }
}
