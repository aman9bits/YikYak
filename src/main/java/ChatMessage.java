import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMessage {
	@JsonProperty
	private String sender;
	
	@JsonProperty
	private String receiver;
	
	@JsonProperty
	private long timestamp;
	
	@JsonProperty
	private String message;
	
	public ChatMessage(){}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
