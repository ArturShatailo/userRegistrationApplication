package registration.funds;

import registration.CookieFactory;
import registration.InstanceRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/transferFunds")
public class TransferFundsServlet extends HttpServlet implements InstanceRepository, CookieFactory {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {

    }
}
