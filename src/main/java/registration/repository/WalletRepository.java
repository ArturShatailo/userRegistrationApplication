package registration.repository;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import registration.Crudable;
import registration.InstanceRepository;
import registration.Interceptors.Logged;
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

@Logged
@Slf4j
public class WalletRepository implements Crudable<Wallet>, Loggable, Connected, Numbers {


    public int createWalletsForUser(User user) {

        boolean statusUSD = saveWallet(new Wallet(generateNumber(), user.getEmail(), user.getId(), "USD", 0.0));
        boolean statusEUR = saveWallet(new Wallet(generateNumber(), user.getEmail(), user.getId(), "EUR", 0.0));

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
            String sql = "insert into wallets(wallet_number, owner, owner_id, currency, balance) values (?,?,?,?,?)";

            //Loggable interface method
            toLogStartSqlRequest("saveWallet()", sql);

            @Cleanup Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, wallet.getWallet_number());
            ps.setString(2, wallet.getOwner());
            ps.setInt(3, wallet.getOwner_id());
            ps.setString(4, wallet.getCurrency());
            ps.setDouble(5, wallet.getBalance());

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
        if (R.next()) {
            T.setId(R.getInt(1));
            T.setWallet_number(R.getString(2));
            T.setOwner(R.getString(3));
            T.setOwner_id(R.getInt(5));
            T.setCurrency(R.getString(4));
            T.setBalance(R.getDouble(5));
        }
        return T;
    }

    @Logged
    public Wallet getWallet(int id, String email, String currency) {

        //Loggable interface method
        toLogStartOfMethod("getWallet()", this.getClass().getName());

        Wallet wallet = new Wallet();

        try {
            @Cleanup Connection connection = getConnection();

            String sql = "select * from wallets where owner_id=? AND owner=? AND currency=?";

            //Loggable class method
            toLogStartSqlRequest("getWallet()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, email);
            ps.setString(3, currency);
            ResultSet rs = ps.executeQuery();

            wallet = createObjectByValue(rs, wallet);

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("getWallet()", sqlException);
            //sqlException.printStackTrace();
        }

        return wallet;
    }
}
