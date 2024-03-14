package com.ssg.kms.tableuser;

import java.util.List;

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

import com.ssg.kms.mapping.GetTableMapping;
import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tableuser")
public class TableUserController {
	private final TableUserService tableUserService;
	private final UserService userService;
	
	//초대 - invite
	@PostMapping("/create/{tableId}")
	public ResponseEntity<TableUser> createTableUser(@PathVariable Long tableId, @RequestBody TableUserDTO tableUserDto) {
		return ResponseEntity.ok(tableUserService.createTableUser(tableId, tableUserDto, userService.getMyUserWithAuthorities()));
	}	
	
	//개별 내역 확인
	@GetMapping("/read/{tableUserId}")
    public ResponseEntity<TableUser> readTableUser(@PathVariable Long tableUserId) {
        return ResponseEntity.ok(tableUserService.readTableUser(tableUserId, userService.getMyUserWithAuthorities()));
    }
	
	//포함된 모든 내역 확인
	@GetMapping("/read")
    public ResponseEntity<List<GetTableMapping>> readAllTableUser() {
        return ResponseEntity.ok(tableUserService.readAllTableUser(userService.getMyUserWithAuthorities()));
    }
	
	//승인 - accept 
	@PutMapping("/update/{tableUserId}")
    public ResponseEntity<TableUser> updateTableUser(@PathVariable Long tableUserId) {
        return ResponseEntity.ok(tableUserService.updateTableUser(tableUserId, userService.getMyUserWithAuthorities()));
    }
	
	//거절 - reject
	@DeleteMapping("/delete/{tableUserId}")
    public BodyBuilder deleteTableUser(@PathVariable Long tableUserId) {
		tableUserService.deleteTableUser(tableUserId, userService.getMyUserWithAuthorities());
        return ResponseEntity.ok();
    }
}
