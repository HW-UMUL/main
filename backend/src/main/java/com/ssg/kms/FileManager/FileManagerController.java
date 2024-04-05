package com.ssg.kms.FileManager;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileManagerController {
	private final FileManagerService fileManagerService;
	private final UserService userService;
	
	/////////////////////////////////////////////////
	// Own
	/////////////////////////////////////////////////
	@PostMapping("/create")
	public ResponseEntity<List<FileManager>> createFile(@RequestPart(value = "files", required = false) MultipartFile[] files) throws Exception {
		return ResponseEntity.ok(fileManagerService.createFile(files, userService.getMyUserWithAuthorities()));
	}

	@GetMapping("/read")
    public ResponseEntity<InputStreamResource> readFile() throws FileNotFoundException {
		
        return ResponseEntity.ok(fileManagerService.readFile());
    }

	/*

	@GetMapping("/read/{fileId}")
    public ResponseEntity<Post> readPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.readPost(postId, userService.getMyUserWithAuthorities()));
    }
    
	@PutMapping("/update/{fileId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @Valid @RequestBody PostDTO postDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postDto, userService.getMyUserWithAuthorities()));
    }
	
	@DeleteMapping("/delete/{postId}")
    public ResponseEntity<Post> deletePost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.deletePost(postId, userService.getMyUserWithAuthorities()));
    }
    */
}
