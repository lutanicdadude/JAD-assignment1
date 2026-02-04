<!-- Lucas Dong
P2429535
Jonah Tong
P2429564 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="model.UpcomingAppointments" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Silvery Care - Home Page</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/view/homePage/homePage.css" rel="stylesheet">
</head>
<body>
    <header>
        <%@ include file="../../header/header.jsp"%>
    </header>

    <section class="container">
        <div class="welcome-section">
            <%
                User user = (User) session.getAttribute("user");
                if (user != null) {
            %>
                    <h2>Welcome back, <strong><%= user.getName() %></strong> 👋</h2>
                    <p class="lead mt-2">We’re glad to see you again! Here’s your care overview for today.</p>
            <%
                } else {
            %>
                    <h2>Welcome back!</h2>
                    <p class="lead mt-2">We’re glad to see you again!</p>
            <%
                }
            %>
        </div>
    </section>

    <section class="container mb-5">
        <div class="row g-4">
            <!-- Card 1 -->
            <div class="col-md-4">
                <div class="card text-center h-100">
                    <div class="card-body">
                        <h5 class="card-title">Today's Care Visit</h5>
                        <a href="<%= request.getContextPath() %>/ViewVisit" class="btn btn-primary">View Visit</a>
                    </div>
                </div>
            </div>

            <!-- Card 2 -->
            <div class="col-md-4">
                <div class="card text-center h-100">
                    <div class="card-body">
                        <h5 class="card-title">Schedule Visit/Support</h5>
                        <p class="card-text">Book a visit to meet your loved ones.</p>
                        <a href="<%= request.getContextPath() %>/bookVisit" class="btn btn-primary">Book a Visit</a>
                    </div>
                </div>
            </div>

            <!-- Card 3 -->
            <div class="col-md-4">
                <div class="card text-center h-100">
                    <div class="card-body">
                        <h5 class="card-title">Available care services</h5>
                        <p class="card-text">View the different services we provide just for you.</p>
                        <a href="<%= request.getContextPath() %>/services" class="btn btn-primary">View Services</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Additional Row -->
        <div class="row g-4 mt-3">
            <!-- Card 4 -->
            <div class="col-md-6">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title text-center">Upcoming Appointments</h5>
                        <ul class="list-group list-group-flush">
                            <%
                                @SuppressWarnings("unchecked")
                                List<UpcomingAppointments> bookings =
                                    (List<UpcomingAppointments>) request.getAttribute("bookings");

                                if (bookings != null && !bookings.isEmpty()) {
                                    for (UpcomingAppointments b : bookings) {
                            %>
                                        <li class="list-group-item">
                                            <%= b.getService_name() %> - <%= b.getScheduled_date() %>
                                        </li>
                            <%
                                    }
                                } else {
                            %>
                                        <li class="list-group-item">No upcoming appointments</li>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Card 5 -->
            <div class="col-md-6">
                <div class="card h-100">
                    <div class="card-body text-center">
                        <h5 class="card-title">Quick Actions</h5>
                        <p>Manage your account and services quickly from here:</p>
                        <div class="d-grid gap-2">
                            <a href="${pageContext.request.contextPath}/services" class="btn btn-primary">Book a New Service</a>
                            <a href="${pageContext.request.contextPath}/profile" class="btn btn-outline-success">Edit Profile</a>
                            <a href="${pageContext.request.contextPath}/feedback" class="btn btn-outline-success">Give Us Feedback!</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <%@ include file="../../footer/footer.jsp"%>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>
</body>
</html>
