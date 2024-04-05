package com.ssg.kms.star.wiki;

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
@RequestMapping("/api/wikistar")
public class WikiStarController {
	private final WikiStarService wikiStarService;
	private final UserService userService;
	
	@PostMapping("/check/{wikiId}")
	public ResponseEntity checkWikiStar(@PathVariable Long wikiId) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		wikiStarService.checkWiki(wikiId, user);
		
		return ResponseEntity.ok(wikiStarService.readStar(wikiId, user));
	}
	
	@GetMapping("/read/{wikiId}")
    public ResponseEntity readWikiStar(@PathVariable Long wikiId) {
		return ResponseEntity.ok(wikiStarService.readStar(wikiId, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/read/my")
    public ResponseEntity<List<GetWikiMapping>> readMyWikiStar() {
		return ResponseEntity.ok(wikiStarService.readMyStar(userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/ischeck/{wikiId}")
    public ResponseEntity isCheck(@PathVariable Long wikiId) {
		return ResponseEntity.ok(wikiStarService.isCheck(wikiId, userService.getMyUserWithAuthorities()));
    }
	
	////////////////////
	
	@GetMapping("/readStarPersonal/{wikiId}")
	public ResponseEntity readStarPersonal(@PathVariable Long wikiId) {
		return ResponseEntity.ok(wikiStarService.readStarPersonal(wikiId, userService.getMyUserWithAuthorities()));
	}
	
	
}
