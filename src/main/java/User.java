import java.security.Principal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements Principal{
	@JsonProperty
	@NotEmpty
	private String username;
	
	@NotEmpty
	@JsonProperty
	private String password;
	
	public User() {
		super();
	}
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return username;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return username + " "+password;
	}

}
