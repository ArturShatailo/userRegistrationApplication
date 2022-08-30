package registration.funds;

import lombok.extern.slf4j.Slf4j;
import registration.CookieFactory;
import registration.InstanceRepository;
import registration.Interceptors.Logged;
import registration.entity.TransferRequest;
import registration.entity.User;
import registration.repository.TransferRequestRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Logged
@Slf4j
@WebServlet("/transferFunds")
public class TransferFundsServlet extends HttpServlet implements InstanceRepository, CookieFactory {

    TransferRequestRepository transferRequestRepository = new TransferRequestRepository();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        String amount = req.getParameter("amount");
        String from = req.getParameter("from");
        String to = req.getParameter("toWallet");
        User user = (User) req.getSession().getAttribute("user");
        String fromEmail = null;

        if (user != null) fromEmail = user.getEmail();

        //validation

        if(validation()) {

            TransferRequest transferRequest = new TransferRequest(from, fromEmail, to, amount, new Date().getTime(), "processed");
            int statusRequest = transferRequestRepository.save(transferRequest);

            if (statusRequest > 0) {
                int statusTransfer = ur.transferFunds(transferRequest);
                if (statusTransfer > 0) {
                    setCookie(resp, "successfulMessage", "Request processed successfully", 5);
                    getServletContext().getRequestDispatcher("/funds.jsp").forward(req, resp);
                } else {
                    setCookie(resp, "errorMessage", "Sorry, unable to process request", 5);
                    getServletContext().getRequestDispatcher("/funds.jsp").forward(req, resp);
                }

            } else {
                log.info("unable to create new request {}", transferRequest);
                setCookie(resp, "errorMessage", "Sorry, unable to create new request", 5);
                getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
            }

        }

    }

    private boolean validation() {


/* if email is null
        setCookie(resp, "errorMessage", "Your login session is over", 5);
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
*/
        return true;
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {

    }
}
