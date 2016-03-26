import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageWithoutReceiver{
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@JsonProperty
	String message;
	
	@JsonProperty
	long timestamp;
	
	@JsonProperty
	String sender;

	public MessageWithoutReceiver(long timestamp, String sender, String message) {
		super();
		this.message = message;
		this.timestamp = timestamp;
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String toString(){
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{'timestamp':"+this.timestamp+",'sender':"+this.sender+",message':"+this.message+"}";
		}
	}
}
