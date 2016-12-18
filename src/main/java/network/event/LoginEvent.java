package network.event;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginEvent {

	private String username;
	private Date time;

	public LoginEvent(String username) {
		this.username = username;
		time = new Date();
	}
}
