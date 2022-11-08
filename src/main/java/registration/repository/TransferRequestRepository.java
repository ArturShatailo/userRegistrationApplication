package registration.repository;

import lombok.Cleanup;
import registration.entity.User;
import registration.servlets.Crudable;
import registration.Interceptors.Logged;
import registration.Loggable;
import registration.entity.TransferRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransferRequestRepository  implements Crudable<TransferRequest>, Loggable, Connected {

    @Logged
    @Override
    public int save(TransferRequest transferRequest) {

        //Loggable interface method
        toLogStartOfMethod("save()", this.getClass().getName());

        int connectionStatus = 0;

        try {
            String sql = "insert into transfer_requests(from_wallet, email_from, to_wallet, email_to, date, status, amount, currency) values (?,?,?,?,?,?,?,?)";

            //Loggable interface method
            toLogStartSqlRequest("save()", sql);

            @Cleanup Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, transferRequest.getFrom());
            ps.setString(2, transferRequest.getFromEmail());
            ps.setString(3, transferRequest.getTo());
            ps.setString(4, transferRequest.getToEmail());
            ps.setLong(5, transferRequest.getDate());
            ps.setString(6, transferRequest.getStatus());
            ps.setString(7, transferRequest.getAmount());
            ps.setString(8, transferRequest.getCurrency());

            //Loggable interface method
            toLogConnectionStatus("save()", connectionStatus);

            connectionStatus = ps.executeUpdate();
            //connection.close();

        } catch (SQLException sqlException) {
            toLogSqlException("save", sqlException);
            //sqlException.printStackTrace();
        }

        return connectionStatus;
    }

    @Override
    public int update(TransferRequest transferRequest) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public TransferRequest getById(int id) {
        return null;
    }

    /**
     * Creates List collection 'transfersList'.
     * Inside try block creates Connection object 'connection' using getConnection() method.
     * Creates PreparedStatement object 'ps' using prepareStatement method of 'connection' object with
     * SQL request 'select' as a parameter.
     * Using executeQuery method of PrepareStatement class creates 'rs' object instance of ResultSet class.
     * Using while loop and next() method of 'rs' object adds new created User objects with data from 'rs' columns
     * in fields into created 'transfersList' List collection.
     * Closes connection here.
     * Catches SQLException and prints it.
     *
     * @return created List of all TransferRequest objects created with data received from database table
     */
    @Logged
    @Override
    public List<TransferRequest> getAll() {

        //Loggable interface method
        toLogStartOfMethod("getAll()", this.getClass().getName());

        List<TransferRequest> transfersList = new ArrayList<>();

        try {
            @Cleanup Connection connection = getConnection();
            String sql = "select * from transfer_requests";

            //Loggable class method
            toLogStartSqlRequest("getAll()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) transfersList.add( new TransferRequest(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(8),
                    rs.getString(7),
                    rs.getString(9),
                    rs.getLong(5),
                    rs.getString(6)));

            //connection.close();

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("getAll()", sqlException);
            //sqlException.printStackTrace();
        }

        return transfersList;
    }

    /**
     * Creates List collection 'transfersList' that has received in @param 'value' value in any of columns.
     * Inside try block creates Connection object 'connection' using getConnection() method.
     * Creates PreparedStatement object 'ps' using prepareStatement method of 'connection' object with
     * SQL request 'select' as a parameter.
     * Using executeQuery method of PrepareStatement class creates 'rs' object instance of ResultSet class.
     * Using while loop and next() method of 'rs' object adds new created User objects with data from 'rs' columns
     * in fields into created 'transfersList' List collection.
     * Closes connection here.
     * Catches SQLException and prints it.
     *
     * @return created List of all TransferRequest objects created with data received from database table
     */
    @Logged
    public List<TransferRequest> getAllByValue(String value) {

        //Loggable interface method
        toLogStartOfMethod("getAllByValue()", this.getClass().getName());

        List<TransferRequest> transfersList = new ArrayList<>();

        try {
            @Cleanup Connection connection = getConnection();
            String sql = "select * from transfer_requests where " +
                    "from_wallet=? OR email_from=? OR to_wallet=? OR status=? OR amount=? " +
                    "OR email_to=? OR currency=?";

            //Loggable class method
            toLogStartSqlRequest("getAllByValue()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);
            ps.setString(4, value);
            ps.setString(5, value);
            ps.setString(6, value);
            ps.setString(7, value);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) transfersList.add( new TransferRequest(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(8),
                    rs.getString(7),
                    rs.getString(9),
                    rs.getLong(5),
                    rs.getString(6)));

            //connection.close();

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("getAllByValue()", sqlException);
            //sqlException.printStackTrace();
        }

        return transfersList;
    }

    @Override
    public TransferRequest createObjectByValue(ResultSet R) throws SQLException {
        return null;
    }
}
