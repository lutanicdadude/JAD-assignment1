package adminControllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.RegisterResult;
import java.io.IOException;
import dao.AdminDAO;

/**
 * Servlet implementation class AddAdmin
 */
@WebServlet("/admin/AddAdmin")
public class AddAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAdmin() {
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
        String password1 = request.getParameter("password");
        String password2 = request.getParameter("confirmPassword");
    
        // validate credentials and create new user
        AdminDAO dao = new AdminDAO();
        RegisterResult result = dao.registerAdmin(email, password1, password2);
    
        if (result.isSuccess()) {
            request.setAttribute("successMessage", result.getMessage());
            // Redirect to admin page
            response.sendRedirect(request.getContextPath() + "/admin/DisplayAdminDetails");
        } else {
          // Keep the modal open and pass error message
          request.setAttribute("modalToOpen", "addAdminModal");
          request.setAttribute("errorMessage", result.getMessage());
          request.setAttribute("addAdminModalOpen", true);
          RequestDispatcher rd = request.getRequestDispatcher("/admin/DisplayAdminDetails");
          rd.forward(request, response);
        }
	}

	
}
