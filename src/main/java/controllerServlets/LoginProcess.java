package controllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import dao.UserDAO;
import model.LoginResult;
import model.User;

@WebServlet("/LoginProcess")
public class LoginProcess extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginProcess() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve parameters from login form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // DAO instance
        UserDAO dao = new UserDAO();

        // Validate user credentials (returns login status + user_type)
        LoginResult result = dao.validateUser(email, password);

        if (result.isSuccess()) {

            // Fetch FULL USER DETAILS from DB ---------------------
            User user = dao.getUserByEmail(email);

            // Preferred payment method
            String preferredPayment = dao.getPreferredPayment(user.getId()); 
            // ------------------------------------------------------------

            // Store into SESSION for use across pages
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userEmail", email);
            session.setAttribute("preferredPayment", preferredPayment);
            session.setAttribute("user_type", result.getUser_type());

            // Redirect based on user type
            if ("customer".equals(result.getUser_type())) {
                response.sendRedirect("RetrieveUserName");
            } else {
                response.sendRedirect("admin/DisplayAdminDetails");
            }

        } else {

            // Login failed → stay on login page
            request.setAttribute("errorMessage", result.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/view/loginPage/loginPage.jsp");
            rd.forward(request, response);
        }
    }
}
