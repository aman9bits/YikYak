import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {
    @Inject
	private AbstractDataManager dataManager;
	
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        String hashedPassword;
		try {
			hashedPassword = dataManager.getPassword(credentials.getUsername());
			if (BCrypt.checkpw(credentials.getPassword(), hashedPassword)) {
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