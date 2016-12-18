package network.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginEvent {

	private String username;

	public LoginEvent(String username) {
		this.username = username;
	}
}
