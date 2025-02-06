package com.nightly.repository;

import com.nightly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

  // 유저 정보 얻기
  User findByEmail(String email);

  // 이메일 중복 확인
  boolean existsByEmail(String email);

  // 전화번호 중복 확인
  boolean existsByPhone(String phone);

  // 닉네임 중복 확인
  boolean existsByNickname(String nickName);

  // 비밀번호 재설정
  Optional<User> findOptionalByEmail(String email);
}
