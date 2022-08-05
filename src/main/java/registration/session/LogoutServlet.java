package registration.session;

import registration.CookieFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

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

        /*
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")){
                    cookie.setValue("");
                    break;
                }
            }
        }*/

        HttpSession session = req.getSession(false);
        if(session != null){
            session.invalidate();
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
