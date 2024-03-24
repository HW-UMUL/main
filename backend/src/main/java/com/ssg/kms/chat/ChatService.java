package com.ssg.kms.chat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.chatroom.ChatRoom;
import com.ssg.kms.chatroom.ChatRoomRepository;
import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	
	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;
	
    @Transactional
    public Chat createChat(Long chatRoomId, ChatDTO chatDto, Optional<User> user) {
    	
    	ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();

    	Chat chat = Chat.builder()
    			.content(chatDto.getContent())
    			.date(new Date())
    			.user(user.get())
    			.chatRoom(chatRoom)
    			.build();
    	
    	chatRepository.save(chat);
    	    	
		return chat;
    }
    

    @Transactional(readOnly = true)
    public List<Chat> readChat(Long chatRoomId, Optional<User> user) {
    	return chatRepository.findAllByChatRoomId(chatRoomId);
    }

    @Transactional
    public void deleteChat(Long chatId, Optional<User> user) {
    	chatRepository.deleteById(chatId);
    }
}
