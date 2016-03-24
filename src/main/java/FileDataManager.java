import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
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
			fout.close();
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
	public Set<String> getFriendList(String username) throws RetryableException, NonRetryableException {
		File file = new File("friendList.txt");
		Set<String> friends = new HashSet<>();
		if(!file.exists()) {
			throw new NonRetryableException();
		}
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
			    String[] arr = line.split(" ");
			    if(arr[0].equals(username)){
			    	for(int i=1;i<arr.length;i++){
			    		friends.add(arr[i]);
			    	}
			    }
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RetryableException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RetryableException();
		}
		
		return friends;
	}

	@Override
	public void addFriend(String first, String second) throws RetryableException {
		File file = new File("friendList.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RetryableException();
			}
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
			    String[] arr = line.split(" ");
			    if(arr[0].equals(first)){
			    	for(int i=1;i<arr.length;i++){
			    		if(arr[i].equals(second)){
			    			return;
			    		}
			    	}
			    	
			    	return;
			    }
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RetryableException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RetryableException();
		}
		
	}

	@Override
	public void sendMessage(ChatMessage message) throws RetryableException {
		// TODO Auto-generated method stub
		String identifier = message.getSender()+"#"+message.getReceiver();
		File file = new File(identifier+".txt");
		try{
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fout = new FileOutputStream(file,true);
			fout.write((message.getTimestamp()+ " "+ message.getMessage()+System.getProperty("line.separator")).getBytes());
			fout.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RetryableException();
		}
	}

}
