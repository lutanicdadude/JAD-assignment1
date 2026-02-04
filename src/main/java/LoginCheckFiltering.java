import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckFiltering implements Filter {

    // called when the filter is first created
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Optional initialization
    }

    // main method of the filter
    // Every request matching the filter's URL pattern passes though this main method
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // don't create a new session

        // Check if user is logged in
        Object user = (session != null) ? session.getAttribute("user") : null;
        String userType = (session != null) ? (String) session.getAttribute("user_type") : null;

        if (user == null) {
            // Not logged in → redirect to login page
            res.sendRedirect(req.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        // Role-based access control
        String uri = req.getRequestURI();
        
        // admin pages start with /admin
        if (uri.contains("/admin") && !"admin".equals(userType)) {
            res.sendRedirect(req.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        // Example: customer pages start with /customer
        if (uri.contains("/customer") && !"customer".equals(userType)) {
            res.sendRedirect(req.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        System.out.println("Valid");       // Authorized → continue with request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Optional cleanup
    }
}