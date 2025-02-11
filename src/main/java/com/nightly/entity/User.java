package com.nightly.entity;

import com.nightly.dto.SignupRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  private String email;
  private String password;
  private String name;
  private String nickname;
  private String phone;
  private String profileImage;
  private String role;
  private LocalDateTime signupTime;
  private LocalDateTime updateTime;
  private int followingCount;
  private int followerCount;
  private String status;
  private String content;

  // toEntity
  public static User signUpToEntity(SignupRequestDto dto) {
    return User.builder()
        .email(dto.getEmail())
        .password(dto.getPassword())
        .name(dto.getName())
        .nickname(dto.getNickname() != null ? dto.getNickname() : dto.getName())
        .phone(dto.getPhone())
        .profileImage(dto.getProfileImage())
        .role(dto.getRole())
        .signupTime(dto.getSignupTime())
        .updateTime(dto.getUpdateTime())
        .followingCount(dto.getFollowingCount())
        .followerCount(dto.getFollowerCount())
        .status(dto.getStatus())
        .content(dto.getContent())
        .build();
  }
}
