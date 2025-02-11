package com.nightly.repository;

import com.nightly.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
  Optional<RefreshToken> findByRefreshToken(String refreshToken);
  Optional<RefreshToken> findByEmail(String email);
  void deleteByRefreshToken(String refreshToken);
}
