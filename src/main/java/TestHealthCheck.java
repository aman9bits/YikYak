import com.codahale.metrics.health.HealthCheck;

public class TestHealthCheck extends HealthCheck {
    
    @Override
    protected Result check() throws Exception {
        if (false) {
            return Result.unhealthy("template doesn't include a name");
        }
        return Result.healthy();
    }
}