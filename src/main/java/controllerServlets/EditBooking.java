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

@WebServlet("/EditBooking")
public class EditBooking extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditBooking() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Basic login check (same idea as ViewVisit)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        // int userId = user.getId(); // not enforced now, but ready if you need ownership checks later

        // This should be booking_visit.id (coming from the hidden field in the modal / card)
        String idStr          = request.getParameter("id");
        String name           = request.getParameter("name");
        String relationship   = request.getParameter("relationship");
        String visitDate      = request.getParameter("visitDate");      // "YYYY-MM-DD"
        String visitTime      = request.getParameter("visitTime");      // e.g. "10:00–11:00 AM"
        String purposeOfVisit = request.getParameter("purposeOfVisit");

        int bookingVisitId = 0;
        try {
            bookingVisitId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        BookingDAO bookingDAO = new BookingDAO();
        boolean success = bookingDAO.updateBookingVisit(
                bookingVisitId,
                name,
                relationship,
                visitDate,
                visitTime,
                purposeOfVisit
        );

        // Optional: you could set a request/session attribute with success/failure message

        // ✅ Redirect back to user’s bookings page
        response.sendRedirect(request.getContextPath() + "/ViewVisit");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Just bounce to ViewVisit on GET
        resp.sendRedirect(req.getContextPath() + "/ViewVisit");
    }
}
