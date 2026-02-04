package controllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dao.BookingDAO;
import model.User;

@WebServlet("/DeleteBooking")
public class DeleteBooking extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteBooking() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Basic login check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        // int userId = user.getId(); // reserved for future ownership checks

        String idStr = request.getParameter("id"); // booking_visit.id
        int bookingVisitId = 0;
        try {
            bookingVisitId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        BookingDAO bookingDAO = new BookingDAO();
        boolean success = bookingDAO.deleteBookingVisit(bookingVisitId);

        // Optional: store success/failure somewhere

        // ✅ Redirect back to user’s bookings list
        response.sendRedirect(request.getContextPath() + "/ViewVisit");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/ViewVisit");
    }
}
