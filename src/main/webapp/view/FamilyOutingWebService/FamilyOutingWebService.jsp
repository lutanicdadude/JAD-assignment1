<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.FamilyOuting" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Family Outing Servicer</title>

<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Custom CSS -->

<link rel="stylesheet" href="${pageContext.request.contextPath}/view/FamilyOutingWebService/FamilyOutingCSS.css">

</head>

<body>

<!-- ===== SPECIAL HEADER (REPLACES INCLUDE) ===== -->
<header>
    <div class="header-container">

        <div class="left-section">
            <h2>Family Care Servicer</h2>
            <p>Providing family care to future generations</p>
        </div>

    </div>
</header>

<section class="container my-5">

    <!-- Page Header -->
    <div class="text-center mb-5">
        <h2 class="fw-bold">Family Outing Services</h2>
        <p class="text-muted">
            Review and confirm upcoming family outings for your loved ones.
        </p>
    </div>

    <!-- Error Message -->
    <%
        String error = (String) request.getAttribute("err");
        if ("NotFound".equals(error)) {
    %>
        <div class="alert alert-danger text-center">
            ❌ User not found!
        </div>
    <%
        }
    %>

    <!-- Action Button -->
    <div class="text-center mb-4">
        <form action="<%= request.getContextPath() %>/FamilyOuting">
            <button type="submit" class="btn btn-primary px-4">
                List All Family Outings
            </button>
        </form>
    </div>

    <%
        @SuppressWarnings("unchecked")
        ArrayList<FamilyOuting> foAL =
            (ArrayList<FamilyOuting>) request.getAttribute("familyOutingArray");

        if (foAL != null && !foAL.isEmpty()) {
    %>

    <form action="<%= request.getContextPath() %>/FamilyOutingSelection" method="post">

        <div class="row g-4">

        <%
            for (FamilyOuting fo : foAL) {
                boolean confirmed = "CONFIRMED".equalsIgnoreCase(fo.getStatus());
        %>

            <div class="col-md-6 col-lg-4">
                <div class="card outing-card h-100 position-relative p-3">

                    <span class="badge status-badge
                        <%= confirmed ? "bg-success" : "bg-warning text-dark" %>">
                        <%= confirmed ? "CONFIRMED" : "PENDING" %>
                    </span>

                    <div class="card-body">

                        <h5 class="card-title text-primary mb-3">
                            <%= fo.getServiceName() %>
                        </h5>

                        <div class="mb-2">
                            <div class="label-muted">Date</div>
                            <div><%= fo.getServiceDate() %></div>
                        </div>

                        <div class="mb-2">
                            <div class="label-muted">Time</div>
                            <div>
                                <%= fo.getServiceStart() %> – <%= fo.getServiceEnd() %>
                            </div>
                        </div>

                        <div class="mb-2">
                            <div class="label-muted">Duration</div>
                            <%
                                int duration = fo.getServiceDuration();
                                int hours = duration / 3600;
                                int minutes = (duration % 3600) / 60;
                            %>
                            <div><%= hours %>hr <%= minutes %>min</div>
                        </div>

                        <div class="mb-3">
                            <div class="label-muted">Additional Notes</div>
                            <div class="text-muted small">
                                <%= fo.getAdditionalNotes() %>
                            </div>
                        </div>

                        <button
                            type="button"
                            class="btn btn-outline-secondary w-100"
                            data-bs-toggle="modal"
                            data-bs-target="#outingModal"
                            data-outing-id="<%= fo.getId() %>">
                            Mark as confirmed
                        </button>

                    </div>
                </div>
            </div>

        <%
            }
        %>

        </div>

    </form>

    <%
        } else if (foAL != null) {
    %>
        <div class="alert alert-info text-center">
            No family outings available.
        </div>
    <%
        }
    %>

</section>

<!-- ================= MODAL ================= --> 
<div class="modal fade" id="outingModal" tabindex="-1" aria-hidden="true"> 
	<div class="modal-dialog modal-dialog-centered"> 
		<div class="modal-content"> 
			<form action="<%= request.getContextPath() %>/FamilyOutingStatus" method="post"> 
				<div class="modal-header"> 
					<h5 class="modal-title">
						Confirm customer order
					</h5> 
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button> 
				</div> 
				<div class="modal-body">
				 	<!-- Hidden ID --> 
					<input type="hidden" name="outingId" id="outingId"> 
					<p class="text-muted"> 
						Are you sure you want to update this outing status? 
					</p> 
				</div> 
				<div class="modal-footer"> 
					<button type="submit" name="action" value="DECLINE" class="btn btn-danger"> 
						Decline 
					</button>
					<button type="submit" name="action" value="CONFIRMED" class="btn btn-success"> 
						Confirm 
					</button> 
				</div> 
			</form> 
		</div> 
	</div> 
</div>

<!-- JS code to pass the id to the modal -->
<script>
document.addEventListener("DOMContentLoaded", function () {

    const outingModal = document.getElementById("outingModal");

    outingModal.addEventListener("show.bs.modal", function (event) {
        const button = event.relatedTarget;
        const outingId = button.getAttribute("data-outing-id");

        console.log("Passing outingId:", outingId); // DEBUG

        document.getElementById("outingId").value = outingId;
    });

});
</script>

<footer>
    <%@ include file="/footer/footer.jsp" %>
</footer>

</body>
</html>
