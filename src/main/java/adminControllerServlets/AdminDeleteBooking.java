package adminControllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import dao.BookingDAO;

@WebServlet("/admin/AdminDeleteBooking")
public class AdminDeleteBooking extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminDeleteBooking() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        BookingDAO bookingDAO = new BookingDAO();
        boolean success = bookingDAO.deleteBookingVisit(id);

        response.sendRedirect(request.getContextPath() + "/admin/DisplayAdminDetails");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/admin/DisplayAdminDetails");
    }
}
