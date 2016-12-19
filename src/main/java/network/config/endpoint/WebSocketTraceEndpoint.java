package network.config.endpoint;

import java.util.List;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.trace.Trace;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "endpoints.websockettrace", ignoreUnknownFields = false)
public class WebSocketTraceEndpoint extends AbstractEndpoint<List<Trace>> {

	private final TraceRepository repository;

	public WebSocketTraceEndpoint(TraceRepository repository) {
		super("websockettrace");
		this.repository = repository;
	}

	@Override
	public List<Trace> invoke() {
		return this.repository.findAll();
	}
}