package adminControllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ServiceStatus;
import java.io.IOException;
import dao.UserDAO;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/admin/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
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
	  // Retrieve the hidden <input name="id"> from the JSP form
      int id = Integer.parseInt(request.getParameter("id"));

      // Call your DAO to delete the service using the id
      UserDAO dao = new UserDAO();
      ServiceStatus deleted = dao.deleteUser(id);
      System.out.println("boolean:" + deleted.isSuccess());

      if (deleted.isSuccess()) {
          // attach a success message
          System.out.println("User deleted successfully.");
      } else {
          // error if the delete failed (e.g., no record found)
          System.out.println("Unable to delete service.");
      }
      // Redirect back to the services page after deletion
      response.sendRedirect(request.getContextPath() + "/admin/DisplayAdminDetails");
	}

}
