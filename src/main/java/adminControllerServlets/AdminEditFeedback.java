package adminControllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import dao.FeedbackDAO;

@WebServlet("/admin/AdminEditFeedback")
public class AdminEditFeedback extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminEditFeedback() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr     = request.getParameter("id");
        String message   = request.getParameter("message");
        String ratingStr = request.getParameter("rating");

        int id = 0;
        int rating = 0;

        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            rating = Integer.parseInt(ratingStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        FeedbackDAO feedbackDAO = new FeedbackDAO();

        // ⚠️ Use the version of updateFeedback that matches your DAO:
        // If you changed DAO to (id, rating, message):
        feedbackDAO.updateFeedback(id, rating, message);

        // If your DAO still has the 'resolved' parameter, use this instead:
        // feedbackDAO.updateFeedback(id, rating, message, false);

        response.sendRedirect(request.getContextPath() + "/admin/DisplayAdminDetails");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/admin/DisplayAdminDetails");
    }
}
