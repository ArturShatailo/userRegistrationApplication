package registration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Wallet {

    private int id;

    private String wallet_number;

    private String owner;

    private int owner_id;

    private String currency;

    private Double balance;

    public Wallet(String wallet_number, String owner, int owner_id, String currency, Double balance) {
        this.wallet_number = wallet_number;
        this.owner = owner;
        this.owner_id = owner_id;
        this.currency = currency;
        this.balance = balance;
    }
}


