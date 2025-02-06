package com.nightly.service;

import com.nightly.dto.SignupRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
  
  // 회원 가입
  ResponseEntity<?> signUp(SignupRequestDto dto);
  
}
