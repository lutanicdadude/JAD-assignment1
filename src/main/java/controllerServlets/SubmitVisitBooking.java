package controllerServlets;

import java.io.IOException;

import dao.BookingDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/SubmitVisitBooking")
public class SubmitVisitBooking extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SubmitVisitBooking() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // 1) Check that user is logged in
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        int userId = user.getId();

        // 2) Get form parameters
        String name           = request.getParameter("name");
        String relationship   = request.getParameter("relationship");
        String visitDate      = request.getParameter("visitDate");      // "YYYY-MM-DD"
        String visitTime      = request.getParameter("visitTime");
        String purposeOfVisit = request.getParameter("purposeOfVisit");

        // Basic validation (you can expand this)
        if (name == null || name.trim().isEmpty()
                || relationship == null || relationship.trim().isEmpty()
                || visitDate == null || visitDate.trim().isEmpty()
                || visitTime == null || visitTime.trim().isEmpty()
                || purposeOfVisit == null || purposeOfVisit.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Please fill in all the fields.");
            RequestDispatcher rd = request.getRequestDispatcher("/view/bookingPage/bookingVisit.jsp");
            rd.forward(request, response);
            return;
        }

        // 3) Insert into DB
        BookingDAO dao = new BookingDAO();

        // 3a) Insert into booking_visit
        int bookingVisitId = dao.insertBookingVisit(
                name.trim(),
                relationship.trim(),
                visitDate.trim(),
                visitTime.trim(),
                purposeOfVisit.trim()
        );

        if (bookingVisitId <= 0) {
            request.setAttribute("errorMessage", "Failed to save visit booking. Please try again.");
            RequestDispatcher rd = request.getRequestDispatcher("/view/bookingPage/bookingVisit.jsp");
            rd.forward(request, response);
            return;
        }

        // 3b) Insert into bookings (link to user)
        boolean linked = dao.linkVisitBookingToUser(userId, bookingVisitId);

        if (!linked) {
            request.setAttribute("errorMessage",
                    "Visit was created, but we could not link it to your account. Please contact support.");
        } else {
            request.setAttribute("successMessage", "Your visit has been booked successfully!");
        }

        // 4) Forward back to the booking page (clear form or keep values as you prefer)
        RequestDispatcher rd = request.getRequestDispatcher("/view/bookingPage/bookingVisit.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        // Optional: redirect GETs to booking page
        response.sendRedirect(request.getContextPath() + "/bookVisit");
    }
}
