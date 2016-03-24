import java.io.IOException;
import java.util.Set;

public abstract class AbstractDataManager {
	public abstract void signup(User user) throws SignupFailedException;

	public abstract Set<String> getFriendList(String username);
	public abstract String getPassword(String username) throws UserNotPresentException, IOException;
}
