package registration.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import registration.Interceptors.Logged;
import registration.entity.User;
import registration.repository.InstanceRepository;

@Logged
@Slf4j
@WebServlet("/get-user")
public class GetUserServlet extends HttpServlet implements InstanceRepository, CookieFactory {

    /**
     * Gets object from session and cast it into User class. The value from session is set into 'user' object
     * instance of User class.
     * If 'user' is not null, creates JSONObject 'obj' and puts into it values of 'user' object fields with
     * accordingly set fields.
     * Using PrintWriter object 'out' sends created JSONObject 'obj' object in 'resp'.
     * In case of 'user' is null, sets 'errorMessage' coolie record and redirects to 'login.jsp' page.
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(user != null){

            log.info("Try to get user information as JSON: {} by servlet {}", user, this.getServletName());

            resp.setContentType("application/json");
            JSONObject obj = new JSONObject();

            obj.put("name", user.getName());
            obj.put("surname", user.getSurname());
            obj.put("email", user.getEmail());
            obj.put("country", user.getCountry());

            PrintWriter out = resp.getWriter();
            out.println(obj);
            out.close();

        } else {

            log.info("Session is empty and user information cannot be uploaded by servlet {}", this.getServletName());

            setCookie(resp, "errorMessage", "Sorry, unable to login", 5);
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
