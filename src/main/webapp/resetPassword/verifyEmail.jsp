<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Silvery Care - Reset Password Page</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="./resetPassword.css" rel="stylesheet">
</head>
<body>
	<header>
		<%@ include file="../header/header.jsp"%>
	</header>
	<div class="reset-card">
	    <div class="card-title">Reset Your Password</div>
	    <p class="text-center text-muted mb-4">Enter your email address to receive password reset instructions.</p>
	    
	    <form action="/user/reset-password" method="POST">
	      <div class="mb-3">
	        <label for="email" class="form-label">Email Address</label>
	        <input type="email" id="email" name="email" class="form-control" placeholder="you@example.com" required>
	      </div>
	
	      <button type="submit" class="btn btn-primary w-100 mb-3">Verify Email</button>
	      <div class="text-center">
	        <a href="../loginPage/loginPage.jsp">Back to Login</a>
	      </div>
	    </form>
  	</div>
  	<footer>
    	<%@ include file="../footer/footer.jsp"%>
  	</footer>
</body>
</html>