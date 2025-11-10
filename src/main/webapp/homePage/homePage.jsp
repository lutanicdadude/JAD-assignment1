<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Silvery Care - Home Page</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="./homePage.css" rel="stylesheet">
</head>
<body>
	<header>
		<%@ include file="../header/header.jsp"%>
	</header>
	<section class="container">
    	<div class="welcome-section">
      		<h2>Welcome back, <strong>Mrs. Tan</strong> 👋</h2>
      		<p class="lead mt-2">We’re glad to see you again! Here’s your care overview for today.</p>
    	</div>
  	</section>

  	<!-- Dashboard Overview -->
  	<section class="container mb-5">
   		<div class="row g-4">
	      	<!-- Card 1 -->
	      	<div class="col-md-4">
	        	<div class="card text-center h-100">
	          		<div class="card-body">
	            		<h5 class="card-title">Today's Care Visit</h5>
	            		<p class="card-text">Nurse Clara will visit at <strong>10:00 AM</strong> for your daily check-up.</p>
	            		<a href="#" class="btn btn-primary">View Details</a>
	          		</div>
	        	</div>
	      	</div>
	
	      	<!-- Card 2 -->
	      	<div class="col-md-4">
	        	<div class="card text-center h-100">
	          		<div class="card-body">
	            		<h5 class="card-title">Schedule Visit/Support</h5>
	            		<p class="card-text">Book a visit to meet your loved ones.</p>
	            		<a href="#" class="btn btn-primary">Book a Visit</a>
	          		</div>
	        	</div>
	      	</div>
	
	      	<!-- Card 3 -->
	      	<div class="col-md-4">
	        	<div class="card text-center h-100">
	          		<div class="card-body">
	            		<h5 class="card-title">Available care services</h5>
	            		<p class="card-text">View the different services we provide just for you.</p>
	            		<a href="#" class="btn btn-primary">View Services</a>
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
              				<li class="list-group-item">💊 Doctor Consultation – 9 Nov 2025, 3:00 PM</li>
              				<li class="list-group-item">🧘 Physiotherapy Session – 12 Nov 2025, 11:00 AM</li>
              				<li class="list-group-item">🩺 Nurse Home Visit – 14 Nov 2025, 9:30 AM</li>
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
		              <a href="#" class="btn btn-primary">Book a New Service</a>
		              <a href="#" class="btn btn-outline-success">Edit Profile</a>
		              <a href="#" class="btn btn-outline-success">Contact Support</a>
		            </div>
		          </div>
        		</div>
      		</div>
   		</div>
  </section>
  <footer>
    	<%@ include file="../footer/footer.jsp"%>
 	</footer>
</body>
</html>