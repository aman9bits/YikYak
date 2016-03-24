import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class FileDataManager extends AbstractDataManager{

	@Override
	public void signup(User user) throws SignupFailedException {
		// TODO Auto-generated method stub
		try {
       		File file = new File("usernameList.txt");
    		if(!file.exists()) {
    		    file.createNewFile();
    		}
			FileOutputStream fout = new FileOutputStream(file,true);
			fout.write((user+System.getProperty("line.separator")).getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SignupFailedException("IO Exception occurred.");
		} 
	}

	@Override
	public String getPassword(String username) throws UserNotPresentException, IOException {
		File file = new File("usernameList.txt");
		if(!file.exists()) {
		    throw new UserNotPresentException();
		}
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
		    String[] arr = line.split(" ");
		    if(arr[0].equals(username)){
		    	return arr[1];
		    }
		}
		throw new UserNotPresentException();
	}

	@Override
	public Set<String> getFriendList(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
