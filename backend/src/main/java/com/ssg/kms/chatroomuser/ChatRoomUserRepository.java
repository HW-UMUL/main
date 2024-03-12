package com.ssg.kms.chatroomuser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

	List<ChatRoomUser> findAllByChatRoomId(Long chatRoomId);

}
