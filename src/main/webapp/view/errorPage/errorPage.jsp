<!-- Lucas Dong
P2429535
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Silvery Care – ErrorPage</title>
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
      <h2 class="mb-1">There seems to be an error</h2>
      <p class="lead">Please return to the previous page.</p>
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
