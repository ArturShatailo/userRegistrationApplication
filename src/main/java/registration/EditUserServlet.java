package registration;


import lombok.extern.slf4j.Slf4j;
import registration.Interceptors.Logged;
import registration.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@Logged
@Slf4j
@WebServlet("/editUserServlet")
public class EditUserServlet extends HttpServlet implements InstanceRepository, CookieFactory {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPut(req, resp);
    }

    /**
     * Receives data from HttpServletRequest by method getParameter() into String variables.
     * Gets User object from session.
     * Checks if 'user' object from session is not null.
     * In case of not null, sets new values of 'user' fields changing them to values collected from 'req'
     * into String variables.
     * In case of null, sets error cookies and redirects to 'login.jsp' page.
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure or any input / output requests failure.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int status = 0;

        //Receives requested data from HttpServletRequest object 'req' by getParameter() method calls
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String country = req.getParameter("country");

        resp.setContentType("text/html");
        HttpSession session = req.getSession();

        //Receives User object from session
        User user = (User) session.getAttribute("user");

        if (user != null) {
            log.info("Try to update user {} information by servlet {}",  user.getEmail(), this.getServletName());

            user.setName(name);
            user.setSurname(surname);
            user.setCountry(country);
            status = ur.update(user);

        } else {
            log.info("An attempt to edit information without logging in in servlet {}", this.getServletName());
            setCookie(resp, "errorMessage", "You are not logged in", 5);
            resp.sendRedirect("/login.jsp");
        }

        if(status > 0) {
            log.info("Successful information change by user {} in servlet {}", user.getEmail(), this.getServletName());
            setCookie(resp, "successfulMessage", "Information changed", 5);
            resp.sendRedirect("/personal-area.jsp");
        } else {
            log.info("Unable to update your information by null user in servlet {}", this.getServletName());
            setCookie(resp, "errorMessage", "Unable to update your information", 5);
            resp.sendRedirect("/personal-area.jsp");
        }

    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }
}
