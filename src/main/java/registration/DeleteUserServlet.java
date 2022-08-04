package registration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteServlet")
public class DeleteUserServlet extends HttpServlet implements InstanceRepository, IdIterable {

    /**
     * Creates id int variable and starts try-catch structure check.
     * Inside try block servlet tries to call method getID, implemented from IdItable interface to get id value
     * from request. In case of no NumberFormatException, calls delete() method of 'ur' variable.
     * Then, checks status and in case of successful deleting, prints message.
     * In case of NumberFormatException prints error message in catch block and closes 'out' PrintWriter
     * in finally block.
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws ServletException Exception in case of servlet error, ErrorHandler servlet should be called
     * but is not created
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        int id = 0;
        try{
            //calls method that returns "id" value from 'request' HttpServletRequest object
            id = getID(req);

            //Call delete method of UserRepository class object.
            //Method is implemented from Crudable.
            int status = ur.delete(id);

            if (status > 0) {
                out.print("Record removed successfully!");
            } else {
                out.println("Sorry! unable to remove record");
            }

        } catch (NumberFormatException npe){
            out.println("Unable to remove record");
        } finally {
            out.close();
        }
    }
}
