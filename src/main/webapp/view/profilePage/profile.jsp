<!-- Lucas Dong
P2429535
Jonah Tong
P2429564 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Silvery Care - Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/view/profilePage/profile.css" rel="stylesheet">
</head>
<body>
<header>
    <%@ include file="../../header/header.jsp" %>
</header>

<%
    User user = (User) session.getAttribute("user");
    String preferredPaymentRaw = (String) session.getAttribute("preferredPayment");

    // Value that will be sent back to server ("Cash", "Online", or "")
    String preferredPaymentValue;
    if (preferredPaymentRaw == null || preferredPaymentRaw.trim().isEmpty()
            || "null".equalsIgnoreCase(preferredPaymentRaw)) {
        preferredPaymentValue = "";
    } else {
        preferredPaymentValue = preferredPaymentRaw.trim(); // should be "Cash" or "Online"
    }

    // Friendly text shown on the page
    String preferredPaymentDisplay;
    if (preferredPaymentValue.isEmpty()) {
        preferredPaymentDisplay = "Not set (add your preferred payment method)";
    } else {
        preferredPaymentDisplay = preferredPaymentValue;   // "Cash" or "Online"
    }
%>


<section class="container">
    <div class="welcome-section">
        <%
            if (user != null) {
        %>
            <h2>Your Profile, <strong><%= user.getName() %></strong> 😊</h2>
            <p class="lead mt-2">Review and update your personal details.</p>
        <%
            } else {
        %>
            <h2>Your Profile</h2>
            <p class="lead mt-2">We could not find your user details. Please log in again.</p>
        <%
            }
        %>
    </div>
</section>

<section class="container mb-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title text-center mb-3">Account Details</h5>

                    <%
                        if (user != null) {
                    %>
                    <div class="mb-3">
                        <label class="form-label fw-bold">User ID</label>
                        <div class="form-control-plaintext"><%= user.getId() %></div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Name</label>
                        <div class="form-control-plaintext"><%= user.getName() %></div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Email</label>
                        <div class="form-control-plaintext"><%= user.getEmail() %></div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Preferred Payment Method</label>
                        <div class="form-control-plaintext"><%= preferredPaymentDisplay %></div>
                    </div>

                    <div class="d-grid mt-4">
                        <!-- This button opens the modal and passes current values via data-* -->
					<button class="btn btn-primary"
					        data-bs-toggle="modal"
					        data-bs-target="#editProfileModal"
					        data-id="<%= user.getId() %>"
					        data-name="<%= user.getName() %>"
					        data-email="<%= user.getEmail() %>"
					        data-preferred-payment="<%= preferredPaymentValue %>">
					    Edit Profile
					</button>

                    <%
                        } else {
                    %>
                        <div class="alert alert-danger">
                            User details not found. Please log in again.
                        </div>
                    <%
                        }
                    %>

                </div>
            </div>
        </div>
    </div>
</section>

<!-- EDIT PROFILE MODAL -->
<div class="modal fade" id="editProfileModal" tabindex="-1">
  <div class="modal-dialog">
    <!-- Form posts to EditProfile servlet -->
    <form class="modal-content" action="${pageContext.request.contextPath}/EditProfile" method="post">
      <input type="hidden" id="edit-id" name="id">

      <div class="modal-header">
        <h5 class="modal-title">Edit Profile</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>

      <div class="modal-body">
        <div class="mb-2">
          <label class="form-label">Name</label>
          <input class="form-control" id="edit-name" name="name" required>
        </div>

        <div class="mb-2">
          <label class="form-label">Email</label>
          <input class="form-control" id="edit-email" name="email" type="email" required>
        </div>

        <div class="mb-2">
		  <label class="form-label">Preferred Payment Method</label>
		  <select class="form-select" id="edit-preferred-payment" name="preferredPayment">
		    <option value="">-- Select preferred payment --</option>
		    <option value="Cash">Cash</option>
		    <option value="Online">Online</option>
		  </select>
		</div>

      </div>

      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Close</button>
        <button class="btn btn-primary" type="submit">Save Changes</button>
      </div>

      <% if (request.getAttribute("profileError") != null) { %>
        <div class="alert alert-danger m-3">
          <%= request.getAttribute("profileError") %>
        </div>
      <% } %>
    </form>
  </div>
</div>

<div class="text-center mt-4">
    <a href="${pageContext.request.contextPath}/home"
       class="btn btn-outline-secondary px-4 ms-2 rounded-pill">
        Back to Home
    </a>
</div>


<footer>
    <%@ include file="../../footer/footer.jsp" %>
</footer>

		<script 
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous">
 		</script>

<script>
// Prefill Edit Profile modal 
document.getElementById('editProfileModal').addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget; // button that opened the modal

    let id = button.getAttribute("data-id");
    let name = button.getAttribute("data-name");
    let email = button.getAttribute("data-email");
    let preferredPayment = button.getAttribute("data-preferred-payment");

    document.getElementById('edit-id').value = id;
    document.getElementById('edit-name').value = name;
    document.getElementById('edit-email').value = email;
    document.getElementById('edit-preferred-payment').value = preferredPayment;
});
</script>
</body>
</html>
