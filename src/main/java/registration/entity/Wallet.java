package registration.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Wallet {

    private int id;

    private String wallet_number;

    private String owner;

    private String currency;

    private Double balance;

    public Wallet(String wallet_number, String owner, String currency, Double balance) {
        this.wallet_number = wallet_number;
        this.owner = owner;
        this.currency = currency;
        this.balance = balance;
    }
}


