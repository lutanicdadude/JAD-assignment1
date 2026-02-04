package controllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Service;
import model.Booking;
import java.io.IOException;
import java.util.List;
import dao.BookingDAO;
import model.User;

/**
 * Servlet implementation class ViewVisit
 */
@WebServlet("/ViewVisit")
public class ViewVisit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewVisit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // retrieve existing session
        HttpSession session = request.getSession(false);
    
        // if no session OR no user in session → redirect to login
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }
    
        // get user from session
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
    
        BookingDAO dao = new BookingDAO();
        List<Booking> bookings = dao.getBooking(userId);
        request.setAttribute("bookings", bookings);
    
        RequestDispatcher rd = request.getRequestDispatcher("/view/bookingPage/viewBookings.jsp");
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
