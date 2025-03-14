package com.nightly.controller;

import com.nightly.dto.SigninRequestDto;
import com.nightly.dto.SignupRequestDto;
import com.nightly.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody SignupRequestDto dto) {
    ResponseEntity<?> response = authService.signUp(dto);
    return response;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody SigninRequestDto dto, HttpServletResponse response) {
    ResponseEntity<?> signResponse = authService.login(dto, response);
    return signResponse;
  }
}
