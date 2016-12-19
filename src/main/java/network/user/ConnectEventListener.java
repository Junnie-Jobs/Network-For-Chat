package network.user;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ConnectEventListener {
	
	private UserRepository userRepository;
	
	private SimpMessagingTemplate messagingTemplate;
	
	private String loginDestination;
	
	private String logoutDestination;
	
	public ConnectEventListener(SimpMessagingTemplate messagingTemplate, UserRepository userRepository) {
		this.messagingTemplate = messagingTemplate;
		this.userRepository = userRepository;
	}
		
	@EventListener
	private void whenSessionConnected(SessionConnectEvent event) {
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String username = headers.getUser().getName();
		LoginEvent loginEvent = new LoginEvent(username);
		messagingTemplate.convertAndSend(loginDestination, loginEvent);
		userRepository.add(headers.getSessionId(), loginEvent);
	}
	
	@EventListener
	private void whenSessionDisconnect(SessionDisconnectEvent event) {
		
		Optional.ofNullable(userRepository.getParticipant(event.getSessionId()))
				.ifPresent(login -> {
					messagingTemplate.convertAndSend(logoutDestination, new LogoutEvent(login.getUsername()));
					userRepository.removeParticipant(event.getSessionId());
				});
	}


}
