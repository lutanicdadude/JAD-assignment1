package controllerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.RegisterResult;
import java.io.IOException;
import dao.PaymentDAO;
import dao.UserDAO;
import model.Payment;

/**
 * Servlet implementation class ValidatePayment
 */
@WebServlet("/ValidatePayment")
public class ValidatePayment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidatePayment() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
	  
	  HttpSession session = request.getSession(false);
	  if (session == null) {
	      response.sendRedirect(request.getContextPath() + "/login.jsp");
	      return;
	  }

      String cardName = request.getParameter("cardName");
      String cardNumber = request.getParameter("cardNumber");
      String expiry = request.getParameter("expiry");
      String cvv = request.getParameter("cvv");
      String email = (String) session.getAttribute("userEmail");
      String serviceType = request.getParameter("serviceType");
      String preferredDateStr = request.getParameter("preferredDate");
      String startTime = request.getParameter("startTime");
      String endTime = request.getParameter("endTime");
      String additionalNotes = request.getParameter("additionalNotes");
      int numOfParticipants = Integer.parseInt(request.getParameter("participants"));
      String location = request.getParameter("outingLocation");
      System.out.println("duration: " + request.getParameter("duration"));
      double duration = Double.parseDouble(request.getParameter("duration")); 
  
      if (cardName == null || cardName.trim().isEmpty()) {
          request.setAttribute("paymentError", "Name on card is required.");
      } else if (!cardNumber.matches("\\d{13,19}")) {
          request.setAttribute("paymentError", "Invalid card number.");
      } else if (!expiry.matches("\\d{2}/\\d{2}")) {
          request.setAttribute("paymentError", "Invalid expiry format.");
      } else if (!cvv.matches("\\d{3,4}")) {
          request.setAttribute("paymentError", "Invalid CVV.");
      }
  
      if (request.getAttribute("paymentError") != null) {
          request.getRequestDispatcher("/view/payment/paymentPage.jsp").forward(request, response);
          return;
      }
      
      // Call DAO to insert data
      PaymentDAO dao = new PaymentDAO();
      boolean success = dao.insertPayment(email, serviceType, preferredDateStr, startTime, endTime, duration, additionalNotes, numOfParticipants, location);
      
      if (success) {
        request.getRequestDispatcher("/view/payment/paymentSuccess.jsp")
        .forward(request, response);
      } else {
       request.setAttribute("paymentError", "Payment failed. Please try again.");
       request.getRequestDispatcher("/view/payment/paymentPage.jsp")
              .forward(request, response);
      }
  }


}
