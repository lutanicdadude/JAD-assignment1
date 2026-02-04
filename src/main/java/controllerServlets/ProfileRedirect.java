package controllerServlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class ProfileRedirect extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Typically you'll already have the logged-in user set as a request/session attribute
        // e.g. from a filter or login servlet. Here we just forward.

        request.getRequestDispatcher("/view/profilePage/profile.jsp")
               .forward(request, response);
    }
}
