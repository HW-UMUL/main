package com.ssg.kms.like.post;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.mapping.GetPostMapping;
import com.ssg.kms.user.User;
import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/postlike")
public class PostLikeController {
	private final PostLikeService postLikeService;
	private final UserService userService;
	
	@PostMapping("/check/{postId}")
	public ResponseEntity checkPostLike(@PathVariable Long postId) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		postLikeService.checkPost(postId, user);
		
		return ResponseEntity.ok(postLikeService.readLike(postId, user));
	}
	
	@GetMapping("/read/{postId}")
    public ResponseEntity readPostLike(@PathVariable Long postId) {
		return ResponseEntity.ok(postLikeService.readLike(postId, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/read/my")
    public ResponseEntity<List<GetPostMapping>> readMyPostLike() {
		return ResponseEntity.ok(postLikeService.readMyLike(userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/ischeck/{postId}")
    public ResponseEntity isCheck(@PathVariable Long postId) {
		return ResponseEntity.ok(postLikeService.isCheck(postId, userService.getMyUserWithAuthorities()));
    }

	////////////////////////
	@GetMapping("/readLikePersonal/{postId}")
	public ResponseEntity readLikePersonal(@PathVariable Long postId) {
		return ResponseEntity.ok(postLikeService.readLikePersonal(postId, userService.getMyUserWithAuthorities()));
	}

}
