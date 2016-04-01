
public abstract class Encrypter {
	public abstract boolean checkPassword(String candidate, String hashed);
	
	public abstract String hashPassword(String password);
}
