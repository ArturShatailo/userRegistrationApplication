package registration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet ("/registerUser")
public class RegisterUserServlet extends HttpServlet implements InstanceRepository, IdIterable, CookieFactory{

    /**
     *
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        //PrintWriter out = resp.getWriter();

        //get string values that can be requested in request object.
        String name = req.getParameter("name").trim();
        String surname = req.getParameter("surname").trim();
        String email = req.getParameter("email").trim();
        String country = req.getParameter("country").trim();
        String password = req.getParameter("password").trim();
        String passwordRepeat = req.getParameter("passwordRepeat").trim();

        //Form verification
        Validable v = () -> {

            if(name.equals("") || surname.equals("") || email.equals("")
                    || country.equals("") || password.equals("") || passwordRepeat.equals("")){
                setCookie(resp, "errorMessage", "Empty fields are not allowed", 5);
                return false;
            }

            if(!passwordRepeat.equals(password)){
                setCookie(resp, "errorMessage", "Repeated password is incorrect", 5);
                return false;
            }

            try {
                User user = ur.getByEmail(email);
                if(user.getEmail() != null) {
                    setCookie(resp, "errorMessage", "This email is already registered", 5);
                    return false;
                }
            } catch (Exception e) {
                setCookie(resp, "successfulMessage", "Registered successfully", 5);
                return true;
            }

            return true;
        };


        if (v.formValidation()) {

            User user = new User(name, surname, email, country, password);
            int status = ur.save(user);

            if(status > 0) {
                setCookie(resp, "successfulMessage", "Registered successfully", 5);
                resp.sendRedirect("/registration/loginUser");
            } else {
                setCookie(resp, "errorMessage", "Sorry, unable to create new record", 5);
            }
        } else {
            getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }
}
