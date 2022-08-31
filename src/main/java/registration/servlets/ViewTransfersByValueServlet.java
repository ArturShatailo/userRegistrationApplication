package registration.servlets;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import registration.Interceptors.Logged;
import registration.entity.TransferRequest;
import registration.entity.User;
import registration.repository.InstanceRepository;
import registration.repository.TransferRequestRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Logged
@Slf4j
@WebServlet("/get-transfer-request-byValue")
public class ViewTransfersByValueServlet extends HttpServlet implements InstanceRepository, IdIterable, CookieFactory {

    TransferRequestRepository trr = new TransferRequestRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        @Cleanup PrintWriter out = resp.getWriter();

        try {
            //Calls method that returns "id" value from 'request' HttpServletRequest object
            String value = req.getParameter("value");

            log.info("Try to get Transfer request by value: {} in servlet {}", value, this.getServletName());

            List<TransferRequest> ltr = trr.getAllByValue(value);

            //Checks if any record found
            if (ltr != null && ltr.size() == 0) {
                log.error("There are no transfer requests uploaded {}", this.getServletName(), new NullPointerException());
                throw new NullPointerException();
            }

            log.info("Try to generate JSON object in servlet {}", this.getServletName());
            JSONObject obj = new JSONObject();
            int counter = 0;
            for(TransferRequest tr : ltr){
                JSONObject uo = new JSONObject();
                uo.put("id", tr.getId());
                uo.put("from-wallet", tr.getFrom());
                uo.put("email_from", tr.getFromEmail());
                uo.put("to_wallet", tr.getTo());
                uo.put("email_to", tr.getToEmail());
                uo.put("amount", tr.getAmount());
                uo.put("currency", tr.getCurrency());
                uo.put("date", tr.getDate());
                uo.put("status", tr.getStatus());
                obj.put(String.valueOf(counter), uo);
                counter++;
            }

            log.info("JSON sent, records found by value: {} in servlet {}", value, this.getServletName());
            setCookie(resp, "successfulMessage", "Records found successfully!", 5);
            out.print(obj);

        } catch (NullPointerException npe) {
            log.error("Can't find object in database in servlet {}", this.getServletName(), npe);
            setCookie(resp, "errorMessage", "Sorry, can't find data", 5);
        }
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }
}
