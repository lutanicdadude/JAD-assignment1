//Lucas Dong
//P2429535

package controllerServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import dao.BookingDAO;
import dao.ServiceDAO;
import model.ServiceResult;
import model.User;

@WebServlet("/bookService")
public class BookServiceRedirect extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookServiceRedirect() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check login using the "user" session attribute (same as home/profile)
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (session == null || user == null) {
            // Not logged in → go to login page
            response.sendRedirect(request.getContextPath() + "/view/loginPage/loginPage.jsp");
            return;
        }
        
        // Retreive Service Id from URL
        String serviceIdStr = request.getParameter("serviceId");

        // validate Service Id to make sure it is not null
        // If null or empty return back to service page
        if (serviceIdStr == null || serviceIdStr.trim().isEmpty()) {
          response.sendRedirect(request.getContextPath() + "/view/errorPage/errorPage.jsp");
          return;
        }
        
        // Change datatype of serviceIdStr to int
        int serviceId = Integer.parseInt(serviceIdStr);
        
        // Run DAO to get Service name
        BookingDAO Bdao = new BookingDAO();
        String serviceName = Bdao.getServiceName(serviceId);
        
        ServiceDAO Sdao = new ServiceDAO();
        ServiceResult serviceData = Sdao.getServicesById(serviceId);
        System.out.println(serviceName);
        
        // Get price
        String price = "0.00";

        if (serviceData != null && serviceData.getprice() != null && !serviceData.getprice().isEmpty()) {
            price = serviceData.getprice();
        }

        // Store price in request
        System.out.println(price);
        session.setAttribute("servicePrice", price);
        request.setAttribute("DBservicePrice", price);
        
        // Store service name in request
        request.setAttribute("serviceName", serviceName);
        
        // Store Id in serviceId
        session.setAttribute("serviceId", serviceId);

        RequestDispatcher rd = request.getRequestDispatcher("/view/bookingPage/bookingService.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
