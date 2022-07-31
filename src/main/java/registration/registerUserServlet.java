package registration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet ("/registerUser")
public class registerUserServlet extends HttpServlet implements InstanceRepository, IdIterable {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        //get string values that can be requested in request object.
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("passwordRepeat");


        //Form verification


        try {
            if(!passwordRepeat.equals(password)) throw new Exception();
        } catch (Exception e) {
            out.println("Sorry, password is not the same as repeated one");
            out.close();
        }

        /*try {
            User user = ur.getById(id);
            if(user.getEmail() != null) throw new Exception();
        } catch (Exception e) {
            out.println("Sorry, this email is already registered");
            out.close();
        }*/

        User user = new User(name, surname, email, country, password);

        int status = ur.save(user);

        if(status > 0) {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
            //req.getRequestDispatcher("viewByIDServlet").forward(req, resp);
            //resp.sendRedirect("viewServlet");
        } else {
            out.println("Sorry, unable to create new record");
        }
        out.close();

    }
}
