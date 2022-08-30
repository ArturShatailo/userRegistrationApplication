package registration;

import lombok.extern.slf4j.Slf4j;
import registration.Interceptors.Logged;
import registration.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@Logged
@Slf4j
@WebServlet ("/registerUser")
public class RegisterUserServlet extends HttpServlet implements InstanceRepository, IdIterable, CookieFactory{

    /**
     * Creates String variables set as received values from getParameter() method of HttpServletRequest 'req'.
     * Implements Validable functional interface method and the return value is set as variable 'v'.
     * In case of 'v' is true, creates new User object with fields filled out with created String values.
     * Calls save method of 'ur' object instance of UserRepository class.
     * In case of 'v' is false, redirects to 'registration.jsp' page and sets error cookie record with value from
     * implemented Validable functional interface.
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure or any input / output requests failure.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        //get string values that can be requested in request object.
        String name = req.getParameter("name").trim();
        String surname = req.getParameter("surname").trim();
        String email = req.getParameter("email").trim();
        String country = req.getParameter("country").trim();
        String password = req.getParameter("password").trim();
        String passwordRepeat = req.getParameter("passwordRepeat").trim();
        String role = "user";

        //Form verification functional interface 'Validable' implementation
        Validable v = () -> {

            //Checks if any or all fields is/are empty
            if(name.equals("") || surname.equals("") || email.equals("")
                    || country.equals("") || password.equals("") || passwordRepeat.equals("")){
                setCookie(resp, "errorMessage", "Empty fields are not allowed", 5);
                return false;
            }

            //Checks if 'password' field is equal to 'passwordRepeat' field
            if(!passwordRepeat.equals(password)){
                setCookie(resp, "errorMessage", "Repeated password is incorrect", 5);
                return false;
            }

            /*
            Try to create new User object and sets it as a value of return from getByEmail() method of
            'ur' object instance of UserRepository class. Checks if 'email' field of created User object is not
            null. In case of true, it means that there is some record with this 'email' in database table. So,
            sets error cookie and returns false.
            Catches Exception in case of getEmail() method will be addressed to null object and sets success message
            in cookies.
            */
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

        //In case of 'v' variable that is set as functional interface return is true
        if (v.formValidation()) {

            log.info("Try to validate registration form in servlet {}", this.getServletName());
            //New User object creating with data from request (form).
            User user = new User(name, surname, country, email, password, role);

            //calls save method of 'ur' object instance of UserRepository class.
            int status = ur.save(user);

            if(status > 0) {
                log.info("User {} is registered successfully in servlet {}", user.getEmail(), this.getServletName());
                setCookie(resp, "successfulMessage", "Registered successfully", 5);
                resp.sendRedirect("/loginUser");
            } else {
                log.info("Unable to create new record for user {} in servlet {}", user.getEmail(), this.getServletName());
                setCookie(resp, "errorMessage", "Sorry, unable to create new record", 5);
            }
        } else {
            log.error("Validation of registration form is failed in servlet {}", this.getServletName());
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
