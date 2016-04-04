import java.util.List;
import java.util.Set;

public abstract class AbstractDataManager {
	public abstract void signup(User user) throws RetryableException;

	public abstract Set<String> getFriendList(String username) throws RetryableException, NonRetryableException;
	public abstract String getPassword(String username) throws NonRetryableException, RetryableException;

	public abstract void addFriend(String first, String second) throws RetryableException;

	public abstract void sendMessage(ChatMessage message) throws RetryableException;

	public abstract List<MessageWithoutReceiver> loadMessages(String first, String second) throws RetryableException;

	public abstract User getUser(String username);
}
