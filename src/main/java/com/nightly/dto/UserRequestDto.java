package com.nightly.dto;

import org.springframework.web.multipart.MultipartFile;

import com.nightly.entity.User;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestDto {

	private String email;
	private String name;
	private String nickname;

	/**
	 * 비밀번호 유효성 검사를 위한 정규식(Regex)
	 * 1. ^ 
	 * 	  → 문자열의 시작
	 * 2. (?=.*[A-Za-z]) 
	 * 	  → 적어도 하나 이상의 영문자 포함 (대소문자 가능)
	 * 3. (?=.*\d) 
	 * 	  → 적어도 하나 이상의 숫자 포함
	 * 4. (?=.*[!@#$%^&*(),.?":{}|<>]) 
	 *    → 적어도 하나 이상의 특수문자 포함
	 * 5. [A-Za-z\d!@#$%^&*(),.?":{}|<>]{8,20} 
	 *    → 허용된 문자(영문, 숫자, 특수문자)만 사용 가능하며 길이는 8~20자
	 * 6. $ 
	 * 	  → 문자열의 끝
	 */
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
