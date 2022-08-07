package registration.filters;

import registration.CookieFactory;
import registration.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter, CookieFactory {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log(">>> AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        HttpSession session = req.getSession();
        User sessionObject = (User) session.getAttribute("user");
        User sessionObjectAdmin = (User) session.getAttribute("admin");

        if ((sessionObject == null || sessionObjectAdmin == null) && (uri.endsWith("/admin-area")
                || uri.endsWith("/loginAdmin")
                || uri.endsWith("/deleteServlet")
                || uri.endsWith("/editUserServlet")
                || uri.endsWith("/get-user")
                //|| uri.endsWith("login-admin.jsp")
                || uri.endsWith("/personal-area.jsp")
                || uri.endsWith("/personal-area")
                || uri.endsWith("/get-user-byEmail")
                || uri.endsWith("/viewByIDServlet")
                || uri.endsWith("/get-users")
                || uri.endsWith("/admin.jsp"))) {

            this.context.log("<<< Unauthorized access request");
            setCookie(res, "errorMessage", "No access", 5);
            res.sendRedirect("/login.jsp");

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }
}
