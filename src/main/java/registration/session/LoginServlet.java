package registration.session;

import registration.InstanceRepository;
import registration.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginUser")
public class LoginServlet extends HttpServlet implements InstanceRepository {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        // Requested parameters
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = ur.getByEmail(email);

        if(user.getEmail() != null) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                HttpSession session = req.getSession();
                session.setAttribute("user", "user");
                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30 * 60);
                Cookie userEmail = new Cookie("user", email);
                userEmail.setMaxAge(30 * 60);
                resp.addCookie(userEmail);
                out.println("Welcome back to the team, " + user.getName() + "!");
            } else {
                out.println("Either user name or password is wrong!");
                out.println(user.getEmail());
                out.println(email);
                out.println(password);
                out.println(user.getPassword());
            }
        } else {
            out.println("This email is not registered");
        }
    }
}
