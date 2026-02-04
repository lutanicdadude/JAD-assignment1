package controllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Service;
import java.io.IOException;
import java.util.List;
import dao.ServiceDAO;

/**
 * Servlet implementation class RetrieveService
 */
@WebServlet("/RetrieveService")
public class RetrieveService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        String category = request.getParameter("category");
        ServiceDAO dao = new ServiceDAO();
        List<Service> services = dao.getServicesByCategory(category);
    
        request.setAttribute("services", services);
        request.setAttribute("pageTitle", category);
    
        RequestDispatcher rd = request.getRequestDispatcher("/displayCategoryServices");
        rd.forward(request, response);
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
