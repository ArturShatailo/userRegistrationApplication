package registration;

import registration.CookieFactory;
import registration.InstanceRepository;
import registration.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

@WebServlet("/get-user")
public class GetUserServlet extends HttpServlet implements InstanceRepository, CookieFactory {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(user != null){

            resp.setContentType("application/json");
            JSONObject obj = new JSONObject();

            obj.put("name", user.getName());
            obj.put("surname", user.getSurname());
            obj.put("email", user.getEmail());
            obj.put("country", user.getCountry());
            PrintWriter out = resp.getWriter();
            out.println(obj);

        } else {
            setCookie(resp, "errorMessage", "Sorry, unable to login", 5);
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
