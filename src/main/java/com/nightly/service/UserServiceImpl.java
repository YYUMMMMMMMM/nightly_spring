package com.nightly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nightly.dto.UserRequestDto;
import com.nightly.dto.UserResponseDto;
import com.nightly.entity.User;
import com.nightly.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto getUser(String userEmail) {
        return UserResponseDto.fromEntity(userRepository.findByEmail(userEmail));
    }

    @Override
    public List<UserResponseDto> getUserList(String nickname) {
        return userRepository.findByNicknameStartingWith(nickname)
                             .stream()
                             .map(UserResponseDto::fromEntity)
                             .collect(Collectors.toList());
    }

    // 프로필 수정 정보 : 프로필 이미지, 닉네임, 전화번호, 자기 소개
    @Override
    public UserResponseDto updateUser(UserRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        user.setProfileImage(dto.getProfileImage());
        user.setNickname(dto.getNickname());
        user.setPhone(dto.getPhone());
        user.setContent(dto.getContent());
        return UserResponseDto.fromEntity(userRepository.save(user));
    }

    // 계정 활성화
    @Override
    public UserResponseDto activateUser(UserRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        user.setStatus("ACTIVE");
        return UserResponseDto.fromEntity(userRepository.save(user));
    }

    // 계정 비활성화
    @Override
    public void inactivateUser(UserRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        user.setStatus("INACTIVE");
    }
    
    // 계정 삭제
    @Override
    public void deleteUser(String userEmail) {
        userRepository.delete(userRepository.findByEmail(userEmail));
    }
}
