package registration.servlets;

import registration.entity.User;
import registration.repository.InstanceRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/personal-area")
public class PersonalAreaServlet extends HttpServlet implements InstanceRepository, CookieFactory{


    /**
     * Gets HttpSession object using request object, and it's method getSession().
     * Gets User object from session and in case of User is not null redirecting to 'personal-area.jsp' page;
     * In case of User is null, sets cookie "errorMessage" with error message and redirects to 'login.jsp' page.
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws ServletException Exception in case of servlet error, ErrorHandler servlet should be called
     * but is not created
     * @throws IOException can be thrown in case of PrintWriter failure or any input / output requests failure.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(user != null){
            resp.sendRedirect("/personal-area.jsp");
        } else {
            setCookie(resp, "errorMessage", "Sorry, unable to login", 5);
            resp.sendRedirect("/login.jsp");
        }

/*
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(user !=null ) {
            req.setAttribute("name", user.getName());
            req.setAttribute("surname", user.getSurname());
            req.setAttribute("email", user.getEmail());
            req.setAttribute("country", user.getCountry());

            getServletContext().getRequestDispatcher("/personal-area.jsp").forward(req, resp);

        } else {
            setCookie(resp, "errorMessage", "Sorry, unable to login", 5);
            resp.sendRedirect("/registration/login.jsp");
        }
*/
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }
}
