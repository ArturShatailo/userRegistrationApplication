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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        //get id of the employee has to be deleted
        int id = getID(req);

        //Call delete method of UserRepository class object.
        //Method is implemented from Crudable.
        int status = ur.delete(id);

        if (status > 0) {
            out.print("Record removed successfully!");
            resp.sendRedirect("viewServlet");
        } else {
            out.println("Sorry! unable to remove record");
        }
        out.close();
    }
}
