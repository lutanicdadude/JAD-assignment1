package controllerServlets;

import java.io.IOException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/EditProfile")
public class EditProfile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("=== EditProfile.doPost ===");

        // 0. Get logged-in user from SESSION (not from the hidden field)
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("No session – redirecting to login");
            response.sendRedirect(request.getContextPath() + "/loginPage/loginPage.jsp");
            return;
        }

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            System.out.println("No user in session – redirecting to login");
            response.sendRedirect(request.getContextPath() + "/loginPage/loginPage.jsp");
            return;
        }

        int userId = sessionUser.getId();
        System.out.println("Session userId = " + userId);

        // 1. Read values from form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String preferredPayment = request.getParameter("preferredPayment");

        System.out.println("New name = " + name);
        System.out.println("New email = " + email);
        System.out.println("New preferredPayment = " + preferredPayment);

        UserDAO dao = new UserDAO();

        boolean ok = dao.updateUserProfile(userId, name, email, preferredPayment);

        System.out.println("updateUserProfile result = " + ok);

        if (!ok) {
            request.setAttribute("profileError", "Failed to update profile. Please try again.");
            // Forward back to profile.jsp (modal will show error at bottom)
            request.getRequestDispatcher("/view/profilePage/profile.jsp")
                   .forward(request, response);
            return;
        }

        // 2. Reload updated user & preferred payment from DB
        User updatedUser = dao.getUserByEmail(email);
        String updatedPreferredPayment = dao.getPreferredPayment(updatedUser.getId());

        // 3. Update session
        session.setAttribute("user", updatedUser);
        session.setAttribute("preferredPayment", updatedPreferredPayment);

        // 4. Redirect back to profile page
        response.sendRedirect(request.getContextPath() + "/profile");
    }
}
