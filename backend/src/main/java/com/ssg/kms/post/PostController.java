package com.ssg.kms.post;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
	private final PostService postService;
	private final UserService userService;
	
	/////////////////////////////////////////////////
	// Own
	/////////////////////////////////////////////////
	@PostMapping("/create")
	public ResponseEntity<Post> createPost(@Valid @RequestBody PostDTO PostDto) {
		return ResponseEntity.ok(postService.createPost(PostDto, userService.getMyUserWithAuthorities()));
	}
	
	@GetMapping("/read/{postId}")
    public ResponseEntity<Post> readPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.readPost(postId, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/read")
    public ResponseEntity<List<Post>> readPost() {
        return ResponseEntity.ok(postService.readAllPost(userService.getMyUserWithAuthorities()));
    }
	
	@PutMapping("/update/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @Valid @RequestBody PostDTO postDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postDto, userService.getMyUserWithAuthorities()));
    }
	
	@DeleteMapping("/delete/{postId}")
    public ResponseEntity<Post> deletePost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.deletePost(postId, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/search/{searchKeyword}")
    public ResponseEntity<List<Post>> searchPage(@PathVariable String searchKeyword) {        
        return ResponseEntity.ok(postService.searchPost(searchKeyword, userService.getMyUserWithAuthorities()));
    }
	/////////////////////////////////////////////////
	// Table
	/////////////////////////////////////////////////
	@PostMapping("/create/{tableId}")
	public ResponseEntity<Post> createTablePost(@PathVariable Long tableId, @Valid @RequestBody PostDTO PostDto) {
		return ResponseEntity.ok(postService.createPost(tableId, PostDto, userService.getMyUserWithAuthorities()));
	}

}
