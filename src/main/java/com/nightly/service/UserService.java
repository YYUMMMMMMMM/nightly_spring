package com.nightly.service;

import java.util.List;

import com.nightly.dto.UserDto;

public interface UserService {
    // 유저 정보 조회
    UserDto getUser(String email);

    // 유저 목록 조회 (검색어를 포함하는 5개의 계정)
    List<UserDto> getUserList(String nickname);

    // 유저 정보 수정
    UserDto updateUser(UserDto dto);

    // 계정 활성화
    UserDto activateUser(UserDto dto);

    // 계정 비활성화
    void inactivateUser(UserDto dto);

    // 계정 탈퇴 : 유저 정보 삭제
    void deleteUser(String email);
}