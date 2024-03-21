package com.ssg.kms.wiki;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiRepository extends JpaRepository<Wiki, Long> {

	void deleteAllByTableId(Long tableId);

	List<Wiki> findAllByCategoryName(String categoryName);

	List<Wiki> findAllByUserId(Long id);

}
