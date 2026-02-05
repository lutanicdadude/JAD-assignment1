<!-- Lucas Dong
P2429535
Jonah Tong
P2429564 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Silvery Care - Book a Service</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/view/bookingPage/bookingService.css" rel="stylesheet">
</head>
<body>

<header>
    <%@ include file="../../header/header.jsp" %>
</header>

<%
    String serviceName = (String) request.getAttribute("serviceName");
    if (serviceName == null) {
        serviceName = "Selected Service";
    }

    // checks if retrieved service name has these words
    boolean isNutrition = serviceName.toLowerCase().contains("nutrition") || serviceName.toLowerCase().contains("meal");

    boolean isOuting = serviceName.toLowerCase().contains("outing");
%>

<section class="container mt-4">

    <div class="booking-header text-center mb-4">
        <h2>Book: <%= serviceName %></h2>
        <p class="lead">Please fill in the details for your booking.</p>
    </div>

    <%
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
        <script>
            alert("<%= errorMessage %>");
        </script>
    <%
        }
    %>

    <div class="card p-4 shadow-sm">
        <form action="${pageContext.request.contextPath}/view/payment/paymentPage.jsp"
              method="post"
              class="row g-4">

            <!-- Service Name -->
            <div class="col-md-6">
                <label class="form-label">Service Name</label>
                <input type="text"
                       class="form-control"
                       name="serviceName"
                       value="<%= serviceName %>"
                       readonly>
            </div>

            <!-- Preferred Date -->
            <div class="col-md-6">
                <label class="form-label">Preferred Date</label>
                <input type="date"
                       class="form-control"
                       name="preferredDate"
                       min="<%= LocalDate.now() %>"
                       required>
            </div>

            <!-- User Name -->
            <div class="col-md-6">
                <label class="form-label">Your Name</label>
                <input type="text"
                       class="form-control"
                       name="visitorName"
                       placeholder="Enter your full name"
                       required>
            </div>

            <!-- Time Range -->
            <div class="col-md-6">
                <label class="form-label">Service Time</label>
                <div class="row g-2">
                    <div class="col">
                        <input type="time"
                               name="startTime"
                               class="form-control"
                               required>
                    </div>
                    <div class="col">
                        <input type="time"
                               name="endTime"
                               class="form-control"
                               required>
                    </div>
                </div>
                <small class="text-muted">From → To</small>
            </div>

            <!-- Nutrition / Meal Prep Fields -->
            <% if (isNutrition) { %>
                <div class="col-md-6">
                    <label class="form-label">Dietary Preferences</label>
                    <input type="text"
                           class="form-control"
                           name="dietaryPreferences"
                           placeholder="e.g. Halal, Vegetarian, Low sugar">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Health Goals</label>
                    <input type="text"
                           class="form-control"
                           name="healthGoals"
                           placeholder="e.g. Weight loss, diabetes control">
                </div>
            <% } %>

            <!-- Family Outing Fields -->
            <% if (isOuting) { %>
                <div class="col-md-6">
                    <label class="form-label">Number of Participants</label>
                    <input type="number"
                           class="form-control"
                           name="participants"
                           min="1"
                           placeholder="Enter number of participants">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Outing Location</label>
                    <input type="text"
                           class="form-control"
                           name="outingLocation"
                           placeholder="e.g. Zoo, park, shopping mall">
                </div>
            <% } %>

            <!-- Additional Notes -->
            <div class="col-12">
                <label class="form-label">Additional Notes</label>
                <textarea class="form-control"
                          name="additionalNotes"
                          rows="3"
                          placeholder="Any special instructions (optional)"></textarea>
            </div>

            <!-- Buttons -->
            <div class="col-12 text-center">
                <button type="submit" class="btn btn-primary px-4">
                    Proceed to Payment
                </button>
                <a href="${pageContext.request.contextPath}/home"
                   class="btn btn-outline-secondary px-4 ms-2">
                   Back to Home
                </a>
            </div>

        </form>
    </div>

</section>

<footer>
    <%@ include file="../../footer/footer.jsp" %>
</footer>

</body>
</html>
