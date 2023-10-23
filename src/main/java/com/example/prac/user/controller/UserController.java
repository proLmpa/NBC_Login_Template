package com.example.prac.user.controller;

import com.example.prac.common.dto.ApiResponseDto;
import com.example.prac.user.dto.SignupRequestDto;
import com.example.prac.user.dto.UserResponseDto;
import com.example.prac.user.entity.User;
import com.example.prac.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "사용자 API", description = "사용자의 동작과 관련된 API 정보를 담고 있습니다.")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Validated @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), "회원가입에 성공했습니다."));
    }

    @Operation(summary = "회원 정보 조회")
    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> getUserInfo(long userId) {
        User user = userService.getUserInfo(userId);
        return ResponseEntity.ok().body(UserResponseDto.of(user));
    }
}
