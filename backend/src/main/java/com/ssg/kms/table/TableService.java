package com.ssg.kms.table;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.post.PostRepository;
import com.ssg.kms.tableuser.TableUser;
import com.ssg.kms.tableuser.TableUserRepository;
import com.ssg.kms.user.User;
import com.ssg.kms.wiki.WikiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;
    private final PostRepository postRepository;
    private final WikiRepository wikiRepository;
    private final TableUserRepository tableUserRepository;
    
    @Transactional
    public Tables createTable(TableDTO tableDto, Optional<User> user) {
 
    	Tables table = Tables.builder()
    			.name(tableDto.getName())
    			.description(tableDto.getDesc())
    			.date(new Date())
    			.build();
    	
    	TableUser tableUser = TableUser.builder()
    			.accept(true)
    			.user(user.get())
    			.table(table)
    			.build();
    	
    	tableRepository.save(table);    	
    	tableUserRepository.save(tableUser);

    	return table;
    }
    
    @Transactional(readOnly = true)
    public Tables readTable(Long tableId, Optional<User> user) {
    	return tableRepository.findById(tableId).get();
    }

    @Transactional
    public Tables updateTable(Long tableId, TableDTO tableDto, Optional<User> user) {
    	Tables table = tableRepository.findById(tableId).get();
    	
    	table.setName(tableDto.getName());
    	table.setDescription(tableDto.getDesc());
    	
		return tableRepository.save(table);
    }

    @Transactional
    public void deleteTable(Long tableId, Optional<User> user) {
    	postRepository.deleteAllByTableId(tableId);
    	wikiRepository.deleteAllByTableId(tableId);
    	tableRepository.deleteById(tableId);    	    	
    }
}
