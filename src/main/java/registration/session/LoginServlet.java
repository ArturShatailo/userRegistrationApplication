package registration.session;

import registration.CookieFactory;
import registration.InstanceRepository;
import registration.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginUser")
public class LoginServlet extends HttpServlet implements InstanceRepository, CookieFactory {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/registration/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Requested parameters
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = ur.getByEmail(email);

        if(user.getEmail() != null) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30 * 60);
                Cookie userEmail = new Cookie("user", email);
                userEmail.setMaxAge(30 * 60);
                resp.addCookie(userEmail);

                setCookie(resp, "successfulMessage", "Nice to see you and welcome back, "+ user.getName(), 5);

                resp.sendRedirect("/registration/personal-area");
            } else {
                setCookie(resp, "errorMessage", "Either username or password is wrong", 5);
                resp.sendRedirect("/registration/login.jsp");
            }
        } else {
            setCookie(resp, "errorMessage", "This email is not registered", 5);
            resp.sendRedirect("/registration/login.jsp");
        }
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }
}
