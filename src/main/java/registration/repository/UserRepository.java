package registration.repository;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import registration.Crudable;
import registration.Interceptors.Logged;
import registration.Loggable;
import registration.entity.TransferRequest;
import registration.entity.User;
import registration.entity.Wallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Logged
@Slf4j
public class UserRepository implements Crudable<User>, Loggable, Connected {

    WalletRepository walletRepository = new WalletRepository();

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

    @Logged
    @Override
    public int save(User user) {

        //Loggable interface method
        toLogStartOfMethod("save()", this.getClass().getName());

        int connectionStatus = 0;

        try {
            String sql = "insert into users(name, surname, email, country, password, role) values (?,?,?,?,?,?)";

            //Loggable interface method
            toLogStartSqlRequest("save()", sql);

            @Cleanup Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(4, user.getCountry());
            ps.setString(3, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getRole());

            //Loggable interface method
            toLogConnectionStatus("save()", connectionStatus);

            connectionStatus = ps.executeUpdate();

            //create wallets for each registered user
            if (connectionStatus > 0)
                connectionStatus = walletRepository.createWalletsForUser(user);

        } catch (SQLException sqlException) {
            toLogSqlException("save", sqlException);
            //sqlException.printStackTrace();
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
    @Logged
    @Override
    public int update(User user) {

        //Loggable interface method
        toLogStartOfMethod("update()", this.getClass().getName());

        int connectionStatus = 0;

        try {
            @Cleanup Connection connection = getConnection();
            String sql = "update users set name=?,surname=?,email=?,country=?,password=? where id=?";

            //Loggable interface method
            toLogStartSqlRequest("update()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getCountry());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getId());

            //Loggable interface method
            toLogConnectionStatus("update()", connectionStatus);

            connectionStatus = ps.executeUpdate();
            //connection.close();

        } catch (SQLException sqlException) {
            //Loggable interface method
            toLogSqlException("update()", sqlException);
            //sqlException.printStackTrace();
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
    @Logged
    @Override
    public int delete(int id) {

        //Loggable interface method
        toLogStartOfMethod("delete()", this.getClass().getName());

        int connectionStatus = 0;

        try {
            @Cleanup Connection connection = getConnection();
            String sql = "delete from users where id=?";

            //Loggable class method
            toLogStartSqlRequest("delete()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            connectionStatus = ps.executeUpdate();

            //Loggable class method
            toLogConnectionStatus("delete()", connectionStatus);

            connection.close();

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("delete()", sqlException);
            //sqlException.printStackTrace();
        }
        return connectionStatus;
    }

    /**
     * <p>
     *     Inside try-catch construction creates 'connection' object as an instance of Connection class,
     *     using method getConnection().
     *     Creates PreparedStatement object with 'update' SQL request, using prepareStatement() method of
     *     Connection class with 'connection' object.
     *     Sets received parameter 'id' as 'id' parameter for SQL request and isdeleted as true value;
     *     Executes SQL request with executeUpdate method of 'ps' object instance of PreparedStatement class.
     *     Closes connection here.
     * </p>
     * <p>
     *     Catches SQLException and prints it.
     * </p>
     *
     * @param id column 'id' value as a field 'id' of object that should be updated in database table
     * @return 'connectionStatus' integer variable.
     */
    @Logged
    public int deleteUserById(int id) {

        //Loggable interface method
        toLogStartOfMethod("deleteUserById()", this.getClass().getName());

        int connectionStatus = 0;

        try {
            @Cleanup Connection connection = getConnection();
            //String sql = "delete from users where id=?";
            String sql = "update users set isdeleted=? where id=?";
            //Loggable class method
            toLogStartSqlRequest("deleteUserById()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setInt(2, id);
            connectionStatus = ps.executeUpdate();

            //Loggable class method
            toLogConnectionStatus("deleteUserById()", connectionStatus);

            connection.close();

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("deleteUserById()", sqlException);
            //sqlException.printStackTrace();
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
     *     Received in 'id' parameter is set as 'id' parameter of SQL request. "user" value is set as
     *     role column and false value is set as isdeleted column in SQL request.
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
    @Logged
    @Override
    public User getById(int id) {

        //Loggable interface method
        toLogStartOfMethod("getById()", this.getClass().getName());

        User user = new User();

        try {
            @Cleanup Connection connection = getConnection();

            String sql = "select * from users where id=? AND role=? AND isdeleted=?";

            //Loggable class method
            toLogStartSqlRequest("getById()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, "user");
            ps.setBoolean(3, false);
            ResultSet rs = ps.executeQuery();

            user = createObjectByValue(rs, user);
            
            //connection.close();

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("getById()", sqlException);
            //sqlException.printStackTrace();
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
    @Logged
    public User getByEmail(String email) {

        //Loggable interface method
        toLogStartOfMethod("getByEmail()", this.getClass().getName());

        User user = new User();

        try {
            @Cleanup Connection connection = getConnection();
            String sql = "select * from users where email=? AND role=? AND isdeleted=?";

            //Loggable class method
            toLogStartSqlRequest("getByEmail()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, "user");
            ps.setBoolean(3, false);
            ResultSet rs = ps.executeQuery();

            user = createObjectByValue(rs, user);

            //connection.close();

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("getByEmail()", sqlException);
            //sqlException.printStackTrace();
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
            user.setRole(rs.getString(7));
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
    @Logged
    @Override
    public List<User> getAll() {

        //Loggable interface method
        toLogStartOfMethod("getAll()", this.getClass().getName());

        List<User> usersList = new ArrayList<>();

        try {
            @Cleanup Connection connection = getConnection();
            String sql = "select * from users where role=? AND isdeleted=?";

            //Loggable class method
            toLogStartSqlRequest("getAll()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "user");
            ps.setBoolean(2, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) usersList.add( new User(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(5),
                                rs.getString(4),
                                rs.getString(6),
                                rs.getString(7)));

            //connection.close();

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("getAll()", sqlException);
            //sqlException.printStackTrace();
        }

        return usersList;
    }


}
