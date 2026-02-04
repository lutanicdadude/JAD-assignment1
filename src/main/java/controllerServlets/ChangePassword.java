package controllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.VerifyUser;
import java.io.IOException;
import dao.UserDAO;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Get email from URL query parameter
        String email = request.getParameter("email");
        // get password from resetPassword form
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Create DAO instance and validate user
        UserDAO dao = new UserDAO();
        VerifyUser result = dao.verify_password(email, newPassword, confirmPassword);
    
        if (result.isSuccess()) {
            // susceptible to broken access control
            // Redirect to login page
            response.sendRedirect(request.getContextPath()+"/view/loginPage/loginPage.jsp?email="+email);
        } else {
            // If login fails, show error message on login page
            request.setAttribute("errorMessage", result.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/view/resetPassword/resetPassword.jsp");
            rd.forward(request, response);
        }
	}

}
