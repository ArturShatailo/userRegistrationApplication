package registration.repository;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import registration.servlets.Crudable;
import registration.Interceptors.Logged;
import registration.Loggable;
import registration.statics.CurrencyExchange;
import registration.statics.Numbers;
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
public class WalletRepository implements Crudable<Wallet>, Loggable, Connected, Numbers, CurrencyExchange {


    public int createWalletsForUser(User user) {

        boolean statusUSD = saveWallet(new Wallet(generateNumber(), user.getEmail(), "USD", 0.0));
        boolean statusEUR = saveWallet(new Wallet(generateNumber(), user.getEmail(), "EUR", 0.0));

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
            String sql = "insert into wallets(wallet_number, owner, currency, balance) values (?,?,?,?)";

            //Loggable interface method
            toLogStartSqlRequest("saveWallet()", sql);

            @Cleanup Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, wallet.getWallet_number());
            ps.setString(2, wallet.getOwner());
            ps.setString(3, wallet.getCurrency());
            ps.setDouble(4, wallet.getBalance());

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

    @Logged
    public Wallet getWallet(String email, String currency) {

        //Loggable interface method
        toLogStartOfMethod("getWallet()", this.getClass().getName());

        Wallet wallet = null;

        try {
            @Cleanup Connection connection = getConnection();

            String sql = "select * from wallets where owner=? AND currency=?";

            //Loggable class method
            toLogStartSqlRequest("getWallet()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, currency);
            ResultSet rs = ps.executeQuery();

            wallet = createObjectByValue(rs);

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("getWallet()", sqlException);
            //sqlException.printStackTrace();
        }

        return wallet;
    }

    @Override
    public Wallet createObjectByValue(ResultSet R) throws SQLException {
        if (R.next()) { return new Wallet(
                R.getInt(1),
                R.getString(2),
                R.getString(3),
                R.getString(4),
                R.getDouble(5));
        }
        return null;
    }
    @Logged
    public int transferFunds(Wallet fromWallet, Wallet toWallet, String amount) {

        //Loggable interface method
        toLogStartOfMethod("transferFunds()", this.getClass().getName());

        double amountValue = Double.parseDouble(amount);
        double argument = defineArgument(fromWallet.getCurrency(), toWallet.getCurrency());
        double balanceFrom = fromWallet.getBalance();
        double balanceTo = fromWallet.getBalance();
        double newBalanceFrom = balanceFrom - amountValue;
        double newBalanceTo = balanceTo + (amountValue * argument);

        int transaction = makeTransaction(newBalanceFrom, newBalanceTo,
                fromWallet.getWallet_number(), toWallet.getWallet_number());

        return transaction > 0
                ? 1
                : makeTransaction(balanceFrom, balanceTo,
                fromWallet.getWallet_number(), toWallet.getWallet_number());
    }

    @Logged
    private int makeTransaction(double newBalanceFrom, double newBalanceTo, String wallet_number_from, String wallet_number_to) {
        toLogStartOfMethod("makeTransaction()", this.getClass().getName());

        int connectionStatus = 0;

        try {
            @Cleanup Connection connection = getConnection();
            //String sql = "delete from users where id=?";
            String sql = "update wallets set balance=? where wallet_number=?";
            //Loggable class method
            toLogStartSqlRequest("makeFromTransaction()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, newBalanceFrom);
            ps.setString(2, wallet_number_from);

            PreparedStatement ps1 = connection.prepareStatement(sql);
            ps1.setDouble(1, newBalanceTo);
            ps1.setString(2, wallet_number_to);

            int connectionStatusFrom = ps.executeUpdate();
            int connectionStatusTo = ps1.executeUpdate();
            connectionStatus = connectionStatusFrom > 0 && connectionStatusTo > 0 ? 1 : 0;

            //Loggable class method
            toLogConnectionStatus("makeFromTransaction()", connectionStatus);

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("makeFromTransaction()", sqlException);
            //sqlException.printStackTrace();

        }
        return connectionStatus;

    }

    private double defineArgument(String currencyF, String currencyT) {

        if(currencyF.equals(currencyT)) return 1;
        if(currencyF.equals("USD")) return usd_eur;
        else return eur_usd;

    }

    public Wallet getByNumber(String number) {

        //Loggable interface method
        toLogStartOfMethod("getByNumber()", this.getClass().getName());

        Wallet wallet = null;

        try {
            @Cleanup Connection connection = getConnection();

            String sql = "select * from wallets where wallet_number=?";

            //Loggable class method
            toLogStartSqlRequest("getByNumber()", sql);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();

            wallet = createObjectByValue(rs);

        } catch (SQLException sqlException) {
            //Loggable class method
            toLogSqlException("getByNumber()", sqlException);
            //sqlException.printStackTrace();
        }

        return wallet;

    }


}
