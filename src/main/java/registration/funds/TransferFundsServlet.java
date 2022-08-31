package registration.funds;

import lombok.extern.slf4j.Slf4j;
import registration.servlets.CookieFactory;
import registration.repository.InstanceRepository;
import registration.Interceptors.Logged;
import registration.servlets.Validable;
import registration.entity.TransferRequest;
import registration.entity.User;
import registration.entity.Wallet;
import registration.repository.TransferRequestRepository;
import registration.repository.WalletRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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

    WalletRepository walletRepository = new WalletRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        String amount = req.getParameter("amount");
        String from = req.getParameter("from");
        String to = req.getParameter("toWallet");
        User user = (User) req.getSession().getAttribute("user");

        Wallet toWallet = getWalletOfUser(to, resp, req);
        Wallet fromWallet = getWalletOfUser(from, resp, req);


        Validable v = () -> {

            double amountValue;

            try {
                amountValue = Double.parseDouble(amount);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                setCookie(resp, "errorMessage", "Enter only numbers in amount field please", 5);
                return false;
            }

            //Checks if any or all fields is/are empty
            if(from.equals("") || to.equals("") || amount.equals("")){
                setCookie(resp, "errorMessage", "Empty fields are not allowed", 5);
                return false;
            }

            if(amountValue <= 0.0){
                setCookie(resp, "errorMessage", "Negative or zero amount", 5);
                return false;
            }

            if(fromWallet != null && toWallet != null){
                log.info("unable to create new request {}", fromWallet);
                if(!fromWallet.getOwner().equals(user.getEmail())){
                    setCookie(resp, "errorMessage", "Wrong wallet", 5);
                    return false;
                }
            } else {
                setCookie(resp, "errorMessage", "Wrong wallet", 5);
                return false;
            }

            if(fromWallet.getBalance() < amountValue) {
                setCookie(resp, "errorMessage", "Not enough money", 5);
                return false;
            }

            return true;
        };


        if(v.formValidation()) {

            TransferRequest transferRequest = new TransferRequest(from, fromWallet.getOwner(), to, amount, new Date().getTime(), "processed");
            int statusRequest = transferRequestRepository.save(transferRequest);

            if (statusRequest > 0) {
                int statusTransfer = walletRepository.transferFunds(fromWallet, toWallet, amount);
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
        } else {
            log.error("Validation of transfer form is failed in servlet {}", this.getServletName());
            getServletContext().getRequestDispatcher("/funds.jsp").forward(req, resp);
        }
    }

    private Wallet getWalletOfUser(String walletNumber, HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException {

        Wallet wallet = null;

        try {
            wallet = walletRepository.getByNumber(walletNumber);
        } catch (Exception e) {
            log.info("unable to find wallet {}", walletNumber);
            setCookie(resp, "errorMessage", "Unable to find wallet", 5);
            getServletContext().getRequestDispatcher("/funds.jsp").forward(req, resp);
        }

        return wallet;
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }

}
