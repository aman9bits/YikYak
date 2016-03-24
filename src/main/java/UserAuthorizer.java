import io.dropwizard.auth.Authorizer;

public class UserAuthorizer implements Authorizer<User> {
    public boolean authorize(User user, String role) {
        return user.getName().equals("good-guy") && role.equals("ADMIN");
    }
}