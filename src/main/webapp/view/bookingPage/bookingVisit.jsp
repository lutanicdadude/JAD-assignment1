<!-- Lucas Dong
P2429535
Jonah Tong
P2429564 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Silvery Care - Book a Visit</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/view/bookingPage/bookingVisit.css" rel="stylesheet">
</head>
<body>
<header>
    <%@ include file="../../header/header.jsp" %>
</header>

<section class="container mt-4">
    <div class="booking-header text-center mb-4">
        <h2>Book a Visit</h2>
        <p class="lead">Schedule a visit to see your loved one.</p>
    </div>

    <div class="card p-4 shadow-sm">
        <!-- Show success / error messages from servlet -->
        <%
            String successMessage = (String) request.getAttribute("successMessage");
            String errorMessage   = (String) request.getAttribute("errorMessage");
            if (successMessage != null) {
        %>
            <div class="alert alert-success"><%= successMessage %></div>
        <%
            }
            if (errorMessage != null) {
        %>
            <div class="alert alert-danger"><%= errorMessage %></div>
        <%
            }
        %>

        <!-- IMPORTANT: action points to servlet -->
        <form action="${pageContext.request.contextPath}/SubmitVisitBooking" method="post" class="row g-3">

            <div class="col-md-6">
                <label for="visitorName" class="form-label">Your Name</label>
                <input type="text" class="form-control" id="visitorName" name="name" required>
            </div>

            <div class="col-md-6">
                <label for="relationship" class="form-label">Relationship</label>
                <input type="text" class="form-control" id="relationship" name="relationship" required>
            </div>

            <div class="col-md-6">
                <label for="visitDate" class="form-label">Visit Date</label>
                <input type="date" class="form-control" id="visitDate" name="visitDate" required>
            </div>

            <div class="col-md-6">
                <label for="visitTime" class="form-label">Visit Time / Duration</label>
                <input type="text" class="form-control" id="visitTime" name="visitTime"
                       placeholder="e.g. 10:00–11:00 AM" required>
            </div>

            <div class="col-md-12">
                <label for="purpose" class="form-label">Purpose of Visit</label>
                <textarea class="form-control" id="purpose" name="purposeOfVisit" rows="3"
                          placeholder="e.g. Family visit, care discussion, etc." required></textarea>
            </div>

            <div class="col-12 text-center mt-3">
                <button type="submit" class="btn btn-primary px-4">Submit Booking</button>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary px-4 ms-2">
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
