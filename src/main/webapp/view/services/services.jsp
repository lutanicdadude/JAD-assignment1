<!-- Lucas Dong
P2429535
Jonah Tong
P2429564 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Silvery Care – Services</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/view/services/services.css" rel="stylesheet">
</head>
<body>
  <header>
    <%@ include file="../../header/header.jsp" %>
  </header>

  <!-- Hero / Intro -->
  <section class="container mt-4">
    <div class="welcome-section text-center">
      <h2 class="mb-1">Our Care Services</h2>
      <p class="lead">Explore the categories below to see how we support you and your family.</p>
    </div>
  </section>

  <!-- Categories -->
  <section class="container my-4">
    <div class="row g-4">
      
      <!-- Family Services -->
      <div class="col-md-4">
        <div class="card h-100 shadow-sm">
          <div class="card-body d-flex flex-column">
            <h5 class="card-title">Family Services</h5>
            <p class="card-text">Stay connected and supported through family-focused programs.</p>
            <form action="${pageContext.request.contextPath}/RetrieveService" method="post" class="mt-auto">
			    <button type="submit" name="category" value="Family" class="btn btn-primary mt-auto w-100">
			        View Family Services
			    </button>
			</form>
          </div>
        </div>
      </div>

      <!-- Personal Care -->
      <div class="col-md-4">
        <div class="card h-100 shadow-sm">
          <div class="card-body d-flex flex-column">
            <h5 class="card-title">Personal Care</h5>
            <p class="card-text">Daily living support that preserves comfort and dignity.</p>
            <form action="${pageContext.request.contextPath}/RetrieveService" method="post" class="mt-auto">
			    <button type="submit" name="category" value="Personal" class="btn btn-primary mt-auto w-100">
			        View Personal Care
			    </button>
			</form>
          </div>
        </div>
      </div>

      <!-- Recreational -->
      <div class="col-md-4">
        <div class="card h-100 shadow-sm">
          <div class="card-body d-flex flex-column">
            <h5 class="card-title">Recreational & Wellbeing</h5>
            <p class="card-text">Joyful activities that enrich the mind, body, and spirit.</p>
            <form action="${pageContext.request.contextPath}/RetrieveService" method="post" class="mt-auto">
			    <button type="submit" name="category" value="Recreational" class="btn btn-primary mt-auto w-100">
			        View Recreational
			    </button>
			</form>
          </div>
        </div>
      </div>

    </div>
  </section>
  
	<section class="container text-center my-4">
	  <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary px-4 ms-2">Back to Home</a>
	</section>
	  

  <footer>
    <%@ include file="../../footer/footer.jsp" %>
  </footer>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
