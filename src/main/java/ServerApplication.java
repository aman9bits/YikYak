import io.dropwizard.auth.Authenticator;
import javax.servlet.ServletRegistration;

import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

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
    	//final ServerResource resource = new ServerResource();
    	//environment.jersey().register(resource);
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
    	
    	Injector injector = Guice.createInjector(new AbstractModule() {
    	      @Override
			protected void configure() {
    	    	  bind(AbstractDataManager.class).to(FileDataManager.class);
    	    	  bind(new TypeLiteral<Authenticator<BasicCredentials,User>>(){}).toInstance(new SimpleAuthenticator());
    	    	  bind(Authenticator.class).to(SimpleAuthenticator.class);
    	    	  bind(Encrypter.class).to(BCryptEncrypter.class);
    	    	  bind(ServerResource.class);
    	    	  
    	      }
    	    });
    	environment.jersey().register(injector.getInstance(ServerResource.class));
    	AtmosphereServlet servlet = new AtmosphereServlet();
    	servlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "Yak-India.Yik-Yak.ServerResource.websocket");
    	servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/json");
    	servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");
    	 
    	ServletRegistration.Dynamic servletHolder = environment.servlets().addServlet("Chat", servlet);
    	servletHolder.addMapping("/chat/*");
    }
}
