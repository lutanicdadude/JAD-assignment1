package controllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dao.FeedbackDAO;
import model.Feedback;
import model.User;

@WebServlet("/DeleteFeedback")
public class DeleteFeedback extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteFeedback() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = null;

        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        // Not logged in → go to login page
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        int feedbackId = Integer.parseInt(request.getParameter("id"));

        FeedbackDAO dao = new FeedbackDAO();

        // Permission check
        Feedback fb = dao.getFeedbackById(feedbackId);
        if (fb == null || fb.getUserId() != user.getId()) {
            session.setAttribute("feedbackError", "You are not allowed to delete this feedback.");
            response.sendRedirect(request.getContextPath() + "/feedback");
            return;
        }

        boolean success = dao.deleteFeedback(feedbackId);

        if (success) {
            session.setAttribute("feedbackSuccess", "Feedback deleted successfully.");
        } else {
            session.setAttribute("feedbackError", "Failed to delete feedback.");
        }

        response.sendRedirect(request.getContextPath() + "/feedback");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/feedback");
    }
}
