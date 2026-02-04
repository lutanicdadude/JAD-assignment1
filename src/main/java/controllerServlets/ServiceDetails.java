package controllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dao.ServiceDAO;
import model.ServiceResult;

/**
 * Servlet implementation class ServiceDetails
 */
@WebServlet("/ServiceDetails")
public class ServiceDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    int serviceId = Integer.parseInt(request.getParameter("serviceId"));

	    // Fetch service details from DB
	    ServiceDAO dao = new ServiceDAO();
	    ServiceResult service = dao.getServicesById(serviceId);
	    
	    // throw error if nothing is returned
	    if(service == null) {
	      request.setAttribute("errorMessage", "Service does not exist.");
	      request.getRequestDispatcher("/services.jsp").forward(request, response);
	      return;
	    }

	    request.setAttribute("service", service);
	    request.getRequestDispatcher("/bookService.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
