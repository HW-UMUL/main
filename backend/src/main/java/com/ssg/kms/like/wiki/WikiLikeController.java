package com.ssg.kms.like.wiki;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.mapping.GetWikiMapping;
import com.ssg.kms.user.User;
import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wikilike")
public class WikiLikeController {
	private final WikiLikeService wikiLikeService;
	private final UserService userService;
	
	@PostMapping("/check/{wikiId}")
	public ResponseEntity checkwikiLike(@PathVariable Long wikiId) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		wikiLikeService.checkwiki(wikiId, user);
		
		return ResponseEntity.ok(wikiLikeService.readLike(wikiId, user));
	}
	
	@GetMapping("/read/{wikiId}")
    public ResponseEntity readwikiLike(@PathVariable Long wikiId) {
		return ResponseEntity.ok(wikiLikeService.readLike(wikiId, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/read/my")
    public ResponseEntity<List<GetWikiMapping>> readMywikiLike() {
		return ResponseEntity.ok(wikiLikeService.readMyLike(userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/ischeck/{wikiId}")
    public ResponseEntity isCheck(@PathVariable Long wikiId) {
		return ResponseEntity.ok(wikiLikeService.isCheck(wikiId, userService.getMyUserWithAuthorities()));
    }
	///////////////////////
	
	@GetMapping("/readLikePersonal/{wikiId}")
	public ResponseEntity readLikePersonal(@PathVariable Long wikiId) {
		return ResponseEntity.ok(wikiLikeService.readLikePersonal(wikiId, userService.getMyUserWithAuthorities()));
	}
}
