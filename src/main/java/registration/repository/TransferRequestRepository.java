package registration.repository;

import lombok.Cleanup;
import registration.servlets.Crudable;
import registration.Interceptors.Logged;
import registration.Loggable;
import registration.entity.TransferRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public List<TransferRequest> getAll() {
        return null;
    }

    @Override
    public TransferRequest createObjectByValue(ResultSet R) throws SQLException {
        return null;
    }
}
