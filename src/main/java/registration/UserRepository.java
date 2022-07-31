package registration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Crudable<User> {

    public static Connection getConnection() {

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/users";
        String user = "postgres";
        String password = "Postgresql";

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

    @Override
    public int update(User user) {

        int connectionStatus = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("update users set name=?,surname=?,email=?,country=?,password=? where id=?");
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getCountry());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getId());

            connectionStatus = ps.executeUpdate();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return connectionStatus;
    }

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

    @Override
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
