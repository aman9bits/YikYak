
public class NonRetryableException extends Exception {
String message;
	
	public NonRetryableException(){}
	public NonRetryableException(String message){
		this.message = message;
	}
}
