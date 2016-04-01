import org.mindrot.jbcrypt.BCrypt;

public class BCryptEncrypter extends Encrypter{

	@Override
	public boolean checkPassword(String candidate, String hashed) {
		return BCrypt.checkpw(candidate, hashed);
	}

	@Override
	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

}
