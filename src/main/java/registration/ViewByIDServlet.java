package registration;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/viewByIDServlet")
public class ViewByIDServlet extends HttpServlet implements InstanceRepository, IdIterable {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        //get id according to the request
        int id = getID(req);

        //get employee object according to received id
        User user = ur.getById(id);

        out.print(user);
        out.close();
    }
}
