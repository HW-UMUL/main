package com.ssg.kms.websocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.ssg.kms.chat.Chat;
import com.ssg.kms.chat.ChatDTO;
import com.ssg.kms.chat.ChatService;
import com.ssg.kms.chatroomuser.ChatRoomUser;
import com.ssg.kms.chatroomuser.ChatRoomUserService;
import com.ssg.kms.security.TokenProvider;
import com.ssg.kms.user.User;
import com.ssg.kms.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {
	
	private Map<String, WebSocketSession> sessions = new HashMap<>();
	private final TokenProvider tokenProvider;
	private final UserRepository userRepository;
	private final JSONParser jsonParser = new JSONParser();
	private final ChatRoomUserService chatRoomUserService;
	private final ChatService chatService;
	
	// 연결시
	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String username = getUserNameBySessionWithJwt(session);
		sessions.put(username, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	String username = getUserNameBySessionWithJwt(session);
    	sessions.remove(username);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	String username = getUserNameBySessionWithJwt(session);
    	
    	JSONObject jsonObj = (JSONObject)jsonParser.parse(message.getPayload());
    	
    	//chatRoomId chatDTO
    	Long chatRoomId = Long.parseLong((String)jsonObj.get("chatRoomId"));
    	String content = (String)jsonObj.get("content");
    	
    	ChatDTO chatDto = ChatDTO.builder()
    			.content(content)
    			.build();
    	
    	// 채팅방 유저 가져오기
    	Optional<User> user = userRepository.findByUsername(username);
    	
    	List<ChatRoomUser> chatRoomUsers = 
    			chatRoomUserService.readChatRoomUser(chatRoomId, user);
    	
    	for(ChatRoomUser chatRoomUser : chatRoomUsers) {
    		String foundUsername = chatRoomUser.getUser().getUsername();
    		if(!foundUsername.equals(username)) {
    			Chat chat = chatService.createChat(chatRoomId, chatDto, user);
    			TextMessage textMessage = new TextMessage(String.format("{\"chatId\" : \"%s\"}", Long.toString(chat.getId())));
    			sessions.get(foundUsername).sendMessage(textMessage);
    		}
    	}
    	    	
    }
    
    private String extractUserIdFromCookie(String cookieValue) {
        String[] cookiePairs = cookieValue.split(";");
        
        for (String cookiePair : cookiePairs) {
            String[] pair = cookiePair.trim().split("=");
            if (pair.length == 2) {
                if ("jwtToken".equals(pair[0])) {
                    return pair[1];
                }
            }
        }
        return null;
    }
    
    private String getUserNameBySessionWithJwt(WebSocketSession session) {
    	String cookieValue = session.getHandshakeHeaders().getFirst("Cookie");
    	String jwt = extractUserIdFromCookie(cookieValue);
    	
    	Authentication authentication = tokenProvider.getAuthentication(jwt);    	
    	String username = authentication.getName();
    	return username;
    }
}
