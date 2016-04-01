import java.security.Principal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.auth.basic.BasicCredentials;

public class User implements Principal{
	@JsonProperty
	BasicCredentials credentials;
	
	@JsonProperty
	String name;
	
	@JsonProperty
	String phoneNumber;
	
	@JsonProperty
	String email;
	
	public User() {
		super();
	}

	public String getName() {
		return name;
	}

	
	@Override
	public String toString() {
		return "To be changed";
	}

	public static User build(JSONObject json) {
		User user = new User();
		user.credentials = new BasicCredentials(json.getString("username"),json.getString("hashedPassword"));
		user.name = json.getString("fname") + " " + json.getString("lname");
		user.phoneNumber = json.getString("phoneNumber");
		user.email = json.getString("email");
		return user;
	}

}
