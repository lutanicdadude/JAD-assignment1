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
<title>Silvery Care - login</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/view/loginPage/loginPageCss.css" rel="stylesheet">
</head>
<body>
	<header>
		<%@ include file="../../header/header.jsp"%>
	</header>
	
	<!-- Login Form -->
	<main>
		<div class="login-card">
	      <h3>Member Login</h3>
	      <!-- Form to pass data to back-end validation -->
	      <form action="${pageContext.request.contextPath}/LoginProcess" method="post">
	        <div class="mb-3">
	          <input id="loginEmail" name="email" type="email" class="form-control" placeholder="Email address" required>
	        </div>
	        <div class="mb-3">
	          <input id="loginPassword" name="password" type="password" class="form-control" placeholder="Password" required>
	        </div>
	        <button type="submit" class="btn btn-login mt-2">Login</button>
	        <!-- retrieve message from servlet -->
	        <% if (request.getAttribute("errorMessage") != null) { %>
	        	<!-- display error message -->
			    <div class="alert alert-danger">
			        <%= request.getAttribute("errorMessage") %>
			    </div>
			<% } %>
	      </form>
	      <div class="text-center mt-3">
	        <a href="../resetPassword/verifyEmail.jsp" class="text-success">Forgot password?</a><br>
	        <small>New here? <a href="../register/registerPage.jsp" class="text-success">Create an account</a></small>
	      </div>
	    </div>
	</main>
	
	<footer>
    	<%@ include file="../../footer/footer.jsp"%>
  	</footer>
</body>
</html>