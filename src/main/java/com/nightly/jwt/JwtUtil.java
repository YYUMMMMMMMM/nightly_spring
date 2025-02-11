package com.nightly.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  private final String SECRET_KEY = "mySuperSecretKey12345678901234567890123456789012";
  private final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000L;
  private final long REFRESH_TOKEN_EXPIRE_TIME = 60 * 60 * 24 * 7 * 1000l;

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  // JWT 생성
  public String generateToken(String email, long expireTime) {

    Date expiredDate = new Date(System.currentTimeMillis() + expireTime);

    return Jwts.builder()
        .signWith(getSecretKey(), SignatureAlgorithm.HS256)
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(expiredDate)
        .compact();
  }

  // 엑세스 토큰 생성
  public String generateAccessToken(String email) {
    return generateToken(email, ACCESS_TOKEN_EXPIRE_TIME);
  }

  // 리프레쉬 토큰 생성
  public String generateRefreshToken(String email) {
    return generateToken(email, REFRESH_TOKEN_EXPIRE_TIME);
  }

  // 페이로드(클레임) 추출
  public Claims extractClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSecretKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  // 로그인 주체 추출
  public String extractUseremail(String token) {
    return extractClaims(token).getSubject();
  }

  // 토큰 검증
  public boolean validateToken(String token) {
    try {
      Claims claims = extractClaims(token);
      return !claims.getExpiration().before(new Date());
    } catch (ExpiredJwtException e) {
      System.out.println("JWT has expired : " + e.getMessage());
      return false;
    } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
      System.out.println("Invalid JWT : " + e.getMessage());
      return false;
    }
  }
}
