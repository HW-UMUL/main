package com.ssg.kms.tableuser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.mapping.GetTableMapping;

public interface TableUserRepository extends JpaRepository<TableUser, Long> {

	
	List<GetTableMapping> findAllByUserId(Long userId);
	
}