package com.nightly.service;

import com.nightly.dto.SigninRequestDto;
import com.nightly.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
  
  // 회원 가입
  ResponseEntity<?> signUp(SignupRequestDto dto);

  // 로그인
  ResponseEntity<?> login(SigninRequestDto dto, HttpServletResponse response);

  // 로그아웃
  // ResponseEntity<?> logout(HttpServletResponse response);
}
