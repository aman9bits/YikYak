
public class RetryableException extends Exception {
	String message;
	
	public RetryableException(){}
	public RetryableException(String message){
		this.message = message;
	}

}
