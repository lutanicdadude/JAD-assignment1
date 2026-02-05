<!-- Lucas Dong
P2429535
Jonah Tong
P2429564 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.Duration" %>
<%@ page import="java.time.LocalDate" %>
<%
    // Read values from the previous page (bookingServicePage or similar)
    String serviceType = request.getParameter("serviceName");
    String preferredDateStr = request.getParameter("preferredDate");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String additionalNotes = request.getParameter("additionalNotes");
    String amount = (String) session.getAttribute("servicePrice");

    String participantsStr = request.getParameter("participants");
    int numOfParticipants = 0; // default value

    if (participantsStr != null && !participantsStr.isEmpty()) {
        numOfParticipants = Integer.parseInt(participantsStr);
    }
    
    String locationStr = (String) request.getParameter("outingLocation");
    String location = ""; // default value

    if (locationStr != null && !locationStr.isEmpty()) {
      location = locationStr;
    }
    
    if (amount == null) amount = "0.00";
    
    //Convert String to local time
    LocalTime fromTime = LocalTime.parse(startTime);
	LocalTime toTime = LocalTime.parse(endTime);
    
 	// Validate order
    if (!toTime.isAfter(fromTime)) {
        session.setAttribute("errorMessage", "End time must be after start time.");
        response.sendRedirect(
            request.getContextPath() + "/bookService?serviceId=" + session.getAttribute("serviceId")
        );
        return;
    }
 	
 	// calculate the duration between start and end time
    Duration duration = Duration.between(fromTime, toTime);

    long hours = duration.toHours();
    long minutes = duration.toMinutes() % 60;
    
    // Used to save to database
    long seconds = duration.toSeconds();

    String durationText;
    if (hours > 0) {
        durationText = hours + " hour(s) " + minutes + " minute(s)";
    } else {
        durationText = minutes + " minute(s)";
    }
    
    // Store duration
    request.setAttribute("duration", durationText);

    // Check if preferred date is invalid
    LocalDate preferredDate = LocalDate.parse(preferredDateStr);
	LocalDate today = LocalDate.now();
	
	if (preferredDate.isBefore(today)) {
	    session.setAttribute("errorMessage", "Preferred date cannot be in the past.");
	
	    response.sendRedirect(
	        request.getContextPath() + "/bookService?serviceId=" + session.getAttribute("serviceId")
	    );
	    return;
	}

    // Fallbacks so the summary looks tidy even if params are missing
    serviceType = (serviceType == null || serviceType.isEmpty()) ? "—" : serviceType;
    startTime = (startTime == null || startTime.isEmpty()) ? "—" : startTime;
    endTime = (endTime == null || endTime.isEmpty()) ? "—" : endTime;
    durationText = (durationText == null || durationText.isEmpty()) ? "—" : durationText;
    additionalNotes = (additionalNotes == null || additionalNotes.isEmpty()) ? "—" : additionalNotes;
    if (amount == null || amount.isEmpty()) {
      amount = "0.00";
  	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Silvery Care - Payment</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="./paymentPage.css" rel="stylesheet">
</head>
<body>
    <header>
        <%@ include file="../../header/header.jsp" %>
    </header>
    
    <!-- Error messages -->
    <%
		String paymentError = (String) request.getAttribute("paymentError");
		if (paymentError != null) {
		%>
		<div class="alert alert-danger text-center">
		    <%= paymentError %>
		</div>
	<%
		}
	%>
    

    <section class="container mt-4">
        <div class="booking-header text-center mb-4">
            <h2>Secure Payment</h2>
            <p class="lead">Review your booking and enter your payment details below.</p>
        </div>

        <div class="row g-4">
            <!-- Summary Card -->
            <div class="col-lg-5">
                <div class="card shadow-sm p-3">
                    <h5 class="mb-3 text-center">Booking Summary</h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item d-flex justify-content-between">
                            <span>Service</span><strong><%= serviceType %></strong>
                        </li>
                        <li class="list-group-item d-flex justify-content-between">
                            <span>Date</span><strong><%= preferredDate %></strong>
                        </li>
                        <li class="list-group-item d-flex justify-content-between">
                            <span>Time</span><strong><%= startTime %> - <%= endTime%></strong>
                        </li>
                        <li class="list-group-item d-flex justify-content-between">
                            <span>Duration</span><strong><%= durationText %></strong>
                        </li>
                        <li class="list-group-item">
                            <div class="small text-muted mb-1">Notes</div>
                            <div><%= additionalNotes %></div>
                        </li>
                        <li class="list-group-item d-flex justify-content-between total-row">
                            <span>Total</span><strong>$<%= amount %></strong>
                        </li>
                    </ul>
                </div>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary w-100 mt-3">Back Home</a>
            </div>

            <!-- Payment Form -->
            <div class="col-lg-7">
                <div class="card shadow-sm p-4">
                    <form action="${pageContext.request.contextPath}/ValidatePayment" method="post" class="row g-3">
                        <!-- carry forward booking data to the next page -->
                        <input type="hidden" name="serviceType" value="<%= serviceType %>">
                        <input type="hidden" name="preferredDate" value="<%= preferredDate %>">
                        <input type="hidden" name="startTime" value="<%= startTime %>">
                        <input type="hidden" name="endTime" value="<%= endTime %>">
                        <input type="hidden" name="duration" value="<%= seconds %>">
                        <input type="hidden" name="additionalNotes" value="<%= additionalNotes %>">
    					<input type="hidden" name="servicePrice" value="${servicePrice}">
    					<input type="hidden" name="participants" value="<%= numOfParticipants %>">
    					<input type="hidden" name="outingLocation" value="<%= location %>">

                        <div class="col-12">
                            <label for="cardName" class="form-label">Name on Card</label>
                            <input type="text" class="form-control" id="cardName" name="cardName" placeholder="As printed on card" required>
                        </div>

                        <div class="col-12">
                            <label for="cardNumber" class="form-label">Card Number</label>
                            <input type="text" pattern="[0-9]{13,19}" inputmode="numeric" maxlength="19"
                                   class="form-control" id="cardNumber" name="cardNumber" placeholder="1234 5678 9012 3456" required>
                        </div>

                        <div class="col-md-6">
                            <label for="expiry" class="form-label">Expiry (MM/YY)</label>
                            <input type="text" pattern="[0-9]{2}/[0-9]{2}" class="form-control" id="expiry" name="expiry" placeholder="MM/YY" required>
                        </div>

                        <div class="col-md-6">
                            <label for="cvv" class="form-label">CVV</label>
                            <input type="password" pattern="[0-9]{3,4}" inputmode="numeric" maxlength="4"
                                   class="form-control" id="cvv" name="cvv" placeholder="3–4 digits" required>
                        </div>

                        <div class="col-12">
                            <label for="billingAddress" class="form-label">Billing Address</label>
                            <input type="text" class="form-control" id="billingAddress" name="billingAddress" placeholder="Street, Unit, Postal Code" required>
                        </div>

                        <div class="col-12 text-center">
                            <button type="submit" class="btn btn-primary px-5">Pay Now</button>
                        </div>

                        <p class="text-muted small mt-2 text-center mb-0">
                            This is a demo payment form. Do not enter real card details.
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <%@ include file="../../footer/footer.jsp" %>
    </footer>
</body>
</html>
