package com.nightly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nightly.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/main/search")
public class SearchController {

	private UserService userService;
	
	public SearchController (UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/{nickname}")
	public ResponseEntity<Object> getUserList(@PathVariable("nickname") String nickname) {
		log.info("검색어 : {}", nickname);
		try {
			return ResponseEntity.ok().body(userService.getUserList(nickname));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("사용자 정보를 찾을 수 없습니다.");
		}
	}
}
