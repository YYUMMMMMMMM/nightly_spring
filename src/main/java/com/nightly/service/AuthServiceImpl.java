package com.nightly.service;

import com.nightly.dto.SignupRequestDto;
import com.nightly.entity.User;
import com.nightly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  // private final PasswordEncoder passwordEncoder;

  @Override
  public ResponseEntity<?> signUp(SignupRequestDto dto) {
    try {
      System.out.println(dto);
      // 이메일 중복 검사
      String email = dto.getEmail();
      boolean existedEmail = userRepository.existsByEmail(email);
      if (existedEmail) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입된 계정의 이메일 입니다.");
      }

      // 전화번호 중복 검사
      String phone = dto.getPhone();
      boolean existedPhone = userRepository.existsByPhone(dto.getPhone());
      if (existedPhone) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입된 계정의 전화번호 입니다.");
      }

      // 비밀번호 암호화
      String password = dto.getPassword();
      // String encodedPassword = passwordEncoder.encode(password);
      // dto.setPassword(encodedPassword);

      User user = User.signUpToEntity(dto);
      userRepository.save(user);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스 서버 에러 입니다.");
    }
    return ResponseEntity.status(HttpStatus.OK).body("가입이 완료되었습니다.");
  }
}
