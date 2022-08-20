package registration.session;

import lombok.extern.slf4j.Slf4j;
import registration.CookieFactory;
import registration.Interceptors.Logged;
import registration.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@Logged
@Slf4j
@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet implements CookieFactory {
    //private static final long serialVersionUID = 1L;

    /**
     * Invalidates session that is received from HTTPServletRequest object 'req' by getSession() method.
     * Sets success cookie and redirects to 'login.jsp' page.
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure or any input / output requests failure.
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        HttpSession session = req.getSession(false);

        if(session != null){

            User user = (User) session.getAttribute("user");
            log.info("User {} successfully logged out by servlet: {}", user.getEmail(),  this.getServletName());

            session.invalidate();
        } else {
            log.info("User is trying to logging out but session is null by servlet: {}",  this.getServletName());
        }

        setCookie(resp, "successfulMessage", "Logged out", 5);
        resp.sendRedirect("/login.jsp");
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }

}
