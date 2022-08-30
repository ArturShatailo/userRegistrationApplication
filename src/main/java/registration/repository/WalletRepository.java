package registration.repository;

import lombok.Cleanup;
import registration.Crudable;
import registration.InstanceRepository;
import registration.Loggable;
import registration.Numbers;
import registration.Technical.Tech;
import registration.entity.User;
import registration.entity.Wallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WalletRepository implements Crudable<Wallet>, Loggable, Connected, Numbers {


    public int createWalletsForUser(User user) {

        boolean statusUSD = saveWallet(new Wallet(generateNumber(), user.getEmail(), user.getId(), "USD"));
        boolean statusEUR = saveWallet(new Wallet(generateNumber(), user.getEmail(), user.getId(), "EUR"));

        return  statusUSD && statusEUR ? 1 : 0;
    }

    private String generateNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 10; i > 0; i--) {
            stringBuilder.append(Tech.getRandom(0,9));
        }
        String number = String.valueOf(stringBuilder);
        if (numbers.contains(number)) return generateNumber();
        else return number;
    }

    @Override
    public int save(Wallet wallet) {
        return 0;
    }

    public boolean saveWallet(Wallet wallet) {

        //Loggable interface method
        toLogStartOfMethod("saveWallet()", this.getClass().getName());

        int connectionStatus = 0;

        try {
            String sql = "insert into wallets(wallet_number, owner, owner_id, currency) values (?,?,?,?)";

            //Loggable interface method
            toLogStartSqlRequest("saveWallet()", sql);

            @Cleanup Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, wallet.getWallet_number());
            ps.setString(2, wallet.getOwner());
            ps.setInt(3, wallet.getOwner_id());
            ps.setString(4, wallet.getCurrency());

            //Loggable interface method
            toLogConnectionStatus("save()", connectionStatus);

            connectionStatus = ps.executeUpdate();
            //connection.close();

        } catch (SQLException sqlException) {
            toLogSqlException("save", sqlException);
            //sqlException.printStackTrace();
        }

        return connectionStatus > 0;
    }

    @Override
    public int update(Wallet wallet) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Wallet getById(int id) {
        return null;
    }

    @Override
    public List<Wallet> getAll() {
        return null;
    }

    @Override
    public Wallet createObjectByValue(ResultSet R, Wallet T) throws SQLException {
        return null;
    }
}
