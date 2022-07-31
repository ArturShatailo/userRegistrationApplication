package registration;

import javax.servlet.http.HttpServletRequest;

public interface IdIterable {

    default int getID(HttpServletRequest request){
        String sid = request.getParameter("id");
        if(sid == null)
            throw new NumberFormatException();
        return Integer.parseInt(sid);
    };

}
