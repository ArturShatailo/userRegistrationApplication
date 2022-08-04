package registration;

import javax.servlet.http.HttpServletRequest;

public interface IdIterable {

    /**
     * <p>
     *     Creates String variable equal to parameter "id" from received HttpServletRequest.
     *     "id" parameter is received using getParameter method of HttpServletRequest 'request' object.
     * </p>
     * <p>
     *     Checks if created String is null. Is yes, throws NumberFormatException,
     *     if no, parses String value into Integer.
     * </p>
     *
     * @param request HttpServletRequest object from servlet
     * @return int value of 'id' from HttpServletRequest 'request' object.
     */
    default int getID(HttpServletRequest request){
        String sid = request.getParameter("id");
        if(sid == null)
            throw new NumberFormatException();
        return Integer.parseInt(sid);
    };

}
