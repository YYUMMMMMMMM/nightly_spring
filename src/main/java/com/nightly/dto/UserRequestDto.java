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
public class UserRequestDto {

	private String email;
	private String password;
	private String name;
	private String nickname;
	private String phone;
	
	private String profileImage;
	private String status;
	
	private String content;
	
	public static User toEntity(UserRequestDto dto) {
	  return User.builder()
	                .email(dto.getEmail())
	                .password(dto.getPassword())
	                .name(dto.getName())
	                .nickname(dto.getNickname())
	                .phone(dto.getPhone())
	                .profileImage(dto.getProfileImage())
	                .status(dto.getStatus())
	                .content(dto.getContent())
	                .build();
	}
}
