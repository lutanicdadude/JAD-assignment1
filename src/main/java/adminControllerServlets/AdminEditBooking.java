package adminControllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import dao.BookingDAO;

@WebServlet("/admin/AdminEditBooking")
public class AdminEditBooking extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminEditBooking() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // This should be the booking_visit.id that you put in the hidden field in the modal
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

        if (success) {
          response.sendRedirect(request.getContextPath() + "/admin/DisplayAdminDetails");
        } else {
          // Keep the modal open and pass error message
          request.setAttribute("modalToOpen", "editBookingModal");
          RequestDispatcher rd = request.getRequestDispatcher("/admin/DisplayAdminDetails");
          rd.forward(request, response);
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/admin/DisplayAdminDetails");
    }
}
