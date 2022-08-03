package registration;

import javax.servlet.http.HttpServletResponse;

public interface CookieFactory {

    void setCookie(HttpServletResponse R, String n, String v, int d);

}
