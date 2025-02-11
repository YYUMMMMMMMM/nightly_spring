package com.nightly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nightly.dto.UserDto;
import com.nightly.entity.User;
import com.nightly.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUser(String userEmail) {
        return UserDto.fromEntity(userRepository.findByEmail(userEmail));
    }

    @Override
    public List<UserDto> getUserList(String nickname) {
        return userRepository.findByNickname(nickname)
                             .stream()
                             .map(UserDto::fromEntity)
                             .collect(Collectors.toList());
    }

    // 프로필 수정 정보 : 프로필 이미지, 닉네임, 전화번호
    @Override
    public UserDto updateUser(UserDto dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        user.setProfileImage(dto.getProfileImage());
        user.setNickname(dto.getNickname());
        user.setPhone(dto.getPhone());
        return UserDto.fromEntity(userRepository.save(user));
    }

    // 계정 활성화
    @Override
    public UserDto activateUser(UserDto dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        user.setStatus("ACTIVE");
        return UserDto.fromEntity(userRepository.save(user));
    }

    // 계정 비활성화
    @Override
    public void inactivateUser(UserDto dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        user.setStatus("INACTIVE");
    }
    
    // 계정 삭제
    @Override
    public void deleteUser(String userEmail) {
        userRepository.delete(userRepository.findByEmail(userEmail));
    }
}
