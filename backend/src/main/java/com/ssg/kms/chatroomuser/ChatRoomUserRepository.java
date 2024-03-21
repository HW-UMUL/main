package com.ssg.kms.chatroomuser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.mapping.GetUserMapping;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

	List<ChatRoomUser> findAllByChatRoomId(Long chatRoomId);

	List<GetUserMapping> findAllUserByChatRoomId(Long chatRoomId);

	List<ChatRoomUser> findAllByUserId(Long id);

	Optional<ChatRoomUser> findByUserId(Long id);

	void deleteAllByUserId(Long id);

	List<ChatRoomUser> findByChatRoomIdAndUserId(Long chatRoomId, Long userId);

}
