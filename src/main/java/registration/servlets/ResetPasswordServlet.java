//package registration;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/resetPassword")
//public class ResetPasswordServlet extends HttpServlet implements InstanceRepository, IdIterable, CookieFactory{
//
//    /**
//     *
//     *
//     * @param req HttpServletRequest request received by servlet
//     * @param resp HttpServletResponse response sent by servlet
//     * @throws IOException can be thrown in case of PrintWriter failure or any input / output requests failure.
//     */
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        resp.setContentType("text/html");
//
//        //get string values that can be requested in request object.
//        String email = req.getParameter("email").trim();
//        req.setAttribute("password", passwordGenerator());
//        String password = resp.getParameter("password").trim();
//        String passwordRepeat = req.getParameter("passwordRepeat").trim();
//
//        //Form verification functional interface 'Validable' implementation
//        Validable v = () -> {
//
//            //Checks if any or all fields is/are empty
//            if(name.equals("") || surname.equals("") || email.equals("")
//                    || country.equals("") || password.equals("") || passwordRepeat.equals("")){
//                setCookie(resp, "errorMessage", "Empty fields are not allowed", 5);
//                return false;
//            }
//
//            //Checks if 'password' field is equal to 'passwordRepeat' field
//            if(!passwordRepeat.equals(password)){
//                setCookie(resp, "errorMessage", "Repeated password is incorrect", 5);
//                return false;
//            }
//
//            /*
//            Try to create new User object and sets it as a value of return from getByEmail() method of
//            'ur' object instance of UserRepository class. Checks if 'email' field of created User object is not
//            null. In case of true, it means that there is some record with this 'email' in database table. So,
//            sets error cookie and returns false.
//            Catches Exception in case of getEmail() method will be addressed to null object and sets success message
//            in cookies.
//            */
//            try {
//                User user = ur.getByEmail(email);
//                if(user.getEmail() != null) {
//                    setCookie(resp, "errorMessage", "This email is already registered", 5);
//                    return false;
//                }
//            } catch (Exception e) {
//                setCookie(resp, "successfulMessage", "Registered successfully", 5);
//                return true;
//            }
//
//            return true;
//        };
//
//        //In case of 'v' variable that is set as functional interface return is true
//        if (v.formValidation()) {
//
//            //New User object creating with data from request (form).
//            User user = new User(name, surname, email, country, password);
//
//            //calls save method of 'ur' object instance of UserRepository class.
//            int status = ur.save(user);
//
//            if(status > 0) {
//                setCookie(resp, "successfulMessage", "Registered successfully", 5);
//                resp.sendRedirect("/loginUser");
//            } else {
//                setCookie(resp, "errorMessage", "Sorry, unable to create new record", 5);
//            }
//        } else {
//            getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
//        }
//    }
//
//    private String passwordGenerator() {
//
//
//
//    }
//
//    @Override
//    public void setCookie(HttpServletResponse R, String n, String v, int d) {
//        Cookie cookie = new Cookie(n, v);
//        cookie.setMaxAge(d);
//        R.addCookie(cookie);
//    }
//}
