package controllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import dao.FeedbackDAO;
import model.Feedback;
import model.User;

@WebServlet("/SubmitFeedback")
public class SubmitFeedback extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SubmitFeedback() {
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

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        String title     = request.getParameter("title");
        String ratingStr = request.getParameter("rating");
        String message   = request.getParameter("message");

        String errorMessage = null;
        int rating = 0;

        if (title == null || title.trim().isEmpty()) {
            errorMessage = "Title is required.";
        } else if (message == null || message.trim().isEmpty()) {
            errorMessage = "Feedback message is required.";
        } else {
            try {
                rating = Integer.parseInt(ratingStr);
                if (rating < 1 || rating > 5) {
                    errorMessage = "Rating must be between 1 and 5.";
                }
            } catch (NumberFormatException e) {
                errorMessage = "Invalid rating value.";
            }
        }

        if (errorMessage == null) {
            FeedbackDAO dao = new FeedbackDAO();
            boolean ok = dao.insertFeedback(user.getId(), title.trim(), rating, message.trim());

            if (ok) {
                session.setAttribute("feedbackSuccess", "Thank you! Your feedback has been submitted.");
            } else {
                session.setAttribute("feedbackError", "Failed to submit feedback. Please try again.");
            }
        } else {
            session.setAttribute("feedbackError", errorMessage);
        }

        // PRG pattern: redirect to /feedback so FeedbackRedirect loads the list
        response.sendRedirect(request.getContextPath() + "/feedback");
    }
}
