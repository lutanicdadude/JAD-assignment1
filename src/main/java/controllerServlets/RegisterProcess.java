package controllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.RegisterResult;
import java.io.IOException;
import dao.UserDAO;

/**
 * Servlet implementation class RegisterProcess
 */
@WebServlet("/RegisterProcess")
public class RegisterProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterProcess() {
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
	  // Retrieve parameters from register form
      String email = request.getParameter("email");
      String password1 = request.getParameter("inputPassword1");
      String password2 = request.getParameter("inputPassword2");
      System.out.println("email: " + email);
      System.out.println("passowrd1: " + password1);
      System.out.println("password2: " + password2);
  
      // validate credentials and create new user
      UserDAO dao = new UserDAO();
      RegisterResult result = dao.registerUser(email, password1, password2);
  
      if (result.isSuccess()) {
          // Redirect to login page
          response.sendRedirect(request.getContextPath() +"/view/loginPage/loginPage.jsp");
      } else {
          // If register fails, show error message on register page
          request.setAttribute("errorMessage", result.getMessage());
          RequestDispatcher rd = request.getRequestDispatcher("/view/register/registerPage.jsp");
          rd.forward(request, response);
      }
	}

}
