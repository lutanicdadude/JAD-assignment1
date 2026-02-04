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

@WebServlet("/feedback")
public class FeedbackRedirect extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FeedbackRedirect() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = null;

        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (user == null) {
            // Not logged in → go to login page
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }

        // Get success/error "flash" messages from session (see SubmitFeedback below)
        String success = (String) session.getAttribute("feedbackSuccess");
        String error   = (String) session.getAttribute("feedbackError");
        if (success != null) {
            request.setAttribute("successMessage", success);
            session.removeAttribute("feedbackSuccess");
        }
        if (error != null) {
            request.setAttribute("errorMessage", error);
            session.removeAttribute("feedbackError");
        }

        // Load feedback list from DB
        FeedbackDAO dao = new FeedbackDAO();
        List<Feedback> feedbackList = dao.getFeedbackByUserId(user.getId());

        // Pass data to JSP
        request.setAttribute("user", user);
        request.setAttribute("feedbackList", feedbackList);

        // Show feedback page
        request.getRequestDispatcher("/view/feedbackPage/feedback.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
