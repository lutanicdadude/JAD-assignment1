package controllerServlets;

import java.io.IOException;
import java.util.List;

import dao.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UpcomingAppointments;
import model.User;

@WebServlet("/home")
public class HomePageRedirect extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HomePageRedirect() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Must be logged in
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        // Load upcoming appointments (old behavior)
//        UserDAO dao = new UserDAO();
//        List<UpcomingAppointments> bookings = dao.getBookingByEmail(user.getEmail());

        // Supply values to JSP
        request.setAttribute("user", user);
//        request.setAttribute("bookings", bookings);

        RequestDispatcher rd =
                request.getRequestDispatcher("/view/homePage/homePage.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
