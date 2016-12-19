package network.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import network.config.endpoint.MessageMappingEndpoint;
import network.config.endpoint.WebSocketEndpoint;
import network.user.ConnectEventListener;
import network.user.UserRepository;

@Configuration
public class BeanConfig {

	public static class Destinations {
		private Destinations() {
		}

		private static final String LOGIN = "/topic/chat.login";
		private static final String LOGOUT = "/topic/chat.logout";
	}

	@Bean
	public ConnectEventListener presenceEventListener(SimpMessagingTemplate messagingTemplate) {
		ConnectEventListener presence = new ConnectEventListener(messagingTemplate, participantRepository());
		presence.setLoginDestination(Destinations.LOGIN);
		presence.setLogoutDestination(Destinations.LOGOUT);
		return presence;
	}

	@Bean
	public UserRepository participantRepository() {
		return new UserRepository();
	}
	
	@Bean
	public WebSocketEndpoint websocketEndpoint(WebSocketMessageBrokerStats stats) {
		return new WebSocketEndpoint(stats);
	}
	
	@Bean
	public MessageMappingEndpoint messageMappingEndpoint() {
		return new MessageMappingEndpoint();
	}
	
	@Bean
	public ExecutorSubscribableChannel executorSubscribableChannel(){
		return new ExecutorSubscribableChannel();
	}
}
