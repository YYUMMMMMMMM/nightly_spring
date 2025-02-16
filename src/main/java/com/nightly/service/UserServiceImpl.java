package com.nightly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nightly.dto.UserRequestDto;
import com.nightly.dto.UserResponseDto;
import com.nightly.entity.User;
import com.nightly.exception.UnAuthorizedException;
import com.nightly.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public UserServiceImpl (
    		UserRepository userRepository, 
    		PasswordEncoder passwordEncoder, 
    		ImageService imageService
	) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
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

    // 프로필 수정 정보 : 프로필 이미지, 닉네임, 비밀번호, 전화번호, 자기 소개
    @Override
    public UserResponseDto updateUser(UserRequestDto dto, MultipartFile file) {
        User user = userRepository.findByEmail(dto.getEmail());
        try {
            if (user == null) {
                throw new UsernameNotFoundException("가입된 계정을 찾을 수 없습니다.");
              }
        	// 비밀번호 검증
        	if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
        		throw new UnAuthorizedException("비밀번호가 일치하지 않습니다.");
        	}

        	
        	// 비밀번호 미변경 시  
        	if (dto.getChangePassword() == null) {
        		dto.setChangePassword(passwordEncoder.encode(dto.getCurrentPassword()));
        	}
        	
        	// 기존 프로필 이미지 삭제 후 신규 파일 저장
        	if (file != null) {
        		imageService.deleteImage(imageService.getPublicIdFromCloudinaryUrl(user.getProfileImage()));
        		dto.setProfileImage(String.valueOf(imageService.uploadImage(file).get("url")));
        	}
        	
        	return UserResponseDto.fromEntity(userRepository.save(UserRequestDto.toEntity(user, dto)));
        } catch (Exception e) {
        	log.error("오류 메시지 :  {}", e.getMessage());
        	return null;
        }

    }

    // 계정 활성화 -> 로그인 시 확인 후 계정 활성화 요청
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
        userRepository.save(user);
    }
    
    // 계정 삭제
    @Override
    public void deleteUser(String userEmail) {
        userRepository.delete(userRepository.findByEmail(userEmail));
    }
}
