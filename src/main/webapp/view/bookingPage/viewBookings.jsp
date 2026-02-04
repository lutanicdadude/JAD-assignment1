<!-- Lucas Dong
P2429535
Jonah Tong
P2429564 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Booking" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Silvery Care – View Bookings</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/view/services/services.css" rel="stylesheet">
</head>
<body>
<header>
  <%@ include file="../../header/header.jsp" %>
</header>

<section class="container mt-4">
  <div class="welcome-section text-center">
    <h2 class="mb-1">View Your Bookings</h2>
  </div>
</section>

<section class="container my-4">
  <div class="row g-4">
    <%
      List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
      if (bookings != null && !bookings.isEmpty()) {
    %>
      <h3>Your Bookings:</h3>
      <%
        for (Booking b : bookings) {
          int visitId = b.getBookingVisitId();   // booking_visit.id used by Edit/Delete servlets
      %>
        <div class="col-md-4">
          <div class="card h-100 shadow-sm">
            <div class="card-body d-flex flex-column">
              <h5 class="card-title"><%= b.getPurposeOfVisit() %></h5>
              <p class="card-text mb-1"><strong>Date:</strong> <%= b.getVisitDate() %></p>
              <p class="card-text mb-1"><strong>Time:</strong> <%= b.getVisitTime() %></p>
              <!-- NEW: show relationship -->
              <p class="card-text mb-3"><strong>Relationship:</strong> <%= b.getRelationship() %></p>

              <div class="d-flex gap-2 mt-auto">
                <!-- EDIT opens modal (same style as admin page) -->
                <button type="button"
                        class="btn btn-primary"
                        data-bs-toggle="modal"
                        data-bs-target="#editBookingModal"
                        data-id="<%= visitId %>"
                        data-name="<%= b.getName() %>"
                        data-relationship="<%= b.getRelationship() %>"
                        data-visit-date="<%= b.getVisitDate() %>"
                        data-visit-time="<%= b.getVisitTime() %>"
                        data-purpose="<%= b.getPurposeOfVisit() %>">
                  Edit
                </button>

                <!-- DELETE posts directly to DeleteBooking servlet -->
                <form action="${pageContext.request.contextPath}/DeleteBooking" method="post"
                      onsubmit="return confirm('Delete this booking?');">
                  <input type="hidden" name="id" value="<%= visitId %>">
                  <button class="btn btn-danger" type="submit">Delete</button>
                </form>
              </div>
            </div>
          </div>
        </div>
      <%
        }
      } else {
      %>
        <h3>No bookings available</h3>
      <%
      }
      %>
  </div>
</section>

<section class="container text-center my-4">
  <a href="${pageContext.request.contextPath}/RetrieveUserName" class="btn btn-outline-success">
    Back to Home
  </a>
</section>

<footer>
  <%@ include file="../../footer/footer.jsp" %>
</footer>

<!-- EDIT BOOKING MODAL (user side, same fields as admin) -->
<div class="modal fade" id="editBookingModal" tabindex="-1">
  <div class="modal-dialog">
    <form class="modal-content" action="${pageContext.request.contextPath}/EditBooking" method="post">
      <input type="hidden" id="edit-booking-id" name="id">

      <div class="modal-header">
        <h5 class="modal-title">Edit Booking</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>

      <div class="modal-body">
        <div class="mb-3">
          <label for="edit-booking-name" class="form-label">Your Name</label>
          <input type="text" class="form-control" id="edit-booking-name" name="name" required>
        </div>

        <div class="mb-3">
          <label for="edit-booking-relationship" class="form-label">Relationship</label>
          <input type="text" class="form-control" id="edit-booking-relationship" name="relationship" required>
        </div>

        <div class="mb-3">
          <label for="edit-booking-date" class="form-label">Visit Date</label>
          <input type="date" class="form-control" id="edit-booking-date" name="visitDate" required>
        </div>

        <div class="mb-3">
          <label for="edit-booking-time" class="form-label">Visit Time / Duration</label>
          <input type="text" class="form-control" id="edit-booking-time" name="visitTime"
                 placeholder="e.g. 10:00–11:00 AM" required>
        </div>

        <div class="mb-3">
          <label for="edit-booking-purpose" class="form-label">Purpose of Visit</label>
          <textarea class="form-control" id="edit-booking-purpose" name="purposeOfVisit" rows="3"
                    placeholder="e.g. Family visit, care discussion, etc." required></textarea>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Close</button>
        <button class="btn btn-primary" type="submit">Save Changes</button>
      </div>
    </form>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
// Prefill Edit Booking modal (same pattern as admin page)
document.getElementById('editBookingModal').addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget;

    document.getElementById('edit-booking-id').value           = button.getAttribute("data-id");
    document.getElementById('edit-booking-name').value         = button.getAttribute("data-name");
    document.getElementById('edit-booking-relationship').value = button.getAttribute("data-relationship");
    document.getElementById('edit-booking-date').value         = button.getAttribute("data-visit-date");
    document.getElementById('edit-booking-time').value         = button.getAttribute("data-visit-time");
    document.getElementById('edit-booking-purpose').value      = button.getAttribute("data-purpose");
});
</script>
</body>
</html>
