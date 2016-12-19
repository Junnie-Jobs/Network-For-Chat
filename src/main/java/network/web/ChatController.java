package network.web;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import network.chat.Chat;
import network.user.LoginEvent;
import network.user.UserRepository;

@Controller
public class ChatController {

	@Autowired 
	private UserRepository userRepository;
	
	@SubscribeMapping("/chat.participants")
	public Collection<LoginEvent> findActiveUsers() {
		return userRepository.getActiveSessions().values();
	}
	
	@MessageMapping("/chat.message")
	public Chat filterMessage(@Payload Chat message, Principal principal) {
		message.setUsername(principal.getName());
		return message;
	}
	
	
	

}