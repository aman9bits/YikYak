import java.io.IOException;
import java.util.List;
import java.util.Set;

public abstract class AbstractDataManager {
	public abstract void signup(User user) throws SignupFailedException;

	public abstract Set<String> getFriendList(String username) throws RetryableException, NonRetryableException;
	public abstract String getPassword(String username) throws UserNotPresentException, IOException;

	public abstract void addFriend(String first, String second) throws RetryableException;

	public abstract void sendMessage(ChatMessage message) throws RetryableException;

	public abstract List<MessageWithoutReceiver> loadMessages(String first, String second) throws RetryableException;

	public abstract User getUser(String username);
}
