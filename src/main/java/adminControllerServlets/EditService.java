package adminControllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ServiceStatus;
import java.io.IOException;
import dao.ServiceDAO;

/**
 * Servlet implementation class editService
 */
@WebServlet("/admin/EditService")
public class EditService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditService() {
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
	  // Get data from front-end
	  int id = Integer.parseInt(request.getParameter("id"));
      String name = request.getParameter("name");
      String category = request.getParameter("category");
      String description = request.getParameter("description");
      double price = Double.parseDouble(request.getParameter("price"));
      int duration = Integer.parseInt(request.getParameter("duration"));
      String activeParam = request.getParameter("active");
      boolean active = activeParam != null;   // true = checked, false = unchecked
      
      // Fetch user info from DB
      ServiceDAO dao = new ServiceDAO();
      ServiceStatus result = dao.editservice(id, name, category, description, price, duration, active);
      
      if (result.isSuccess()) {
        request.setAttribute("successMessage", result.getMessage());
        // redirect back to admin page
        response.sendRedirect(request.getContextPath() + "/admin/DisplayAdminDetails");
      } else {
        // Keep the modal open and pass error message
        request.setAttribute("modalToOpen", "editServiceModal");
        request.setAttribute("errorMessage", result.getMessage());
        RequestDispatcher rd = request.getRequestDispatcher("/admin/DisplayAdminDetails");
        rd.forward(request, response);
      }
      
      
	}

}
