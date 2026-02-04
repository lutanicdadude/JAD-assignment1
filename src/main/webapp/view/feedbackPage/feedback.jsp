<!-- Lucas Dong
P2429535
Jonah Tong
P2429564 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="model.Feedback" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Silvery Care - Feedback</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/view/feedbackPage/feedback.css" rel="stylesheet">
</head>
<body>
<header>
    <%@ include file="../../header/header.jsp" %>
</header>

<%
    // Prefer session user, but fall back to request attribute if needed
    User user = (User) session.getAttribute("user");
    if (user == null) {
        user = (User) request.getAttribute("user");
    }

    @SuppressWarnings("unchecked")
    List<Feedback> feedbackList = (List<Feedback>) request.getAttribute("feedbackList");

    String successMessage = (String) request.getAttribute("successMessage");
    String errorMessage   = (String) request.getAttribute("errorMessage");
%>

<!-- Welcome / Intro -->
<section class="container">
    <div class="welcome-section">
        <%
            if (user != null) {
        %>
            <h2>Hi <strong><%= user.getName() %></strong>, we value your feedback 💬</h2>
            <p class="lead mt-2">Share your thoughts with us and review what you’ve shared before.</p>
        <%
            } else {
        %>
            <h2>We value your feedback 💬</h2>
            <p class="lead mt-2">Share your thoughts with us and review what you’ve shared before.</p>
        <%
            }
        %>
    </div>
</section>

<!-- Main Content -->
<section class="container mb-5">
    <div class="row g-4">

        <!-- TOP: Feedback Form -->
        <div class="col-12">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title text-center mb-3">Leave Your Feedback</h5>

                    <%-- Optional success / error messages (from servlets) --%>
                    <%
                        if (successMessage != null) {
                    %>
                        <div class="alert alert-success"><%= successMessage %></div>
                    <%
                        }
                        if (errorMessage != null) {
                    %>
                        <div class="alert alert-danger"><%= errorMessage %></div>
                    <%
                        }
                    %>

                    <form action="${pageContext.request.contextPath}/SubmitFeedback" method="post">
                        <div class="mb-3">
                            <label class="form-label" for="fbTitle">Title</label>
                            <input type="text" class="form-control" id="fbTitle" name="title"
                                   placeholder="Short summary of your feedback" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="fbRating">Rating</label>
                            <select class="form-select" id="fbRating" name="rating" required>
                                <option value="" selected disabled>Choose a rating</option>
                                <option value="5">5 - Excellent</option>
                                <option value="4">4 - Good</option>
                                <option value="3">3 - Average</option>
                                <option value="2">2 - Poor</option>
                                <option value="1">1 - Very Poor</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="fbMessage">Your Feedback</label>
                            <textarea class="form-control" id="fbMessage" name="message"
                                      rows="4" placeholder="Tell us about your experience..." required></textarea>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Submit Feedback</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- BOTTOM: User's Feedback List -->
        <div class="col-12 mt-4">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title text-center mb-3">Your Previous Feedback</h5>

                    <ul class="list-group list-group-flush">
                        <%
                            if (feedbackList != null && !feedbackList.isEmpty()) {
                                for (Feedback fb : feedbackList) {
                        %>
                            <li class="list-group-item">
							    <div class="d-flex justify-content-between align-items-center">
							        <div>
							            <strong><%= fb.getTitle() %></strong><br>
							            <span class="text-muted small">
							                Feedback ID: <%= fb.getId() %>
							            </span>
							        </div>
							        <span class="badge bg-success">
							            Rating: <%= fb.getRating() %>/5
							        </span>
							    </div>
							
							    <p class="mt-2 mb-0"><%= fb.getMessage() %></p>
							
							    <!-- EDIT / DELETE ACTIONS -->
							    <div class="mt-3 d-flex justify-content-end gap-2">
							        <!-- Edit opens modal -->
							        <button type="button"
							                class="btn btn-sm btn-outline-primary"
							                data-bs-toggle="modal"
							                data-bs-target="#editFeedbackModal"
							                data-id="<%= fb.getId() %>"
							                data-title="<%= fb.getTitle() %>"
							                data-rating="<%= fb.getRating() %>"
							                data-message="<%= fb.getMessage() %>">
							            Edit
							        </button>
							
							        <!-- Delete posts to DeleteFeedback -->
							        <form action="${pageContext.request.contextPath}/DeleteFeedback"
							              method="post"
							              onsubmit="return confirm('Delete this feedback?');">
							            <input type="hidden" name="id" value="<%= fb.getId() %>">
							            <!-- tell servlet to come back to this page after delete -->
							            <input type="hidden" name="redirectTo" value="/Feedback">
							            <button type="submit" class="btn btn-sm btn-outline-danger">Delete</button>
							        </form>
							    </div>
							</li>

                        <%
                                }
                            } else {
                        %>
                            <li class="list-group-item">You have not submitted any feedback yet.</li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>

    </div>
</section>

			<!-- EDIT FEEDBACK MODAL (user side) -->
			<div class="modal fade" id="editFeedbackModal" tabindex="-1">
			  <div class="modal-dialog">
			    <form class="modal-content" action="${pageContext.request.contextPath}/EditFeedback" method="post">
			      <input type="hidden" id="edit-fb-id" name="id">
			      <!-- tell servlet to come back to this page after edit -->
			      <input type="hidden" name="redirectTo" value="/Feedback">
			
			      <div class="modal-header">
			        <h5 class="modal-title">Edit Feedback</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			      </div>
			
			      <div class="modal-body">
			        <div class="mb-3">
			          <label class="form-label" for="edit-fb-title">Title</label>
			          <!-- no name attribute: we are not updating title in DB, just showing it -->
			          <input type="text" class="form-control" id="edit-fb-title" readonly>
			        </div>
			
			        <div class="mb-3">
			          <label class="form-label" for="edit-fb-rating">Rating</label>
			          <select class="form-select" id="edit-fb-rating" name="rating" required>
			            <option value="5">5 - Excellent</option>
			            <option value="4">4 - Good</option>
			            <option value="3">3 - Average</option>
			            <option value="2">2 - Poor</option>
			            <option value="1">1 - Very Poor</option>
			          </select>
			        </div>
			
			        <div class="mb-3">
			          <label class="form-label" for="edit-fb-message">Your Feedback</label>
			          <textarea class="form-control" id="edit-fb-message" name="message" rows="4" required></textarea>
			        </div>
			      </div>
			
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
			        <button type="submit" class="btn btn-primary">Save Changes</button>
			      </div>
			    </form>
			  </div>
			</div>
			
			<script>
			  // Prefill Edit Feedback modal with data from the clicked "Edit" button
			  const editFeedbackModal = document.getElementById('editFeedbackModal');
			  editFeedbackModal.addEventListener('show.bs.modal', function (event) {
			      const button = event.relatedTarget;
			
			      document.getElementById('edit-fb-id').value      = button.getAttribute('data-id');
			      document.getElementById('edit-fb-title').value   = button.getAttribute('data-title');
			      document.getElementById('edit-fb-message').value = button.getAttribute('data-message');
			
			      const rating = button.getAttribute('data-rating');
			      document.getElementById('edit-fb-rating').value = rating;
			  });
			</script>


<!-- Back to Home Button -->
<section class="container text-center my-4">
    <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary px-4">
        Back to Home
    </a>
</section>


<footer>
    <%@ include file="../../footer/footer.jsp" %>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
</body>
</html>
