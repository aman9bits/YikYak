import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import io.dropwizard.auth.Auth;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.validation.Validated;

@Path("/")
public class ServerResource {

	@Inject
	AbstractDataManager manager;
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerResource.class);
	 
	@GET
	@Path("/test2")
	@Timed
	public Response Hello(){
		System.out.println("Hello");
		return Response.status(Response.Status.OK).entity("hello").build();
	}
	
	@POST
	@Path("/login")
	public Response login(String request){
		Authenticator<BasicCredentials,User> authenticator = new SimpleAuthenticator();
		try{
			JSONObject json = new JSONObject(request);
			BasicCredentials cred = new BasicCredentials(json.getString("username"),json.getString("password"));
			Optional<User> user = authenticator.authenticate(cred);
			if(user.isPresent()){
				return Response.status(Response.Status.ACCEPTED).entity("Successfully logged in!").build();
			}else{
				return Response.status(Response.Status.UNAUTHORIZED).entity("Username or password incorrect!").build();
			}
		}catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some problem occurred. Try Again").build();
		}catch(JSONException e){
			LOGGER.error(e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid input").build();
		}
		
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("hello")
	public String getGreeting(@Auth User user) { 
		return "Hello world!"; 
	}
	
	@GET
	@Path("/logout")
	public Response logout(){
		return Response.status(Response.Status.UNAUTHORIZED).entity("You are successfully logged out.").build();
	}
	
	@POST
	@Path("/signup")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }) 
	public Response signup(@Valid User user){
		try {
			manager.signup(user);
		} catch (SignupFailedException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Signup failed").build();
		}
		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_XML).entity("Signup successful").build();
	}
	
	@GET
	@Path("/getFriendList/{username}")
	public Response getFriendList(@PathParam(value = "username") String username){
		AbstractDataManager manager = new FileDataManager();
		Set<String> result;
		try {
			result = manager.getFriendList(username);
		} catch (RetryableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some error occurred. Please try later.").build();
		} catch (NonRetryableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.OK).entity("No friends found.").build();
		}
		return Response.status(Response.Status.OK).entity(result.toString()).build();
	}
	
	@GET
	@Path("/addFriend")
	public Response addFriend(@QueryParam("first") String first, @QueryParam("second") String second){
		AbstractDataManager manager = new FileDataManager();
       	try {
			manager.addFriend(first,second);
		} catch (RetryableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some error occurred. Please try later.").build();
		}
		return Response.status(Response.Status.OK).entity("Friend successfully added").build();
	}
	
	
	@POST
	@Path("/sendMessage")
	public Response sendMessage(@Valid ChatMessage message){
		AbstractDataManager manager = new FileDataManager();
		try {
			manager.sendMessage(message);
		} catch (RetryableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some error occurred. Please try later.").build();
		}
		return Response.status(Response.Status.OK).entity("Message successfully sent").build();
	}
	
	@GET
	@Path("/loadMessages")
	public Response loadMessages(@QueryParam(value = "first") String first, @QueryParam(value = "second") String second){
		List<MessageWithoutReceiver> messages;
		try {
			messages = manager.loadMessages(first,second);
		} catch (RetryableException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Some error occurred. Please try later.").build();
		}
		return Response.status(Response.Status.OK).entity(messages.toString()).build();
	}
}
