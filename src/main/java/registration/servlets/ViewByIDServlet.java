package registration.servlets;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import registration.Interceptors.Logged;
import registration.entity.User;
import registration.repository.InstanceRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Logged
@Slf4j
@WebServlet("/viewByIDServlet")
public class ViewByIDServlet extends HttpServlet implements InstanceRepository, IdIterable {

    /**
     * try to get id value by getID() method and in case of no NullPointerException, calls getById() method of
     * 'ur' object to get User needed to be displayed. In case of such id absence,
     * throws NullPointerException. In case of there is found User object in database with such 'id',
     * prints this User object.
     * Catches NumberFormatException or NullPointerException and prints error message, closes 'out' in finally block.
     *
     * @param req HttpServletRequest request received by servlet
     * @param resp HttpServletResponse response sent by servlet
     * @throws IOException can be thrown in case of PrintWriter failure.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        @Cleanup PrintWriter out = resp.getWriter();

        try{
            //Calls method that returns "id" value from 'request' HttpServletRequest object
            int id = getID(req);

            log.info("Try to get User by id: {} in servlet {}", id, this.getServletName());
            log.info("Try to generate Object in servlet {}", this.getServletName());
            //Gets User object by method getById od UserRepository class according to the received id in request
            User user = ur.getById(id);

            //Checks if employee object is not null
            if (user == null){
                log.error("Object is null in servlet {}", this.getServletName(), new NullPointerException());
                throw new NullPointerException();
            }
            out.print(user);

        } catch (NumberFormatException nfe){
            log.error("Id is null in servlet {}", this.getServletName(), nfe);
            out.print("Id is null");
        } catch (NullPointerException npe){
            log.error("can't find this object in database in servlet {}", this.getServletName(), npe);
            out.print("Sorry, can't find this object in database");
        } finally {
            //out.close();
        }
    }
}
