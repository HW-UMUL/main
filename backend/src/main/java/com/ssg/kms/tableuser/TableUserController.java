package com.ssg.kms.tableuser;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
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
@RequestMapping("/api/tableuser")
public class TableUserController {
	private final TableUserService tableService;
	private final UserService userService;
	
	//초대 - invite
	@PostMapping("/create/{tableId}")
	public ResponseEntity<TableUser> createReply(@PathVariable Long tableId, @RequestBody TableUserDTO tableUserDto) {
		return ResponseEntity.ok(tableService.createTableUser(tableId, tableUserDto, userService.getMyUserWithAuthorities()));
	}	
	
	//개별 내역 확인
	@GetMapping("/read/{tableUserId}")
    public ResponseEntity<TableUser> readReply(@PathVariable Long tableUserId) {
        return ResponseEntity.ok(tableService.readTableUser(tableUserId, userService.getMyUserWithAuthorities()));
    }
	
	//승인 - accept 
	@PutMapping("/update/{tableUserId}")
    public ResponseEntity<TableUser> updateReply(@PathVariable Long tableUserId) {
        return ResponseEntity.ok(tableService.updateTableUser(tableUserId, userService.getMyUserWithAuthorities()));
    }
	
	//거절 - reject
	@DeleteMapping("/delete/{tableUserId}")
    public BodyBuilder deleteTableUser(@PathVariable Long tableUserId) {
		tableService.deleteTableUser(tableUserId, userService.getMyUserWithAuthorities());
        return ResponseEntity.ok();
    }
}
