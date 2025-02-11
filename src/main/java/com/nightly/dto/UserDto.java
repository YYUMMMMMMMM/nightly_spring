package com.nightly.dto;

import java.time.LocalDateTime;

import com.nightly.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

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

  public static UserDto fromEntity(User entity) {
    return UserDto.builder()
                  .email(entity.getEmail())
                  .password(entity.getPassword())
                  .name(entity.getName())
                  .nickname(entity.getNickname())
                  .phone(entity.getPhone())
                  .profileImage(entity.getProfileImage())
                  .role(entity.getRole())
                  .signupTime(entity.getSignupTime())
                  .updateTime(entity.getUpdateTime())
                  .followingCount(entity.getFollowingCount())
                  .followerCount(entity.getFollowerCount())
                  .status(entity.getStatus())
                  .content(entity.getContent())
                  .build();
  }
}
