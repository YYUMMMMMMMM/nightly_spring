package com.nightly.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nightly.dto.UserRequestDto;
import com.nightly.dto.UserResponseDto;

public interface UserService {
    // 유저 정보 조회
    UserResponseDto getUser(String email);

    // 유저 목록 조회 (검색어로 시작하는 5개의 계정)
    List<UserResponseDto> getUserList(String nickname);

    // 유저 정보 수정
    UserResponseDto updateUser(UserRequestDto dto, MultipartFile file);

    // 계정 활성화
    UserResponseDto activateUser(UserRequestDto dto);

    // 계정 비활성화
    void inactivateUser(UserRequestDto dto);

    // 계정 탈퇴 : 유저 정보 삭제
    void deleteUser(String email);
}