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
<title>Silvery Care - Landing Page</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="./landingPage.css" rel="stylesheet">
</head>
<body>
	<header>
		<%@ include file="../../header/headerWLoginBtn.jsp"%>
	</header>
	<section class="hero">
	    <h1>Compassionate Care for Every Elderly Heart</h1>
	    <p class="lead mt-3">Dedicated to improving the quality of life for seniors with professional and loving care.</p>
	    <a href="#services" class="btn btn-light mt-4">Explore Our Services</a>
  	</section>
	
  	<!-- Services Section -->
  	<section id="services" class="container my-5">
    	<h2 class="text-center mb-5">Our Services</h2>

   	<div class="row g-4">
      	<!-- Service 1 -->
      	<div class="col-md-4">
        	<div class="card text-center h-100">
	          	<div class="card-body">
		            <h5 class="card-title">Family service</h5>
		            <p class="card-text">Provides activities and support to bring families together.</p>
          		</div>	
        	</div>
      	</div>

      	<!-- Service 2 -->
      	<div class="col-md-4">
       	 	<div class="card text-center h-100">
       	  		<div class="card-body">
        	    	<h5 class="card-title">Personal Service</h5>
         	   		<p class="card-text">Help keep your health and comfort in check.</p>
         	 	</div>
       	 	</div>
     	 </div>

      	<!-- Service 3 -->
      	<div class="col-md-4">
        	<div class="card text-center h-100">
          	<div class="card-body">
            	<h5 class="card-title">Recreational Service</h5>
            	<p class="card-text">Bring joy and laughter to your loved ones.</p>
          	</div>
        	</div>
      	</div>
	</div>
  </section>
  <footer>
    	<%@ include file="../../footer/footer.jsp"%>
  	</footer>
</body>
</html>