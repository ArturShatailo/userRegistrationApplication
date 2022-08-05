package registration;

import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/get-user-byEmail")
public class ViewByEmailServlet extends HttpServlet implements InstanceRepository, IdIterable, CookieFactory {

    /**
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            //Calls method that returns "id" value from 'request' HttpServletRequest object
            String email = req.getParameter("email");

            //Gets User object by method getById od UserRepository class according to the received id in request
            User user = ur.getByEmail(email);

            //Checks if employee object is not null
            if (user == null) {
                throw new NullPointerException();
            }
            JSONObject obj = new JSONObject();
            obj.put("id", user.getId());
            obj.put("name", user.getName());
            obj.put("surname", user.getSurname());
            obj.put("email", user.getEmail());
            obj.put("country", user.getCountry());
            obj.put("password", user.getPassword());

            setCookie(resp, "successfulMessage", "Record found successfully!", 5);
            out.print(obj);

        } catch (NumberFormatException npe) {
            setCookie(resp, "errorMessage", "Email is null", 5);
        } catch (NullPointerException npe) {
            setCookie(resp, "errorMessage", "Sorry, can't find this object in database", 5);
        }finally {
            out.close();
        }

    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }
}
