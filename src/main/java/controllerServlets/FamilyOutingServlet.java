package controllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import model.FamilyOuting;

/**
 * Servlet implementation class FamilyOuting
 */
@WebServlet("/FamilyOuting")
public class FamilyOutingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FamilyOutingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	  response.setContentType("text/html");
          PrintWriter out = response.getWriter();
    
          // REST client
          Client client = ClientBuilder.newClient();
    
          // Your existing REST API
          String restUrl = "http://localhost:8081/user-ws/FamilyOuting/getAllFamilyOuting";
          WebTarget target = client.target(restUrl);
    
          Invocation.Builder invocationBuilder =
                  target.request(MediaType.APPLICATION_JSON);
    
          Response resp = invocationBuilder.get();
    
//          out.println("<h3>REST Status: " + resp.getStatus() + "</h3>");
    
          if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
    
              ArrayList<FamilyOuting> familyOutings =
                      resp.readEntity(new GenericType<ArrayList<FamilyOuting>>() {});
              
              // Save data
              request.setAttribute("familyOutingArray", familyOutings);
    
//              out.println("<h2>FamilyOuting List</h2>");
//    
//              for (FamilyOuting familyOuting : familyOutings) {
//                  out.println("<hr>");
//                  out.println("ServiceName: " + familyOuting.getServiceName() + "<br>");
//                  out.println("serviceDate: " + familyOuting.getServiceDate() + "<br>");
//                  out.println("additional notes: " + familyOuting.getAdditionalNotes() + "<br>");
//                  out.println("serviceDuration: " + familyOuting.getServiceDuration() + "<br>");
//                  out.println("serviceStart: " + familyOuting.getServiceStart() + "<br>");
//                  out.println("serviceEnd: " + familyOuting.getServiceEnd() + "<br>");
//              }
    
          } else {
//              out.println("<h3>Failed to call getAllUsers API</h3>");
//              out.println("<p>URL: " + restUrl + "</p>");
            request.setAttribute("error", "Failed to retrieve family outings");
          }
    
          client.close();
          
          // forward to JSP
          request.getRequestDispatcher("/view/FamilyOutingWebService/FamilyOutingWebService.jsp")
                 .forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
