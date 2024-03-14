package com.ssg.kms.tableuser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.mapping.GetTableMapping;
import com.ssg.kms.table.TableRepository;
import com.ssg.kms.table.Tables;
import com.ssg.kms.user.User;
import com.ssg.kms.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableUserService {
    private final TableUserRepository tableUserRepository;
    private final TableRepository tableRepository;    
    private final UserRepository userRepository;
    
    @Transactional
    public TableUser createTableUser(Long tableId, TableUserDTO tableUserDto, Optional<User> user) {
 
    	User foundUser = userRepository.findByEmail(tableUserDto.getEmail());
    	Tables table = tableRepository.findById(tableId).get();
    	    	
    	TableUser tableUser = TableUser.builder()
    			.user(foundUser)
    			.table(table)
    			.accept(false)
    			.build();

    	return tableUserRepository.save(tableUser);
    }
    
    @Transactional(readOnly = true)
    public TableUser readTableUser(Long tableUserId, Optional<User> user) {
    	return tableUserRepository.findById(tableUserId).get();
    }
    
    @Transactional(readOnly = true)
    public List<GetTableMapping> readAllTableUser(Optional<User> user) {
    	
    	return tableUserRepository.findAllByUserId(user.get().getId());
    	
    }
    
    // 승인
    @Transactional
    public TableUser updateTableUser(Long tableUserId, Optional<User> user) {
    	
    	TableUser tableUser = tableUserRepository.findById(tableUserId).get();
    	
    	tableUser.setAccept(true);
    	
		return tableUserRepository.save(tableUser);
    }

    // 거절
    @Transactional
    public void deleteTableUser(Long tableUserId, Optional<User> user) {
    	tableUserRepository.deleteById(tableUserId);    	    	
    }
}
