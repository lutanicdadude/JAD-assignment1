<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Silvery Care - Register</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="./registerPageCss.css" rel="stylesheet">
</head>
<body>
	<header>
		<%@ include file="../header/header.jsp"%>
	</header>
	
	<!-- Login Form -->
	<main>
		<div class="login-card">
	      <h3>Register</h3>
	      <form>
	        <div class="mb-3">
	          <input id="registerEmail" type="email" class="form-control" placeholder="Email address" required>
	        </div>
	        <div class="mb-3">
	          <input id="inputPassword1" type="password" class="form-control" placeholder="Password" required>
	        </div>
	        <div class="mb-3">
	          <input id="inputPassword2" type="password" class="form-control" placeholder="Confirm Password" required>
	        </div>
	        <button type="submit" class="btn btn-login mt-2">Register</button>
	      </form>
	      <div class="text-center mt-3">
	        <small>Already have an account? <a href="../loginPage/loginPage.jsp" class="text-success">Login</a></small>
	      </div>
	    </div>
	</main>
	
	<footer>
    	<%@ include file="../footer/footer.jsp"%>
  	</footer>
</body>
</html>