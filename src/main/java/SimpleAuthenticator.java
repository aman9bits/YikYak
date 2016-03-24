import java.io.IOException;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {
    private AbstractDataManager dataManager = new FileDataManager();
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        String password;
		try {
			password = dataManager.getPassword(credentials.getUsername());
			if (password.equals(credentials.getPassword())) {
	            return Optional.of(new User(credentials.getUsername(),credentials.getPassword()));
	        }else{
	        	return Optional.absent();
	        }
		} catch (UserNotPresentException e) {
			e.printStackTrace();
			return Optional.absent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Optional.absent();
		}
		
        
    }
}