package adminControllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import model.Customer;
import model.ServiceResult;
import model.Booking;
import model.Feedback;

import dao.ServiceDAO;
import dao.UserDAO;
import dao.AdminDAO;
import dao.BookingDAO;
import dao.FeedbackDAO;

@WebServlet("/admin/DisplayAdminDetails")
public class DisplayAdminDetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DisplayAdminDetails() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int limit = 10;

        // ======== SERVICES PAGINATION ========
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int offset = (page - 1) * limit;

        // ======== CUSTOMERS PAGINATION ========
        int customerPage = 1;
        String customerPageParam = request.getParameter("customerPage");
        if (customerPageParam != null) {
            try {
                customerPage = Integer.parseInt(customerPageParam);
            } catch (NumberFormatException e) {
                customerPage = 1;
            }
        }
        int customerOffset = (customerPage - 1) * limit;

        // ======== BOOKINGS PAGINATION ========
        int bookingPage = 1;
        String bookingPageParam = request.getParameter("bookingPage");
        if (bookingPageParam != null) {
            try {
                bookingPage = Integer.parseInt(bookingPageParam);
            } catch (NumberFormatException e) {
                bookingPage = 1;
            }
        }
        int bookingOffset = (bookingPage - 1) * limit;

        // ======== FEEDBACK PAGINATION ========
        int feedbackPage = 1;
        String feedbackPageParam = request.getParameter("feedbackPage");
        if (feedbackPageParam != null) {
            try {
                feedbackPage = Integer.parseInt(feedbackPageParam);
            } catch (NumberFormatException e) {
                feedbackPage = 1;
            }
        }
        int feedbackOffset = (feedbackPage - 1) * limit;

        // ======== SERVICES ========
        ServiceDAO serviceDAO = new ServiceDAO();
        List<ServiceResult> services = serviceDAO.getService(limit, offset);
        int totalServiceRows = serviceDAO.countServices();
        int totalServicePages = (int) Math.ceil(totalServiceRows / (double) limit);

        request.setAttribute("services", services);
        request.setAttribute("totalServiceRows", totalServiceRows);
        request.setAttribute("totalServicePages", totalServicePages);
        request.setAttribute("currentPage", page); // used by Services tab

        // ======== CUSTOMERS ========
        UserDAO userDAO = new UserDAO();
        List<Customer> customers = userDAO.getCustomerData(limit, customerOffset);
        int totalCustomerRows = userDAO.countUsers();
        int totalCustomerPages = (int) Math.ceil(totalCustomerRows / (double) limit);

        request.setAttribute("customers", customers);
        request.setAttribute("totalCustomerRows", totalCustomerRows);
        request.setAttribute("totalCustomerPages", totalCustomerPages);
        request.setAttribute("customerPage", customerPage);

        // ======== BOOKINGS ========
        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookings = bookingDAO.getBookings(limit, bookingOffset);
        int totalBookingRows = bookingDAO.countBookings();
        int totalBookingPages = (int) Math.ceil(totalBookingRows / (double) limit);

        request.setAttribute("bookings", bookings);
        request.setAttribute("totalBookingRows", totalBookingRows);
        request.setAttribute("totalBookingPages", totalBookingPages);
        request.setAttribute("bookingPage", bookingPage);

        // ======== FEEDBACK ========
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<Feedback> feedbackList = feedbackDAO.getFeedback(limit, feedbackOffset);
        int totalFeedbackRows = feedbackDAO.countFeedback();
        int totalFeedbackPages = (int) Math.ceil(totalFeedbackRows / (double) limit);

        request.setAttribute("feedback", feedbackList);
        request.setAttribute("totalFeedbackRows", totalFeedbackRows);
        request.setAttribute("totalFeedbackPages", totalFeedbackPages);
        request.setAttribute("feedbackPage", feedbackPage);

        // ======== KPI COUNTS: BOOKINGS (Open) & FEEDBACK (Unread) ========
        int bookingsOpen = bookingDAO.countOpenBookings();      // e.g. where status = 'Pending'
        int feedbackUnread = feedbackDAO.countUnreadFeedback(); // e.g. where resolved = false

        Map<String, Integer> counts = new HashMap<>();
        counts.put("bookingsOpen", bookingsOpen);
        counts.put("feedbackUnread", feedbackUnread);
        request.setAttribute("counts", counts);

        // ======== ADMIN COUNT ========
        AdminDAO adminDAO = new AdminDAO();
        int adminCount = adminDAO.countAdmin();
        request.setAttribute("adminCount", adminCount);

        // Forward
        RequestDispatcher rd = request.getRequestDispatcher("/view/admin/admin.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
