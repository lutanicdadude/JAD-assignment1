package controllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ServiceResult;
import java.io.IOException;
import java.util.List;
import dao.ServiceDAO;

/**
 * Servlet implementation class displayService
 */
@WebServlet("/displayService")
public class displayService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public displayService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		// limit how many rows to diaply on front-end
		int limit = 5;
		
		if (request.getParameter("page") != null) {
		  // get current page number
		  page = Integer.parseInt(request.getParameter("page"));
		}
		// skip certain number of rows
		int offset = (page-1)*limit;
		
		// Create DAO instance
        ServiceDAO dao = new ServiceDAO(); 
		// fetch paginated services
		List<ServiceResult> result = dao.getService(limit, offset);
		
  	    // get total number of services
		int totalRows = dao.countServices();
		int totalPages = (int) Math.ceil(totalRows/(double)limit);
        request.setAttribute("result", result);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
    
        // Forward to JSP
        RequestDispatcher rd = request.getRequestDispatcher("/view/admin/admin.jsp");
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
