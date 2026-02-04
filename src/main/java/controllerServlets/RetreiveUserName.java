package controllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import dao.UserDAO;
import model.User;
import model.UpcomingAppointments;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class RetreiveUserName
 */
@WebServlet("/RetrieveUserName")
public class RetreiveUserName extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Create a session (or get existing)
        HttpSession session = request.getSession();
		// Get email from session
        String email = (String) session.getAttribute("email");
        // Fetch user info from DB
        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmail(email);
        // Pass user object to JSP
        request.setAttribute("user", user);
        // Fetch multiple bookings
//        List<UpcomingAppointments> bookings = dao.getBookingByEmail(email);
        // Assign multiple bookings to "bookings"
//        request.setAttribute("bookings", bookings);


        // Forward to JSP
        RequestDispatcher rd = request.getRequestDispatcher("/view/homePage/homePage.jsp");
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
