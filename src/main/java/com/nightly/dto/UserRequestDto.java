package com.nightly.dto;

import com.nightly.entity.User;

import jakarta.validation.constraints.Pattern;
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
	private String name;
	private String nickname;

	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$")
	private String currentPassword;
	
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$")
	private String changePassword;

	@Pattern(regexp ="^[0-9]{11}$")
	private String phone;
	
	private String profileImage;
	
	private String content;
	
	public static User toEntity(User user, UserRequestDto dto) {
        user.setProfileImage(dto.getProfileImage());
        user.setPassword(dto.getChangePassword());
        user.setNickname(dto.getNickname());
        user.setPhone(dto.getPhone());
        user.setProfileImage(dto.getProfileImage());
        user.setContent(dto.getContent());
	  return user;
	}
}
