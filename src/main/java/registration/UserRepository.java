package registration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Crudable<User> {

    /**
     * Creates database connection constants and using DriverManager and method getConnection
     * connects to the clarified in constants database address with password and username.
     * SQLException is caught in case of database connection is failed.
     *
     * @return connection instance of class Connection
     */
    public static Connection getConnection() {
/*
        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String password = "Postgresql";
*/
        Connection connection = null;
        String url = "jdbc:postgresql://ec2-54-170-90-26.eu-west-1.compute.amazonaws.com:5432/d9sd8hq32ar6pt?sslmode=require";
        String user = "mmsapcfmzzfllt";
        String password = "60fd7eee3c4263a4602eca075bfb3cf70deec3bad10cfce1daf28c2944b713ff";


        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connection to the server PostgreSQL is successful.");
            } else {
                System.out.println("Connection failed.");
            }
        } catch (SQLException sqlException) {
            System.out.println("SQL exception please read the error message: ");
            sqlException.printStackTrace();
        }
        return connection;
    }

    /**
     * <p>
     *     Creates 'connection' object as an instance of Connection class using getConnection() method.
     *     Creates 'ps' object as an instance of PreparedStatement class. 'ps' is set as a result of
     *     prepareStatement method of 'connection' object, with SQL 'insert into' request as a parameter.
     * </p>
     * <p>
     *     Sets parameter for SQL request using set methods and values from fields of received in @param user object,
     *     instance of User entity class. Process SQL request using executeUpdate method of 'ps' object instance of
     *     PreparedStatement class and sets returned int value as a 'connectionStatus' variable.
     *     Connection is closed here.
     * </p>
     * <p>
     *     Catches SQLException and prints it.
     * </p>
     *
     * @param user Object that should be saved as a database record
     * @return 'connectionStatus' integer variable.
     */
    @Override
    public int save(User user) {
        int connectionStatus = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into users(name, surname, email, country, password) values (?,?,?,?,?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());

            connectionStatus = ps.executeUpdate();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return connectionStatus;
    }

    /**
     * <p>
     *     Creates 'connection' object as an instance of Connection class using getConnection() method.
     *     Creates 'ps' object as an instance of PreparedStatement class. 'ps' is set as a result of
     *     prepareStatement method of 'connection' object, with SQL 'update' request as a parameter.
     * </p>
     * <p>
     *     Sets parameter for SQL request using set methods and values from fields of received in @param user object,
     *     instance of User entity class. Process SQL request using executeUpdate method of 'ps' object instance of
     *     PreparedStatement class and sets returned int value as a 'connectionStatus' variable.
     *     Connection is closed here.
     * </p>
     * <p>
     *     Catches SQLException and prints it.
     * </p>
     * @param user Object that should be updated
     * @return 'connectionStatus' integer variable.
     */
    @Override
    public int update(User user) {

        int connectionStatus = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("update users set name=?,surname=?,email=?,country=?,password=? where id=?");
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getCountry());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getId());

            connectionStatus = ps.executeUpdate();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return connectionStatus;
    }

    /**
     * <p>
     *     Inside try-catch construction creates 'connection' object as an instance of Connection class,
     *     using method getConnection().
     *     Creates PreparedStatement object with 'delete' SQL request, using prepareStatement() method of
     *     Connection class with 'connection' object.
     *     Sets received parameter 'id' as 'id' parameter for SQL request.
     *     Executes SQL request with executeUpdate method of 'ps' object instance of PreparedStatement class.
     *     Closes connection here.
     * </p>
     * <p>
     *     Catches SQLException and prints it.
     * </p>
     *
     * @param id column 'id' value as a field 'id' of object that should be deleted from database table
     * @return 'connectionStatus' integer variable.
     */
    @Override
    public int delete(int id) {
        int connectionStatus = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from users where id=?");
            ps.setInt(1, id);
            connectionStatus = ps.executeUpdate();

            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return connectionStatus;
    }

    /**
     * <p>
     *     Creates 'user' object instance of User class.
     *     Inside try block creates 'connection' object instance of Connection class using
     *     getConnection() method.
     *     Creates 'ps' object instance of PreparedStatement class. 'ps' is set as a result of method
     *     prepareStatement of connection object with SQL 'select' request as a parameter 'sql'.
     *     Received in 'id' parameter is set as 'id' parameter of SQL request.
     *     The request is processing using executeQuery method of PreparedStatement 'ps' object. The return of
     *     executeQuery method is set as 'rs' object instance of ResultSet class.
     * </p>
     * <p>
     *     Call method createObjectByValue() with 'rs' and 'user' objects in parameters to fill 'user' object with
     *     values from 'rs'.
     *     Closes connection here.
     * </p>
     * <p>
     *     Catches SQLException and prints it.
     * </p>
     *
     * @param id column 'id' value as a field 'id' of object that should be found in database table
     * @return created 'user' object instance of User class
     */
    @Override
    public User getById(int id) {

        User user = new User();

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from users where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            user = createObjectByValue(rs, user);
            
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return user;
    }

    /**
     * Finds in database table a record with 'email' column value equals to 'email' String value received in parameter.
     * Returns object of User class with fields set as values of cells in found table record.
     * <p>
     *     Creates 'user' object instance of User class.
     *     Inside try block creates 'connection' object instance of Connection class using
     *     getConnection() method.
     *     Creates 'ps' object instance of PreparedStatement class. 'ps' is set as a result of method
     *     prepareStatement of connection object with SQL 'select' request as a parameter 'sql'.
     *     Received in 'email' String parameter is set as 'email' parameter of SQL request.
     *     The request is processing using executeQuery method of PreparedStatement 'ps' object. The return of
     *     executeQuery method is set as 'rs' object instance of ResultSet class.
     * </p>
     * <p>
     *     Call method createObjectByValue() with 'rs' and 'user' objects in parameters to fill 'user' object with
     *     values from 'rs'.
     *     Closes connection here.
     * </p>
     * <p>
     *     Catches SQLException and prints it.
     * </p>
     *
     * @param email column 'email' value as a field 'email' of object that should be found in database table
     * @return created 'user' object instance of User class
     */
    public User getByEmail(String email) {

        User user = new User();
        Connection connection = getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from users where email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            user = createObjectByValue(rs, user);

            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return user;
    }

    /**
     * Using next() method checks if there are any record in 'rs' parameter instance of ResultSet.
     * In case of true, sets fields of received in parameter 'user' object instance of User class as
     * column items from 'rs' object.
     *
     * @param rs ResultSet object from database that includes one row of database table
     * @param user Object that should be filled out with data
     * @return User object with set fields
     * @throws SQLException can be thrown in case of none data in the ResultSet object or absence
     * of ResultSet object.
     */
    @Override
    public User createObjectByValue(ResultSet rs, User user) throws SQLException {
        if (rs.next()) {
            user.setId(rs.getInt(1));
            user.setName(rs.getString(2));
            user.setSurname(rs.getString(3));
            user.setCountry(rs.getString(5));
            user.setEmail(rs.getString(4));
            user.setPassword(rs.getString(6));
        }
        return user;
    }

    /**
     * Creates List collection 'usersList'.
     * Inside try block creates Connection object 'connection' using getConnection() method.
     * Creates PreparedStatement object 'ps' using prepareStatement method of 'connection' object with
     * SQL request 'select' as a parameter.
     * Using executeQuery method of PrepareStatement class creates 'rs' object instance of ResultSet class.
     * Using while loop and next() method of 'rs' object adds new created User objects with data from 'rs' columns
     * in fields into created 'usersList' List collection.
     * Closes connection here.
     * Catches SQLException and prints it.
     *
     * @return created List of all User objects created with data received from database table
     */
    @Override
    public List<User> getAll() {

        List<User> usersList = new ArrayList<>();

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) usersList.add( new User(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(4),
                                rs.getString(3),
                                rs.getString(5),
                                rs.getString(6)));

            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return usersList;
    }



}
