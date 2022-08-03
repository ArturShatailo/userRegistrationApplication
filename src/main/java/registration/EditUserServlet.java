package registration;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/editUserServlet")
public class EditUserServlet extends HttpServlet implements InstanceRepository, CookieFactory {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPut(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String country = req.getParameter("country");

        user.setName(name);
        user.setSurname(surname);
        user.setCountry(country);

        ur.update(user);
        setCookie(resp, "successfulMessage", "Information changed", 5);

        resp.sendRedirect("/registration/personal-area.jsp");

    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }
}
