package registration.session;

import registration.CookieFactory;
import registration.InstanceRepository;
import registration.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/loginAdmin")
public class LoginAdminServlet extends HttpServlet implements InstanceRepository, CookieFactory {

    //private static final long serialVersionUID = 1L;

    /**
     * Sends redirect to 'admin.jsp' page without data.
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/login-admin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Requested parameters
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(email.equals("admin@admin.com") && password.equals("admin@admin.com")) {

            User admin = new User("Admin", "Admin", null, email, password);
            HttpSession session = req.getSession();
            session.setAttribute("admin", admin);

            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30 * 60);

            setCookie(resp, "admin", email, 30 * 60);

            setCookie(resp, "successfulMessage", "Welcome to ADMIN panel", 5);
            resp.sendRedirect("/admin-area");
        } else {
            setCookie(resp, "errorMessage", "You are not an admin user", 5);
            resp.sendRedirect("/login-admin.jsp");
        }
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }
}
