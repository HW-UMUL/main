package com.ssg.kms.alarm.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyAlarmRepository extends JpaRepository<ReplyAlarm, Long> {
	
	@Query(value = "DELETE ra FROM reply_alarm ra WHERE ra.post_id IN :postIds AND ra.user_id = :userId", nativeQuery = true)
	void deleteByPostIdInAndUserId(List<Long> postIds, Long userId);

//	void deleteByPostInAndUser(List<Post> posts, User user);

}
