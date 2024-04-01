package com.ssg.kms.log.search;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long>{


	@Query(value = "SELECT search_log_id, content, user_id, MAX(date) AS date FROM search_log "
			+ "WHERE user_id = :userId GROUP BY content ORDER BY MAX(date) DESC LIMIT 10", nativeQuery = true)
	Set<SearchLog> findTop10ByUserIdOrderByDateDesc(Long userId);

//	@Query(value = "DELETE FROM search_log WHERE search_log_id = searchLogId AND  user_id = userId", nativeQuery = true)
//	Set<SearchLog> deleteBySearchLogIdAndUserId(Long searchLogId, Long id); 
	
	
//	@Modifying
//	@Query(value = "DELETE FROM search_log WHERE content = :content "
//			+ "AND user_id = :userId ORDER BY date DESC LIMIT 1;", nativeQuery = true)
//	Set<SearchLog> deleteTop1ByContentAndUserIdOrderByDateDesc(String content, Long userId);

	@Modifying
	@Query(value = "DELETE FROM search_log WHERE content = :content "
	        + "AND user_id = :userId ORDER BY date DESC LIMIT 1;", nativeQuery = true)
	void deleteTop1ByContentAndUserIdOrderByDateDesc(String content, Long userId);
	
	
//	Set<SearchLog> deleteTop1ByContentAndUserIdOrderByDateDesc(String content, Long userId);
}
 