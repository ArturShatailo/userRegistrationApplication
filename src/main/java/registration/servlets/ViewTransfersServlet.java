package registration.servlets;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import registration.Interceptors.Logged;
import registration.entity.TransferRequest;
import registration.repository.InstanceRepository;
import registration.repository.TransferRequestRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Logged
@Slf4j
@WebServlet("/get-transfers")
public class ViewTransfersServlet extends HttpServlet implements InstanceRepository, CookieFactory {

    TransferRequestRepository trr = new TransferRequestRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<TransferRequest> ltr = trr.getAll();

        //if(user != null && lu != null){
        log.info("Try to get all transfers in servlet {}", this.getServletName());

        resp.setContentType("application/json");
        JSONObject obj = new JSONObject();

        DateFormat dataObj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
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
                uo.put("date", dataObj.format(new Date(tr.getDate())));
                uo.put("status", tr.getStatus());
                obj.put(String.valueOf(counter), uo);
                counter++;
            }

            @Cleanup PrintWriter out = resp.getWriter();
            out.print(obj);
            //out.close();

        //} else {
            //setCookie(resp, "errorMessage", "Sorry, unable to upload data", 5);
            //resp.sendRedirect("/registration/login.jsp");
        //}
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }


}
