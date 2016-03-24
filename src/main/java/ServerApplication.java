import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.*;
public class ServerApplication extends Application<ServerConfiguration> {

	public static void main(String[] args) throws Exception {
        new ServerApplication().run(args);
    }

	@Override
    public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(ServerConfiguration configuration, Environment environment) {
    	final ServerResource resource = new ServerResource();
    	environment.jersey().register(resource);
    	environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                    .setAuthenticator(new SimpleAuthenticator())
                    .setAuthorizer(new UserAuthorizer())
                    .setRealm("SUPER SECRET STUFF")
                    .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    	final TestHealthCheck healthCheck = new TestHealthCheck();
    	    environment.healthChecks().register("test", healthCheck);
    }
}
