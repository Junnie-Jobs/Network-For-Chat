package network.web;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import network.domain.ChatMessage;
import network.event.LoginEvent;
import network.event.ParticipantRepository;


@Controller
public class ChatController {

	@Autowired 
	private ParticipantRepository participantRepository;
	
	@Autowired 
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	@SubscribeMapping("/chat.participants")
	public Collection<LoginEvent> retrieveParticipants() {
		return participantRepository.getActiveSessions().values();
	}
	
	@MessageMapping("/chat.message")
	public ChatMessage filterMessage(@Payload ChatMessage message, Principal principal) {
		message.setUsername(principal.getName());
		return message;
	}
	
	@MessageMapping("/chat.private.{username}")
	public void filterPrivateMessage(@Payload ChatMessage message, @DestinationVariable("username") String username, Principal principal) {
		message.setUsername(principal.getName());
		simpMessagingTemplate.convertAndSend("/user/" + username + "/exchange/amq.direct/chat.message", message);
	}

}