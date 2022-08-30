package registration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferRequest {

    private int id;

    private String from;

    private String fromEmail;

    private String to;

    private String amount;

    private Long date;

    private String status;

    public TransferRequest(String from, String fromEmail, String to, String amount, Long date, String status) {
        this.from = from;
        this.fromEmail = fromEmail;
        this.to = to;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }
}
