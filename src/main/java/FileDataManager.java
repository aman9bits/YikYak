import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileDataManager extends AbstractDataManager{

	public FileDataManager(){}
	@Override
	public void signup(User user) throws RetryableException {
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
			throw new RetryableException("IO Exception occurred.");
		} 
	}

	@Override
	public String getPassword(String username) throws NonRetryableException, RetryableException{
		File file = new File("usernameList.txt");
		if(!file.exists()) {
		    throw new NonRetryableException("File not present");
		}
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
			    String[] arr = line.split(" ");
			    if(arr[0].equals(username)){
			    	return arr[1];
			    }
			}
			throw new NonRetryableException("Username not present");
		}catch(IOException e){
			throw new RetryableException("IO Exception occured");
		}
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
		String filename;
		if(message.getSender().compareTo(message.getReceiver())>0){
			filename = message.getSender()+"#"+message.getReceiver();
		}else{
			filename = message.getReceiver()+"#"+message.getSender();
		}
		File file = new File(filename+".txt");
		try{
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fout = new FileOutputStream(file,true);
			fout.write((message.getTimestamp()+ " "+message.getSender()+" " + message.getMessage()+System.getProperty("line.separator")).getBytes());
			fout.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RetryableException();
		}
	}

	@Override
	public List<MessageWithoutReceiver> loadMessages(String first, String second) throws RetryableException {
		// TODO Auto-generated method stub
		List<MessageWithoutReceiver> messages = new ArrayList<>();
		String filename;
		if(first.compareTo(second)>0){
			filename = first+"#"+second;
		}else{
			filename = second+"#"+first;
		}
		File file = new File(filename+".txt");
		try{
			if(!file.exists()) {
				return messages;
			}
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
			    String[] arr = line.split(" ");
			    StringBuilder message = new StringBuilder(arr[2]);
			    for(int i=3;i<arr.length;i++){
			    	message.append(" ").append(arr[i]);
			    }
			    MessageWithoutReceiver mwr = new MessageWithoutReceiver(Long.parseLong(arr[0]),arr[1],message.toString());
			    System.out.println(mwr.toString());
			    messages.add(mwr);
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RetryableException();
		}
		return messages;
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
