package com.ssg.kms.log.search;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/searchlog")
public class SearchLogController {
	private final SearchLogService searchLogService;
	private final UserService userService;
	
//	@PostMapping("/send")
//	public ResponseEntity<SearchLog> sendSearchLog(@RequestBody SearchLogDTO searchLogDTO){
//		return ResponseEntity.ok(searchLogService.sendSearchLog(searchLogDTO, userService.getMyUserWithAuthorities()));
//	}

	@PostMapping("/save")
	public ResponseEntity<SearchLog> saveSearchLog(@RequestBody SearchLog searchLog){
		return ResponseEntity.ok(searchLogService.saveSearchLog(searchLog, userService.getMyUserWithAuthorities()));
	}
	
	@GetMapping("/read")
	public ResponseEntity<Set<SearchLog>> readSearchLog(){
		return ResponseEntity.ok(searchLogService.readSearchLog(userService.getMyUserWithAuthorities()));
	}
	 
	@DeleteMapping("/delete/{content}")
	public ResponseEntity<SearchLog> deleteSearchLog(@PathVariable String content){
		return ResponseEntity.ok(searchLogService.deleteSearchLog(content, userService.getMyUserWithAuthorities()));	
	}
	
//	@DeleteMapping("/delete")
//	public ResponseEntity<SearchLog> deleteSearchLog(@RequestParam Long searchLogId){
//		return ResponseEntity.ok(searchLogService.deleteSearchLog(searchLogId, userService.getMyUserWithAuthorities()));	
//	}
	
}
