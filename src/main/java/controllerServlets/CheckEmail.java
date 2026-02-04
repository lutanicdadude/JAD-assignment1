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
 * Servlet implementation class ChackEmail
 */
@WebServlet("/CheckEmail")
public class CheckEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckEmail() {
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
	// Retrieve parameters from login form
      String email = request.getParameter("email");
  
      // Create DAO instance and validate email
      UserDAO dao = new UserDAO();
      VerifyUser result = dao.verify_email(email);
  
      if (result.isSuccess()) {
          // susceptible to broken access control
          // Redirect to change password page
          response.sendRedirect(request.getContextPath()+"/view/resetPassword/resetPassword.jsp?email="+email);
      } else {
          // If login fails, show error message on login page
          request.setAttribute("errorMessage", result.getMessage());
          RequestDispatcher rd = request.getRequestDispatcher("/view/resetPassword/verifyEmail.jsp");
          rd.forward(request, response);
      }
	}

}
