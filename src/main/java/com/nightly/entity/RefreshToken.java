package com.nightly.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer number;
  private String email;
  private String refreshToken;
  private LocalDateTime expiryTime;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
