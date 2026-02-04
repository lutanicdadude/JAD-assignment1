<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate, java.time.LocalTime, java.time.Duration" %>
<%
    String serviceType = request.getParameter("serviceType");
    String preferredDateStr = request.getParameter("preferredDate");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String additionalNotes = request.getParameter("additionalNotes");
    String amount = request.getParameter("servicePrice");

    serviceType = (serviceType == null || serviceType.isEmpty()) ? "—" : serviceType;
    startTime = (startTime == null || startTime.isEmpty()) ? "—" : startTime;
    endTime = (endTime == null || endTime.isEmpty()) ? "—" : endTime;
    additionalNotes = (additionalNotes == null || additionalNotes.isEmpty()) ? "—" : additionalNotes;
    amount = (amount == null || amount.isEmpty()) ? "0.00" : amount;

    String durationText = "—";
    try {
        LocalTime fromTime = LocalTime.parse(startTime);
        LocalTime toTime = LocalTime.parse(endTime);
        Duration duration = Duration.between(fromTime, toTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        durationText = (hours > 0)
            ? hours + " hour(s) " + minutes + " minute(s)"
            : minutes + " minute(s)";
    } catch (Exception e) {}

    LocalDate preferredDate = LocalDate.now();
    try {
        preferredDate = LocalDate.parse(preferredDateStr);
    } catch (Exception e) {}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Silvery Care - Payment Successful</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- ✅ INLINE CSS -->
<style>
body {
  background-color: #f5f9fc;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #333;
}

header {
  background-color: #88b77b;
  color: white;
  padding: 1rem 0;
}

footer {
  background-color: #88b77b;
  color: white;
  text-align: center;
  padding: 1rem 0;
  margin-top: 3rem;
}

.card {
  border: none;
  border-radius: 1rem;
  background-color: #ffffff;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}

.total-row {
  font-size: 1.05rem;
  background: #f8fbf7;
  border-radius: 0.75rem;
}

.btn-success {
  background-color: #88b77b;
  border-color: #88b77b;
}

.btn-success:hover {
  background-color: #659c55;
  border-color: #659c55;
}

.btn-outline-secondary:hover {
  background-color: #cccccc;
  color: #333;
}
</style>
</head>

<body>

<header>
    <%@ include file="../../header/header.jsp" %>
</header>

<section class="container mt-5">
    <div class="text-center mb-5">
        <h2 class="text-success">Payment Successful!</h2>
        <p class="lead">Thank you for your booking. Your payment has been processed.</p>
    </div>

    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="card p-4">
                <h5 class="mb-3 text-center">Booking Summary</h5>

                <ul class="list-group list-group-flush">
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Service</span><strong><%= serviceType %></strong>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Date</span><strong><%= preferredDate %></strong>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Time</span><strong><%= startTime %> - <%= endTime %></strong>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Duration</span><strong><%= durationText %></strong>
                    </li>
                    <li class="list-group-item">
                        <small class="text-muted">Notes</small>
                        <div><%= additionalNotes %></div>
                    </li>
                    <li class="list-group-item d-flex justify-content-between total-row">
                        <span>Total Paid</span><strong>$<%= amount %></strong>
                    </li>
                </ul>

                <div class="text-center mt-4">
                    <a href="${pageContext.request.contextPath}/home"
                       class="btn btn-success px-5">Back to Home</a>
                    <a href="${pageContext.request.contextPath}/bookingHistory"
                       class="btn btn-outline-secondary px-4 ms-2">View My Bookings</a>
                </div>
            </div>
        </div>
    </div>
</section>

<footer>
    <%@ include file="../../footer/footer.jsp" %>
</footer>

</body>
</html>
