package com.ssg.kms.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	private final UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.signup(userDto));
	}

	@PostMapping("/testtest")
	public ResponseEntity<User> test(@Valid @RequestBody UserDto userDto) {
		System.out.println("test");
		return ResponseEntity.ok(userService.test(userDto));
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<User> getMyUserInfo() {
		return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<User> getUserInfo(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
	}
}
