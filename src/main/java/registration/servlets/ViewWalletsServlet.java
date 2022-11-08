package registration.servlets;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import registration.Interceptors.Logged;
import registration.entity.User;
import registration.entity.Wallet;
import registration.repository.InstanceRepository;
import registration.repository.WalletRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@Logged
@Slf4j
@WebServlet("/get-user-wallets")
public class ViewWalletsServlet extends HttpServlet implements InstanceRepository, CookieFactory{

    WalletRepository walletRepository = new WalletRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(user != null){

            log.info("Try to get user information as JSON: {} by servlet {}", user, this.getServletName());

            Wallet walletUSD = walletRepository.getWallet(user.getEmail(), "USD");
            Wallet walletEUR = walletRepository.getWallet(user.getEmail(), "EUR");

            log.info("Try to send wallets information as JSON: {} and {} by servlet {}", walletUSD, walletEUR, this.getServletName());

            resp.setContentType("application/json");
            JSONObject obj = new JSONObject();

            obj.put("usd-wallet-number", walletUSD.getWallet_number());
            obj.put("usd-wallet-balance", walletUSD.getBalance());
            obj.put("eur-wallet-number", walletEUR.getWallet_number());
            obj.put("eur-wallet-balance", walletEUR.getBalance());

            PrintWriter out = resp.getWriter();
            out.println(obj);
            out.close();

        } else {

            log.info("Session is empty and user information cannot be uploaded by servlet {}", this.getServletName());

            setCookie(resp, "errorMessage", "Sorry, unable to login", 5);
            resp.sendRedirect("/login.jsp");
        }
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }


}
