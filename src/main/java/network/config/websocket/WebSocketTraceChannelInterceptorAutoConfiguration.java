package network.config.websocket;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.trace.InMemoryTraceRepository;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import network.config.endpoint.WebSocketTraceEndpoint;
import network.config.trace.WebSocketTraceChannelInterceptor;

@Configuration
public class WebSocketTraceChannelInterceptorAutoConfiguration {

	@Value("${management.websocket.trace-inbound:true}")
	private boolean enableTraceInboundChannel;

	@Value("${management.websocket.trace-outbound:false}")
	private boolean enableTraceOutboundChannel;

	@Autowired
	private ExecutorSubscribableChannel clientInboundChannel;

	@Autowired
	private ExecutorSubscribableChannel clientOutboundChannel;

	private TraceRepository traceRepository = new InMemoryTraceRepository();

	@Bean
	public WebSocketTraceEndpoint websocketTraceEndpoint() {
		return new WebSocketTraceEndpoint(traceRepository);
	}

	@Bean
	public WebSocketTraceChannelInterceptor webSocketTraceChannelInterceptor() {
		return new WebSocketTraceChannelInterceptor(traceRepository);
	}

	@PostConstruct
	private void addTraceInterceptor() {
		if (enableTraceInboundChannel) {
			clientInboundChannel.addInterceptor(webSocketTraceChannelInterceptor());
		}

		if (enableTraceOutboundChannel) {
			clientOutboundChannel.addInterceptor(webSocketTraceChannelInterceptor());
		}
	}
}