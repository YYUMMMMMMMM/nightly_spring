package com.nightly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nightly.dto.UserRequestDto;
import com.nightly.dto.UserResponseDto;
import com.nightly.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/main/profile")
@Slf4j
public class ProfileController {
    private UserService userService;
    
    public ProfileController (UserService userService) {
    	this.userService = userService;
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<Object> getUser(@PathVariable("email") String email) {
    	log.info("이메일 : {}", email);
    	log.info("service 구현 클래스 : {}", this.userService.getClass().getSimpleName());
    	try {
            return ResponseEntity.ok().body(userService.getUser(email));
    	} catch (Exception e) {
    		return ResponseEntity.badRequest().body("사용자 정보를 찾을 수 없습니다."); 
    	}
    }
    
    @PutMapping
    public ResponseEntity<Object> updateUser(
    		@RequestPart("data") UserRequestDto dto, 
    		@RequestPart(value = "imageFile", required = false) MultipartFile file
	) {
    	log.info("[{}] UpdatedUser : {}", getClass().getSimpleName(), dto);
    	UserResponseDto user = userService.updateUser(dto, file);
        if (user == null) {
            return ResponseEntity.badRequest().body("비밀번호 오류");
        }
        return ResponseEntity.ok().body(user);
    }
}
