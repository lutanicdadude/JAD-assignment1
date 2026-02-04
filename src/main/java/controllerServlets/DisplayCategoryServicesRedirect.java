package controllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;

@WebServlet("/displayCategoryServices")
public class DisplayCategoryServicesRedirect extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DisplayCategoryServicesRedirect() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check if user is logged in (same pattern as home/profile)
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (session == null || user == null) {
            // Not logged in → go to login page
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        // Optional: make user available to JSP
        request.setAttribute("user", user);

        // Forward to the Family Services JSP
        request.getRequestDispatcher("/view/services/displayServices.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
