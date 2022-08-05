package registration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteServlet")
public class DeleteUserServlet extends HttpServlet implements InstanceRepository, IdIterable, CookieFactory {

    /**
     * Creates id int variable and starts try-catch structure check.
     * Inside try block servlet tries to call method getID, implemented from IdItable interface to get id value
     * from request. In case of no NumberFormatException, calls delete() method of 'ur' variable.
     * Then, checks status and in case of successful deleting, prints message.
     * In case of NumberFormatException prints error message.
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws ServletException Exception in case of servlet error, ErrorHandler servlet should be called
     * but is not created
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id;

        try{
            //calls method that returns "id" value from 'request' HttpServletRequest object
            id = getID(req);

            //Call delete method of UserRepository class object.
            //Method is implemented from Crudable.
            int status = ur.delete(id);

            if (status > 0) {
                setCookie(resp, "successfulMessage", "Record removed successfully!", 5);
            } else {
                setCookie(resp, "errorMessage", "Unable to remove record", 5);
            }
        } catch (NumberFormatException npe) {
            setCookie(resp, "errorMessage", "Unable to remove record", 5);
        }
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }

}
