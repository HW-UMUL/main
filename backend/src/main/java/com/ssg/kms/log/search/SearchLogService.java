package com.ssg.kms.log.search;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchLogService {
	
	private final SearchLogRepository searchLogRepository;

//	public SearchLog sendSearchLog(SearchLogDTO searchLogDTO, Optional<User> user) {
//	SearchLog searchLog = SearchLog.builder()
//			.content(searchLogDTO.getContent())
//			.date(searchLogDTO.getDate())
//			.user(user.get())
//			.build();
//	
//	searchLogRepository.save(searchLog);
//	return searchLog;
//	}
	
	@Transactional
	public SearchLog saveSearchLog(SearchLog searchLog, Optional<User> user) {
			// build하지 않아 완전히 저장되어 불러올 때 문제가 생기려나?
			searchLog.setUser(user.get());
			searchLog.setDate(new Date());
		
	searchLogRepository.save(searchLog);
	
	return searchLog;
	}
	
	@Transactional(readOnly = true)
	public Set<SearchLog> readSearchLog(Optional<User> user) {
	    return searchLogRepository.findTop10ByUserIdOrderByDateDesc(user.get().getId());
	}
 

//	@Transactional
//	public SearchLog deleteSearchLog(Long searchLogId, Optional<User> user) {
//		searchLogRepository.deleteBySearchLogIdAndUserId(searchLogId, user.get().getId());
//		SearchLog searchLog = new SearchLog();
//		return searchLog;
//	}
	
	@Transactional // SearchLogIdAndUserId 두개의 조건을 부여해 삭제해야한다.
	public SearchLog deleteSearchLog(String content, Optional<User> user) {
		searchLogRepository.deleteTop1ByContentAndUserIdOrderByDateDesc(content, user.get().getId());
		SearchLog searchLog = new SearchLog();
		return searchLog;
	}

}