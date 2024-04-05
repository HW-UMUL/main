package com.ssg.kms.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat, Long>{

	List<Chat> findAllByChatRoomId(Long chatRoomId);

	List<Chat> findAllByUserId(Long id);

	@Query(value = "SELECT * FROM chat c WHERE c.chat_room_id = :chatRoomId ORDER By c.id DESC LIMIT 1", nativeQuery = true)
	Chat findTopByChatRoomIdOrderByidDesc(Long chatRoomId);

}
