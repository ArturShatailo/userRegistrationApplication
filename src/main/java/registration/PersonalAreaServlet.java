package registration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/personal-area")
public class PersonalAreaServlet extends HttpServlet implements InstanceRepository, CookieFactory{


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(user != null){
            resp.sendRedirect("/registration/personal-area.jsp");
        } else {
            setCookie(resp, "errorMessage", "Sorry, unable to login", 5);
            resp.sendRedirect("/registration/login.jsp");
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
