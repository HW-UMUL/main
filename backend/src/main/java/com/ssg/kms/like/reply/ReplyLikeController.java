package com.ssg.kms.like.reply;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.user.User;
import com.ssg.kms.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replylike")
public class ReplyLikeController {
	private final ReplyLikeService replyLikeService;
	private final UserService userService;
	
	@PostMapping("/check/{replyId}")
	public ResponseEntity checkPostLike(@PathVariable Long replyId) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		replyLikeService.checkReply(replyId, user);
		
		return ResponseEntity.ok(replyLikeService.readLike(replyId, user));
	}
	
	@GetMapping("/read/{replyId}")
    public ResponseEntity readPostLike(@PathVariable Long replyId) {
		return ResponseEntity.ok(replyLikeService.readLike(replyId, userService.getMyUserWithAuthorities()));
    }
}
