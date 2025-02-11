package com.nightly.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$")
  // 최소 8자 이상 최대 20자 이하, 숫자, 특수문자, 영문자가 포함
  private String password;

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z가-힣]{2,20}$")
  private String name;

  @NotBlank
  private String nickname;

  @NotBlank
  @Pattern(regexp ="^[0-9]{11}$")
  private String phone;

  private String profileImage;
  private String role;
  private LocalDateTime signupTime;
  private LocalDateTime updateTime;
  private int followingCount;
  private int followerCount;
  private String status;
  private String content;
}
