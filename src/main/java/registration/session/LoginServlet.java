package registration.session;

import registration.CookieFactory;
import registration.InstanceRepository;
import registration.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/loginUser")
public class LoginServlet extends HttpServlet implements InstanceRepository, CookieFactory {

    //private static final long serialVersionUID = 1L;

    /**
     * Sends redirect to 'login.jsp' page without data.
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/login.jsp");
    }

    /**
     * <p>
     *     Creates String variables 'email' and 'password' as parameters of HttpServletRequest object 'req'
     *     of servlet. Creates User 'user' object and sets as return value of getByEmail() method of
     *     'ur' variable instance of UserRepository class.
     * </p>
     * <p>
     *     Checks if 'email' field of 'user' object is not null.
     *     In case of true, checks if 'email' String variable is equal to 'email' field of 'user' object AND
     *     if 'password' String value is equal to 'password' field of 'user' object.
     *     In case of true, creates session and cookies. Session ges User object as a value. Cookies get 'email' value and success message;
     *     In case of false, creates cookie with error message and redirects to 'login.jsp' page.
     * </p>
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure or any input / output requests failure.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Requested parameters
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        //creates User object and sets it as an object from database found by 'email' column
        User user = ur.getByEmail(email);

        if(user.getEmail() != null) {

            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30 * 60);

                setCookie(resp, "user", email, 30 * 60);
                setCookie(resp, "successfulMessage", "Nice to see you and welcome back, "+ user.getName(), 5);

                resp.sendRedirect("/personal-area");
            } else {
                setCookie(resp, "errorMessage", "Either username or password is wrong", 5);
                resp.sendRedirect("/login.jsp");
            }
        } else {
            setCookie(resp, "errorMessage", "This email is not registered", 5);
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
