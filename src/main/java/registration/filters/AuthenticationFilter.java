package registration.filters;

import registration.CookieFactory;
import registration.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        User sessionObject = (User) req.getSession();

        if (sessionObject == null && !(uri.endsWith("/home.jsp")
                || uri.endsWith("/loginUser")
                || uri.endsWith("/login.jsp")
                || uri.endsWith("/registerUser")
                || uri.endsWith("/registration.jsp")
                || uri.endsWith(".com"))) {
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
