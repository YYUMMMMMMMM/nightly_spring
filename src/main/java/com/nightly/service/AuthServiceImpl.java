package com.nightly.service;

import com.nightly.dto.SigninRequestDto;
import com.nightly.dto.SignupRequestDto;
import com.nightly.entity.RefreshToken;
import com.nightly.entity.User;
import com.nightly.jwt.JwtUtil;
import com.nightly.repository.RefreshTokenRepository;
import com.nightly.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final CustomUserDetailService customUserDetailService;
  private final RefreshTokenRepository refreshTokenRepository;

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
       String encodedPassword = passwordEncoder.encode(password);
       dto.setPassword(encodedPassword);

      User user = User.signUpToEntity(dto);
      userRepository.save(user);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스 서버 에러 입니다.");
    }
    return ResponseEntity.status(HttpStatus.OK).body("가입이 완료되었습니다.");
  }

  @Override
  public ResponseEntity<?> login(SigninRequestDto dto, HttpServletResponse response) {

    String email = dto.getEmail();
    String password = dto.getPassword();

    try {
      // 이메일 유효성 검사
      UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
      if (userDetails == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("가입된 계정이 없습니다.");
      }

      // 회원 상태 확인
      User user = userRepository.findByEmail(email);
      if (user == null || "INACTIVE".equals(user.getStatus())) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비활성화된 계정입니다.");
      }

      // 비밀번호 유효성 검사
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 맞지 않습니다.");
      }

      // JWT 토큰 생성
      String accessToken = jwtUtil.generateAccessToken(email);
      String refreshToken = jwtUtil.generateRefreshToken(email);

      saveRefreshToken(email, refreshToken);

      Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
      accessTokenCookie.setHttpOnly(true);
      accessTokenCookie.setSecure(false);
      accessTokenCookie.setPath("/");
      accessTokenCookie.setMaxAge(60 * 60);
      response.addCookie(accessTokenCookie);
      System.out.println("accessToken : " + accessToken);
      Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
      refreshTokenCookie.setHttpOnly(true);
      refreshTokenCookie.setSecure(false);
      refreshTokenCookie.setPath("/");
      refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7);
      response.addCookie(refreshTokenCookie);
      System.out.println("refreshToken : " + refreshToken);
      Map<String, String> tokens = new HashMap<>();
      tokens.put("accessToken", accessToken);
      tokens.put("refreshToken", refreshToken);
      System.out.println("tokens : " + tokens);

      return ResponseEntity.status(HttpStatus.OK).body(tokens);
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 잘못되었습니다.");
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("가입된 계정이 없습니다.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 오류가 발생했습니다.");
    }
  }

  // 리프레쉬 토큰 DB 저장
  @Transactional
  protected void saveRefreshToken(String email, String refreshToken) {
    // 기존에 리프레시 토큰이 있으면 삭제하고 새로 저장
    Optional<RefreshToken> existingToken = refreshTokenRepository.findByEmail(email);
    existingToken.ifPresent(refreshTokenRepository::delete); // 기존 리프레시 토큰 삭제

    // 새로운 리프레시 토큰 저장
    RefreshToken refreshTokenEntity = new RefreshToken();
    refreshTokenEntity.setEmail(email);
    refreshTokenEntity.setRefreshToken(refreshToken);
    refreshTokenEntity.setExpiryTime(LocalDateTime.now().plusWeeks(1)); // 리프레시 토큰의 만료일을 설정 (1주일)
    refreshTokenRepository.save(refreshTokenEntity);
  }
}
