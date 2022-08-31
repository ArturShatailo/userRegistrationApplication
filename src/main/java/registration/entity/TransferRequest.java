package registration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferRequest {

    private int id;

    private String from;

    private String fromEmail;

    private String to;

    private String toEmail;

    private String amount;

    private String currency;

    private Long date;

    private String status;

    public TransferRequest(String from, String fromEmail, String to, String toEmail, String amount, String currency, Long date, String status) {
        this.from = from;
        this.fromEmail = fromEmail;
        this.to = to;
        this.toEmail = toEmail;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.status = status;
    }
}
