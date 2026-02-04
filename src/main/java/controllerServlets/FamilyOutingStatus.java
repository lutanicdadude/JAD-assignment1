package controllerServlets;

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
 * Servlet implementation class FamilyOutingStatus
 */
@WebServlet("/FamilyOutingStatus")
public class FamilyOutingStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FamilyOutingStatus() {
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
  	    // Get action from form
        String action = request.getParameter("action");
        
        // Get id from form
        int id = Integer.parseInt(request.getParameter("outingId"));
        
        // Create DAO instance and validate user
        ServiceDAO dao = new ServiceDAO();
        ServiceStatus result = dao.updateStatus(action, id);
    
        if (result.isSuccess()) {
            // susceptible to broken access control
            response.sendRedirect(request.getContextPath()+"/FamilyOuting");
        } else {
            // If login fails, show error message on login page
            request.setAttribute("errorMessage", result.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/view/resetPassword/resetPassword.jsp");
            rd.forward(request, response);
        }
	}

}
