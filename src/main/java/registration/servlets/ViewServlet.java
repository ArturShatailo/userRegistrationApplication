package registration.servlets;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import registration.Interceptors.Logged;
import registration.entity.User;
import registration.repository.InstanceRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Logged
@Slf4j
@WebServlet("/get-users")
public class ViewServlet extends HttpServlet implements InstanceRepository, CookieFactory{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<User> lu = ur.getAll();

        //if(user != null && lu != null){
        log.info("Try to get all users in servlet {}", this.getServletName());

            resp.setContentType("application/json");
            JSONObject obj = new JSONObject();

            int counter = 0;
            for(User u : lu){
                JSONObject uo = new JSONObject();
                uo.put("id", u.getId());
                uo.put("name", u.getName());
                uo.put("surname", u.getSurname());
                uo.put("email", u.getEmail());
                uo.put("country", u.getCountry());
                uo.put("password", u.getPassword());
                obj.put(String.valueOf(counter), uo);
                counter++;
            }

            @Cleanup PrintWriter out = resp.getWriter();
            out.print(obj);
            //out.close();

        //} else {
            //setCookie(resp, "errorMessage", "Sorry, unable to upload data", 5);
            //resp.sendRedirect("/registration/login.jsp");
        //}
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }


}
