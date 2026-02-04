<!-- Lucas Dong
P2429535
Jonah Tong
P2429564 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="model.Service" %>
<%@ page import="java.util.List" %>
<html>
<head>
<meta charset="UTF-8" />
<title>Silvery Care – <%= request.getAttribute("pageTitle") %> Services</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/view/services/services.css" rel="stylesheet">
</head>
<body>
<header>
  <%@ include file="../../header/header.jsp" %>
</header>

<section class="container mt-4">
  <div class="welcome-section text-center">
    <h2 class="mb-1"><%= request.getAttribute("pageTitle") %> Services</h2>
    <p class="lead">Select a service that appeals to you.</p>
  </div>
</section>

<section class="container my-4">
  <div class="row g-4">
  	 <!-- dynamic service page -->
	 <%
	 	List<Service> services = (List<Service>) request.getAttribute("services");
       if (services != null) {
     %>
             <h3>Available Services:</h3>
	    <%
	        for (Service s : services) {
	    %>
	        <div class="col-md-4">
		      <div class="card h-100 shadow-sm">
		        <div class="card-body d-flex flex-column">
		          <h5 class="card-title"><%= s.getServiceName()%></h5>
		          <p class="card-text"><%= s.getDescription()%></p>
		          <p class="card-text">$<%= s.getPrice()%></p>
		          <a href="${pageContext.request.contextPath}/bookService?serviceId=<%= s.getServiceId()%>" class="btn btn-primary">Book Service</a>
		        </div>
		      </div>
		    </div>
	    <%
	        }
	    %>
	<%
	    } else {
	%>
	    <h3>No services available</h3>
	<%
	    }
	%>

  </div>
</section>

<section class="container text-center my-4">
  <a href="${pageContext.request.contextPath}/services" class="btn btn-outline-success">
    Back to Services
  </a>
</section>

<footer>
  <%@ include file="../../footer/footer.jsp" %>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
