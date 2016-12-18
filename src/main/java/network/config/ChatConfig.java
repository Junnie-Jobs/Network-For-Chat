package network.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import network.config.endpoint.MessageMappingEndpoint;
import network.config.endpoint.WebSocketEndpoint;
import network.event.ParticipantRepository;
import network.event.PresenceEventListener;

@Configuration
public class ChatConfig {

	public static class Destinations {
		private Destinations() {
		}

		private static final String LOGIN = "/topic/chat.login";
		private static final String LOGOUT = "/topic/chat.logout";
	}

	@Bean
	public PresenceEventListener presenceEventListener(SimpMessagingTemplate messagingTemplate) {
		PresenceEventListener presence = new PresenceEventListener(messagingTemplate, participantRepository());
		presence.setLoginDestination(Destinations.LOGIN);
		presence.setLogoutDestination(Destinations.LOGOUT);
		return presence;
	}

	@Bean
	public ParticipantRepository participantRepository() {
		return new ParticipantRepository();
	}

	@Bean
	public WebSocketEndpoint websocketEndpoint(WebSocketMessageBrokerStats stats) {
		return new WebSocketEndpoint(stats);
	}

	@Bean
	public MessageMappingEndpoint messageMappingEndpoint() {
		return new MessageMappingEndpoint();
	}
}
