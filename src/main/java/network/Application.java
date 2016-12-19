package network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import network.config.WebSocketTraceChannelInterceptorAutoConfiguration;

@SpringBootApplication
@Import(WebSocketTraceChannelInterceptorAutoConfiguration.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
