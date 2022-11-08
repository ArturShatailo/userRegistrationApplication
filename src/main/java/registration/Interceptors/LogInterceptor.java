package registration.Interceptors;

import org.apache.log4j.PropertyConfigurator;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Logged
@Interceptor
public class LogInterceptor implements Serializable {

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {

        PropertyConfigurator.configure("/log4j.properties");

        Logger.getAnonymousLogger().log(Level.INFO, "{0} called with {1} parameters",
                new Object[]{
                        invocationContext.getMethod().getName(),
                        Arrays.deepToString(invocationContext.getParameters())
                }
        );
        return invocationContext.proceed();
    }
}
