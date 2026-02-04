package adminControllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import dao.UserDAO;

@WebServlet("/admin/EditCustomer")
public class EditCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditCustomer() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr            = request.getParameter("id");          // user_id
        String name             = request.getParameter("name");
        String email            = request.getParameter("email");
        String preferredPayment = request.getParameter("preferredPayment"); // "Cash" / "Online" / ""

        int userId = 0;
        try {
            userId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Normalize empty -> null
        if (preferredPayment != null) {
            preferredPayment = preferredPayment.trim();
            if (preferredPayment.isEmpty()) {
                preferredPayment = null;
            }
        }

        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.updateCustomer(userId, name, email, preferredPayment);

        // Could use success/fail flash messages, but just redirect for now
        response.sendRedirect(request.getContextPath() + "/admin/DisplayAdminDetails#customers");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/admin/DisplayAdminDetails");
    }
}
