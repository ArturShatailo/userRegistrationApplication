package registration.repository;

import registration.Interceptors.Logged;
import registration.Loggable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface Connected extends Loggable {


    /**
     * Creates database connection constants and using DriverManager and method getConnection
     * connects to the clarified in constants database address with password and username.
     * SQLException is caught in case of database connection is failed.
     *
     * @return connection instance of class Connection
     */
    @Logged
    default Connection getConnection() {

        toLogStartOfMethod("getConnection()", this.getClass().getName());

        /*Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String password = "Postgresql";*/

        Connection connection = null;
        String url = "jdbc:postgresql://ec2-54-170-90-26.eu-west-1.compute.amazonaws.com:5432/d9sd8hq32ar6pt?sslmode=require";
        String user = "mmsapcfmzzfllt";
        String password = "60fd7eee3c4263a4602eca075bfb3cf70deec3bad10cfce1daf28c2944b713ff";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                log.info("Successful connection: connection = {}", connection);
                //System.out.println("Connection to the server PostgreSQL is successful.");
            } else {
                log.info("Connection failed: connection = {}", connection);
                //System.out.println("Connection failed.");
            }
        } catch (SQLException sqlException) {
            toLogSqlException("save", sqlException);
            //System.out.println("SQL exception please read the error message: ");
            //sqlException.printStackTrace();
        }
        return connection;
    }

}
