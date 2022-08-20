package registration;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registration.Interceptors.Logged;

import java.sql.SQLException;

@Logged
public interface Loggable {

    Logger log = LoggerFactory.getLogger(Loggable.class);

    @Logged
    default void toLogStartOfMethod(String method, String className) {
        log.info("{} method started in class {}", method, className);
    }

    @Logged
    default void toLogSqlException(String method, SQLException sqlException) {
        log.error("SQL exception in {} please read the error message: ", method, sqlException);
    }

    @Logged
    default void toLogStartSqlRequest(String method, String sql) {
        log.info("{} method is trying to send SQL request: {}", method, sql);
    }

    @Logged
    default void toLogConnectionStatus(String method, int connectionStatus) {
        log.info("Connection status: {}", connectionStatus);
    }

}
