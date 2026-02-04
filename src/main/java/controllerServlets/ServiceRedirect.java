package controllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;

@WebServlet("/services")
public class ServiceRedirect extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServiceRedirect() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check login using the "user" session attribute (same as home/profile)
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (session == null || user == null) {
            // Not logged in → go to login page
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        // Optionally make user available to the JSP
        request.setAttribute("user", user);

        // Logged in → show services page
        request.getRequestDispatcher("/view/services/services.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
