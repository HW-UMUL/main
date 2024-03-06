package com.ssg.kms.search;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<SearchModel, Integer> {

	List<SearchModel> findByTitleContaining(String searchKeyword);
	
}
