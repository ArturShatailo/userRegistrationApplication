package registration.filters;

import registration.CookieFactory;
import registration.entity.User;

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
        User user = (User) session.getAttribute("user");
        User admin = (User) session.getAttribute("admin");

        if(adminPermission(admin, uri)) noAccessRedirect(res);
        else
            if (userPermission(user, uri)) noAccessRedirect(res);
            else
                chain.doFilter(request, response);

    }

    private void noAccessRedirect(HttpServletResponse res) {
        this.context.log("<<< Unauthorized access request");
        setCookie(res, "errorMessage", "No access", 5);
        try {
            res.sendRedirect("/login.jsp");
        } catch (IOException e) {
            setCookie(res, "errorMessage", "Cannot redirect", 5);
        }
    }

    private boolean userPermission(User u, String uri) {
        return u == null && (uri.endsWith("/personal-area.jsp")
                || uri.endsWith("/personal-area")
                || uri.endsWith("/get-user")
                || uri.endsWith("/editUserServlet"));
    }

    private boolean adminPermission(User u, String uri) {
        return u == null && (uri.endsWith("/admin-area")
                || uri.endsWith("/deleteServlet")
                || uri.endsWith("/get-user-byEmail")
                || uri.endsWith("/viewByIDServlet")
                || uri.endsWith("/get-users")
                || uri.endsWith("/admin.jsp"));
    }

    @Override
    public void setCookie(HttpServletResponse R, String n, String v, int d) {
        Cookie cookie = new Cookie(n, v);
        cookie.setMaxAge(d);
        R.addCookie(cookie);
    }
}
