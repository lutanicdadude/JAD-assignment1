<!-- Lucas Dong
P2429535
Jonah Tong
P2429564 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.ServiceResult" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.Customer" %>

<%!
    /**
     * Helper to call a no-arg getter by name, e.g. "getId", "getCustomerName".
     * Returns null if anything fails.
     */
    private Object getProp(Object bean, String getterName) {
        if (bean == null || getterName == null) return null;
        try {
            java.lang.reflect.Method m = bean.getClass().getMethod(getterName);
            return m.invoke(bean);
        } catch (Exception e) {
            return null;
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Silvery Care – Admin Dashboard</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/view/admin/admin.css" rel="stylesheet">
</head>
<body>
<header>
  <%@ include file="../../header/header.jsp" %>
</header>

<%
    // --- KPIs for "Bookings (Open)" and "Feedback (Unread)" ---
    int bookingsOpen = 0;
    int feedbackUnread = 0;
    Object countsObj = request.getAttribute("counts");
    if (countsObj instanceof Map) {
        Map<?,?> counts = (Map<?,?>) countsObj;
        Object bo = counts.get("bookingsOpen");
        if (bo instanceof Number) bookingsOpen = ((Number) bo).intValue();
        Object fu = counts.get("feedbackUnread");
        if (fu instanceof Number) feedbackUnread = ((Number) fu).intValue();
    }

    // ===== Pagination variables per tab =====

    // Services
    Integer servicePageObj       = (Integer) request.getAttribute("currentPage");
    Integer serviceTotalPagesObj = (Integer) request.getAttribute("totalServicePages");
    int servicePage       = (servicePageObj != null) ? servicePageObj : 1;
    int serviceTotalPages = (serviceTotalPagesObj != null) ? serviceTotalPagesObj : 1;
    if (serviceTotalPages < 1) serviceTotalPages = 1;

    // Customers
    Integer customerPageObj       = (Integer) request.getAttribute("customerPage");
    Integer customerTotalPagesObj = (Integer) request.getAttribute("totalCustomerPages");
    int customerPage       = (customerPageObj != null) ? customerPageObj : 1;
    int customerTotalPages = (customerTotalPagesObj != null) ? customerTotalPagesObj : 1;
    if (customerTotalPages < 1) customerTotalPages = 1;

    // Bookings
    Integer bookingPageObj       = (Integer) request.getAttribute("bookingPage");
    Integer bookingTotalPagesObj = (Integer) request.getAttribute("totalBookingPages");
    int bookingPage       = (bookingPageObj != null) ? bookingPageObj : 1;
    int bookingTotalPages = (bookingTotalPagesObj != null) ? bookingTotalPagesObj : 1;
    if (bookingTotalPages < 1) bookingTotalPages = 1;

    // Feedback
    Integer feedbackPageObj       = (Integer) request.getAttribute("feedbackPage");
    Integer feedbackTotalPagesObj = (Integer) request.getAttribute("totalFeedbackPages");
    int feedbackPage       = (feedbackPageObj != null) ? feedbackPageObj : 1;
    int feedbackTotalPages = (feedbackTotalPagesObj != null) ? feedbackTotalPagesObj : 1;
    if (feedbackTotalPages < 1) feedbackTotalPages = 1;
%>

<section class="container mt-4">
  <div class="welcome-section text-center">
    <h2 class="mb-1">Admin Dashboard</h2>
    <p class="lead">Manage services, accounts, bookings, and feedback.</p>
  </div>
</section>

<section class="container my-4">
  <!-- Summary Cards -->
  <div class="row g-4 mb-3">
    <div class="col-md-3">
      <div class="card kpi">
        <div class="card-body">
          <div class="kpi-title">Services</div>
          <div class="kpi-value">
            <%
              Integer totalNoServices = (Integer) request.getAttribute("totalServiceRows");
              if (totalNoServices != null) {
            %>
                <h3><%= totalNoServices %></h3>
            <%
              } else {
            %>
                <h3>Null</h3>
            <%
              }
            %>
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-3">
      <div class="card kpi">
        <div class="card-body">
          <div class="kpi-title">Accounts</div>

          <div class="d-flex align-items-center justify-content-between">
            <div class="kpi-value mb-0">
              <%
                Integer totalNoAdmin = (Integer) request.getAttribute("adminCount");
                if (totalNoAdmin != null) {
              %>
                  <h3 class="m-0"><%= totalNoAdmin %></h3>
              <%
                } else {
              %>
                  <h3 class="m-0">Null</h3>
              <%
                }
              %>
            </div>

            <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#addAdminModal">
              Add
            </button>
          </div>

        </div>
      </div>
    </div>

    <div class="col-md-3">
      <div class="card kpi">
        <div class="card-body">
          <div class="kpi-title">Bookings (Open)</div>
          <div class="kpi-value">
            <h3><%= bookingsOpen %></h3>
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-3">
      <div class="card kpi">
        <div class="card-body">
          <div class="kpi-title">Feedback (Unread)</div>
          <div class="kpi-value">
            <h3><%= feedbackUnread %></h3>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Tabs -->
  <ul class="nav nav-tabs" id="adminTabs" role="tablist">
    <li class="nav-item" role="presentation">
      <button class="nav-link active" id="services-tab" data-bs-toggle="tab"
              data-bs-target="#services" type="button" role="tab">Services</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="customers-tab" data-bs-toggle="tab"
              data-bs-target="#customers" type="button" role="tab">Customers</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="bookings-tab" data-bs-toggle="tab"
              data-bs-target="#bookings" type="button" role="tab">Bookings</button>
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="feedback-tab" data-bs-toggle="tab"
              data-bs-target="#feedback" type="button" role="tab">Feedback</button>
    </li>
  </ul>

  <div class="tab-content bg-white p-3 rounded-bottom shadow-sm" id="adminTabsContent">

    <!-- SERVICES CRUD -->
    <div class="tab-pane fade show active" id="services" role="tabpanel">
      <div class="d-flex justify-content-between align-items-center mb-2">
        <h5 class="mb-0">Service Items</h5>
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createServiceModal">+ Create Service</button>
      </div>

      <div class="table-responsive">
        <table class="table align-middle">
          <thead>
            <tr>
              <th>ID</th><th>Name</th><th>Category</th><th>Description</th>
              <th>Price</th><th>Duration</th><th>Status</th><th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <%
                List<ServiceResult> result = (List<ServiceResult>) request.getAttribute("services");
                if (result != null) {
                    for (ServiceResult output : result) {
            %>
              <tr>
                <td><%= output.getid() %></td>
                <td><%= output.getname() %></td>
                <td><%= output.getcategory() %></td>
                <td><%= output.getdescription() %></td>
                <td><%= output.getprice() %></td>
                <td><%= output.getduration() %></td>
                <td>
                  <%
                    if ("t".equals(output.getstatus())) {
                  %>
                      <span style="color: green;">Active</span>
                  <%
                    } else {
                  %>
                      <span style="color: red;">Inactive</span>
                  <%
                    }
                  %>
                </td>
                <td class="d-flex gap-1">
                  <button class="btn btn-sm btn-outline-primary"
                          data-bs-toggle="modal"
                          data-bs-target="#editServiceModal"
                          data-id="<%= output.getid() %>"
                          data-name="<%= output.getname() %>"
                          data-category="<%= output.getcategory() %>"
                          data-description="<%= output.getdescription() %>"
                          data-price="<%= output.getprice() %>"
                          data-duration="<%= output.getduration() %>"
                          data-status="<%= output.getstatus() %>">
                    Edit
                  </button>

                  <form action="${pageContext.request.contextPath}/admin/DeleteService" method="post"
                        onsubmit="return confirm('Delete this service?')">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= output.getid() %>">
                    <button class="btn btn-sm btn-outline-danger">Delete</button>
                  </form>
                </td>
              </tr>
            <%
                    }
                } else {
            %>
              <tr>
                <td colspan="8">No services found.</td>
              </tr>
            <%
                }
            %>
          </tbody>
        </table>

        <!-- Pagination: Services -->
        <nav aria-label="Services page navigation">
          <ul class="pagination justify-content-center">
            <!-- Previous button -->
            <li class="page-item <%= (servicePage <= 1 ? "disabled" : "") %>">
              <a class="page-link" href="?page=<%= (servicePage > 1 ? servicePage - 1 : 1) %>">Previous</a>
            </li>

            <!-- Page numbers -->
            <%
              for (int i = 1; i <= serviceTotalPages; i++) {
            %>
              <li class="page-item <%= (servicePage == i ? "active" : "") %>">
                <a class="page-link" href="?page=<%= i %>"><%= i %></a>
              </li>
            <%
              }
            %>

            <!-- Next button -->
            <li class="page-item <%= (servicePage >= serviceTotalPages ? "disabled" : "") %>">
              <a class="page-link" href="?page=<%= (servicePage < serviceTotalPages ? servicePage + 1 : serviceTotalPages) %>">Next</a>
            </li>
          </ul>
        </nav>
      </div>
    </div>
    
    <!-- Customers CRUD -->
    <div class="tab-pane fade" id="customers" role="tabpanel">
      <div class="d-flex justify-content-between align-items-center mb-2">
        <h5 class="mb-0">Customer Data</h5>
      </div>

      <div class="table-responsive">
        <table class="table align-middle">
          <thead>
            <tr>
              <th>ID</th><th>Name</th><th>Email</th><th>Payment Method</th>
              <th>Last logged in</th><th>Created At</th><th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <%
                List<Customer> customerResult = (List<Customer>) request.getAttribute("customers");
                if (customerResult != null) {
                    for (Customer output : customerResult) {
            %>
              <tr>
                <td><%= output.getId() %></td>
                <td><%= output.getName() %></td>
                <td><%= output.getEmail() %></td>
                <td><%= output.getPaymentMethod() %></td>
                <td><%= output.getLastLoggedIn() %></td>
                <td><%= output.getCreatedAt() %></td>
             
                <td class="d-flex gap-1">
				  <!-- Edit Customer button (opens modal) -->
				  <button class="btn btn-sm btn-outline-primary"
				        data-bs-toggle="modal"
				        data-bs-target="#editCustomerModal"
				        data-id="<%= output.getId() %>"
				        data-name="<%= output.getName() %>"
				        data-email="<%= output.getEmail() %>"
				        data-payment="<%= output.getPaymentMethod() %>">
				  	Edit
				  </button>

				
				  <!-- Delete Customer -->
				  <form action="${pageContext.request.contextPath}/admin/DeleteUser" method="post"
				        onsubmit="return confirm('Delete this User?')">
				    <input type="hidden" name="action" value="delete">
				    <input type="hidden" name="id" value="<%= output.getId() %>">
				    <button class="btn btn-sm btn-outline-danger">Delete</button>
				  </form>
				</td>

              </tr>
            <%
                    }
                } else {
            %>
              <tr>
                <td colspan="8">No customers found.</td>
              </tr>
            <%
                }
            %>
          </tbody>
        </table>

        <!-- Pagination: Customers -->
        <nav aria-label="Customers page navigation">
          <ul class="pagination justify-content-center">
            <!-- Previous button -->
            <li class="page-item <%= (customerPage <= 1 ? "disabled" : "") %>">
              <a class="page-link" href="?customerPage=<%= (customerPage > 1 ? customerPage - 1 : 1) %>">Previous</a>
            </li>

            <!-- Page numbers -->
            <%
              for (int i = 1; i <= customerTotalPages; i++) {
            %>
              <li class="page-item <%= (customerPage == i ? "active" : "") %>">
                <a class="page-link" href="?customerPage=<%= i %>"><%= i %></a>
              </li>
            <%
              }
            %>

            <!-- Next button -->
            <li class="page-item <%= (customerPage >= customerTotalPages ? "disabled" : "") %>">
              <a class="page-link" href="?customerPage=<%= (customerPage < customerTotalPages ? customerPage + 1 : customerTotalPages) %>">Next</a>
            </li>
          </ul>
        </nav>
      </div>
    </div>

         <!-- BOOKINGS MGMT -->
    <div class="tab-pane fade" id="bookings" role="tabpanel">
      <div class="table-responsive">
        <table class="table align-middle">
          <thead>
            <tr>
              <th>ID</th>
              <th>Visitor Name</th>
              <th>Relationship</th>
              <th>Visit Date</th>
              <th>Visit Time / Duration</th>
              <th>Purpose of Visit</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <%
              List<?> bookings = (List<?>) request.getAttribute("bookings");
              if (bookings != null && !bookings.isEmpty()) {
                  for (Object b : bookings) {
                      Object id            = getProp(b, "getId");              // bookings.id
                      Object name          = getProp(b, "getName");            // visitor name
                      Object relationship  = getProp(b, "getRelationship");    // relationship
                      Object visitDate     = getProp(b, "getVisitDate");       // "YYYY-MM-DD"
                      Object visitTime     = getProp(b, "getVisitTime");       // time / duration
                      Object purpose       = getProp(b, "getPurposeOfVisit");  // purpose
            %>
            <tr>
              <td><%= id %></td>
              <td><%= name %></td>
              <td><%= relationship %></td>
              <td><%= visitDate %></td>
              <td><%= visitTime %></td>
              <td class="small"><%= purpose %></td>
              <td class="d-flex gap-1">
                <!-- EDIT: opens modal with same fields as bookingVisit.jsp -->
                <button class="btn btn-sm btn-outline-primary"
                        data-bs-toggle="modal"
                        data-bs-target="#editBookingModal"
                        data-id="<%= id %>"
                        data-name="<%= name %>"
                        data-relationship="<%= relationship %>"
                        data-visit-date="<%= visitDate %>"
                        data-visit-time="<%= visitTime %>"
                        data-purpose="<%= purpose %>">
                  Edit
                </button>

                <!-- DELETE: posts to DeleteBooking servlet -->
                <form action="${pageContext.request.contextPath}/admin/AdminDeleteBooking" method="post"
                      onsubmit="return confirm('Delete this booking?');">
                  <input type="hidden" name="id" value="<%= id %>">
                  <button class="btn btn-sm btn-outline-danger" type="submit">Delete</button>
                </form>
              </td>
            </tr>
            <%
                  }
              } else {
            %>
            <tr><td colspan="7">No bookings found.</td></tr>
            <%
              }
            %>
          </tbody>
        </table>
      </div>

      <!-- Pagination: Bookings -->
      <nav aria-label="Bookings page navigation">
        <ul class="pagination justify-content-center">
          <!-- Previous button -->
          <li class="page-item <%= (bookingPage <= 1 ? "disabled" : "") %>">
            <a class="page-link"
               href="?bookingPage=<%= (bookingPage > 1 ? bookingPage - 1 : 1) %>#bookings">
              Previous
            </a>
          </li>

          <!-- Page numbers -->
          <%
            for (int i = 1; i <= bookingTotalPages; i++) {
          %>
          <li class="page-item <%= (bookingPage == i ? "active" : "") %>">
            <a class="page-link" href="?bookingPage=<%= i %>#bookings"><%= i %></a>
          </li>
          <%
            }
          %>

          <!-- Next button -->
          <li class="page-item <%= (bookingPage >= bookingTotalPages ? "disabled" : "") %>">
            <a class="page-link"
               href="?bookingPage=<%= (bookingPage < bookingTotalPages ? bookingPage + 1 : bookingTotalPages) %>#bookings">
              Next
            </a>
          </li>
        </ul>
      </nav>
    </div>


    <!-- FEEDBACK MGMT -->
    <div class="tab-pane fade" id="feedback" role="tabpanel">
      <div class="d-flex justify-content-between align-items-center mb-2">
        <h5 class="mb-0">Customer Feedback</h5>
      </div>

      <div class="table-responsive">
        <table class="table align-middle">
          <thead>
            <tr>
              <th>ID</th><th>Customer</th><th>Message</th>
              <th>Rating</th><th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <%
              List<?> feedback = (List<?>) request.getAttribute("feedback");
              if (feedback != null && !feedback.isEmpty()) {
                  for (Object f : feedback) {
                      Object id        = getProp(f, "getId");
                      Object customer  = getProp(f, "getCustomerName");
                      Object message   = getProp(f, "getMessage");
                      Object rating    = getProp(f, "getRating");
            %>
              <tr>
                <td><%= id %></td>
                <td><%= customer %></td>
                <td class="small"><%= message %></td>
                <td><%= rating %>/5</td>
                <td class="d-flex gap-1">
				  <!-- Edit Feedback button (opens modal) -->
				  <button class="btn btn-sm btn-outline-primary"
				          data-bs-toggle="modal"
				          data-bs-target="#editFeedbackModal"
				          data-id="<%= id %>"
				          data-customer="<%= customer %>"
				          data-message="<%= message %>"
				          data-rating="<%= rating %>">
				    Edit
				  </button>
				
				  <!-- Delete Feedback -->
				  <form action="${pageContext.request.contextPath}/admin/AdminDeleteFeedback" method="post"
				        onsubmit="return confirm('Delete this feedback?')">
				    <input type="hidden" name="id" value="<%= id %>">
				    <button class="btn btn-sm btn-outline-danger">Delete</button>
				  </form>
				</td>

              </tr>
            <%
                  }
              } else {
            %>
              <tr><td colspan="7">No feedback found.</td></tr>
            <%
              }
            %>
          </tbody>
        </table>
      </div>

      <!-- Pagination: Feedback -->
      <nav aria-label="Feedback page navigation">
        <ul class="pagination justify-content-center">
          <!-- Previous button -->
          <li class="page-item <%= (feedbackPage <= 1 ? "disabled" : "") %>">
            <a class="page-link" href="?feedbackPage=<%= (feedbackPage > 1 ? feedbackPage - 1 : 1) %>">Previous</a>
          </li>

          <!-- Page numbers -->
          <%
            for (int i = 1; i <= feedbackTotalPages; i++) {
          %>
            <li class="page-item <%= (feedbackPage == i ? "active" : "") %>">
              <a class="page-link" href="?feedbackPage=<%= i %>"><%= i %></a>
            </li>
          <%
            }
          %>

          <!-- Next button -->
          <li class="page-item <%= (feedbackPage >= feedbackTotalPages ? "disabled" : "") %>">
            <a class="page-link" href="?feedbackPage=<%= (feedbackPage < feedbackTotalPages ? feedbackPage + 1 : feedbackTotalPages) %>">Next</a>
          </li>
        </ul>
      </nav>
    </div>

  </div>
</section>

<footer>
  <%@ include file="../../footer/footer.jsp" %>
</footer>

<!-- CREATE / EDIT MODALS -->
<!-- Create Service Modal -->
<div class="modal fade" id="createServiceModal" tabindex="-1">
  <div class="modal-dialog">
    <form class="modal-content" action="${pageContext.request.contextPath}/admin/AddService" method="post">
      <input type="hidden" name="action" value="create">
      <div class="modal-header">
        <h5 class="modal-title">Create Service</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <div class="mb-2"><label class="form-label">Name</label>
          <input class="form-control" name="name" required></div>
        <div class="mb-2"><label class="form-label">Category</label>
          <select class="form-select" name="category" required>
            <option value="Family">Family Service</option>
            <option value="Personal">Personal Service</option>
            <option value="Recreational">Recreational</option>
          </select></div>
        <div class="mb-2"><label class="form-label">Description</label>
          <input class="form-control" name="description"
                 placeholder="Description of service" required></div>
        <div class="mb-2"><label class="form-label">Price (SGD)</label>
          <input class="form-control" name="price" type="number"
                 step="0.01" min="0" required></div>
        <div class="mb-2"><label class="form-label">Duration</label>
          <input class="form-control" name="duration" type="number"
                 step="1" min="0" required></div>
        <div class="form-check">
          <input class="form-check-input" type="checkbox" name="active" checked id="svcActive">
          <label class="form-check-label" for="svcActive">Active</label>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Close</button>
        <button class="btn btn-primary" type="submit">Create</button>
      </div>

      <% if ("createServiceModal".equals(request.getAttribute("modalToOpen"))) { %>
	    <div class="alert alert-danger m-3">
	        <%= request.getAttribute("errorMessage") %>
	    </div>
	  <% } %>
    </form>
  </div>
</div>

<!-- Edit Service Modal-->
<div class="modal fade" id="editServiceModal" tabindex="-1">
  <div class="modal-dialog">
    <form class="modal-content" action="${pageContext.request.contextPath}/admin/EditService" method="post">
      <input type="hidden" id="edit-id" name="id">
      <input type="hidden" name="action" value="edit">
      <div class="modal-header">
        <h5 class="modal-title">Edit Service</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <div class="mb-2"><label class="form-label">Name</label>
          <input class="form-control" id="edit-name" name="name" required></div>
        <div class="mb-2"><label class="form-label">Category</label>
          <select class="form-select" id="edit-category" name="category" required>
            <option value="Family">Family Service</option>
            <option value="Personal">Personal Service</option>
            <option value="Recreational">Recreational</option>
          </select></div>
        <div class="mb-2"><label class="form-label">Description</label>
          <input class="form-control" id="edit-description" name="description"
                 placeholder="Description of service" required></div>
        <div class="mb-2"><label class="form-label">Price (SGD)</label>
          <input class="form-control" id="edit-price" name="price" type="number"
                 step="0.01" min="0" required></div>
        <div class="mb-2"><label class="form-label">Duration</label>
          <input class="form-control" id="edit-duration" name="duration"
                 type="number" step="1" min="0" required></div>
        <div class="form-check">
          <input class="form-check-input" type="checkbox" id="edit-active" name="active">
          <label class="form-check-label" for="svcActive">Active</label>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Close</button>
        <button class="btn btn-primary" type="submit">Save Changes</button>
      </div>
      <% if ("editServiceModal".equals(request.getAttribute("modalToOpen"))) { %>
	    <div class="alert alert-danger m-3">
	        <%= request.getAttribute("errorMessage") %>
	    </div>
	  <% } %>
    </form>
  </div>
</div>

<!-- Edit Customer Modal-->
<div class="modal fade" id="editCustomerModal" tabindex="-1">
  <div class="modal-dialog">
    <form class="modal-content" action="${pageContext.request.contextPath}/admin/EditCustomer" method="post">
      <input type="hidden" id="edit-customer-id" name="id">
      <div class="modal-header">
        <h5 class="modal-title">Edit Customer</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <div class="mb-2">
          <label class="form-label">Name</label>
          <input class="form-control" id="edit-customer-name" name="name" required>
        </div>

        <div class="mb-2">
          <label class="form-label">Email</label>
          <input class="form-control" id="edit-customer-email" name="email" type="email" required>
        </div>

        <div class="mb-2">
          <label class="form-label">Preferred Payment Method</label>
          <select class="form-select" id="edit-customer-payment" name="preferredPayment">
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
      <% if ("editCustomerModal".equals(request.getAttribute("modalToOpen"))) { %>
	    <div class="alert alert-danger m-3">
	        <%= request.getAttribute("errorMessage") %>
	    </div>
	  <% } %>
    </form>
  </div>
</div>


<!-- Edit Booking Modal -->
<div class="modal fade" id="editBookingModal" tabindex="-1">
  <div class="modal-dialog">
    <!-- POST to EditBooking servlet -->
    <form class="modal-content" action="${pageContext.request.contextPath}/admin/AdminEditBooking" method="post">
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
      <% if ("editBookingModal".equals(request.getAttribute("modalToOpen"))) { %>
    	<div class="alert alert-danger m-3">
	        <%= request.getAttribute("errorMessage") %>
	    </div>
	  <% } %>
    </form>
  </div>
</div>


<!-- Edit Feedback Modal -->
<div class="modal fade" id="editFeedbackModal" tabindex="-1">
  <div class="modal-dialog">
    <form class="modal-content" action="${pageContext.request.contextPath}/admin/AdminEditFeedback" method="post">
      <input type="hidden" id="edit-feedback-id" name="id">
      <div class="modal-header">
        <h5 class="modal-title">Edit Feedback</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <div class="mb-2"><label class="form-label">Customer</label>
          <input class="form-control" id="edit-feedback-customer" name="customerName" readonly></div>
        <div class="mb-2"><label class="form-label">Rating</label>
          <input class="form-control" id="edit-feedback-rating" name="rating" type="number" min="1" max="5"></div>
        <div class="mb-2"><label class="form-label">Message</label>
          <textarea class="form-control" id="edit-feedback-message" name="message" rows="3"></textarea></div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Close</button>
        <button class="btn btn-primary" type="submit">Save Changes</button>
      </div>
      <% if ("editFeedbackModal".equals(request.getAttribute("modalToOpen"))) { %>
	    <div class="alert alert-danger m-3">
	        <%= request.getAttribute("errorMessage") %>
	    </div>
	<% } %>
    </form>
  </div>
</div>

<!-- Add new admin Modal-->
<div class="modal fade" id="addAdminModal" tabindex="-1">
  <div class="modal-dialog">
    <form class="modal-content" action="${pageContext.request.contextPath}/admin/AddAdmin" method="post">
      <input type="hidden" name="action" value="create">
      <div class="modal-header">
        <h5 class="modal-title">Add new admin</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <div class="mb-2"><label class="form-label">Email</label>
          <input class="form-control" name="email" required></div>
        <div class="mb-2"><label class="form-label">Password</label>
          <input class="form-control" name="password" required></div>
        <div class="mb-2"><label class="form-label">Confirm Password</label>
          <input class="form-control" name="confirmPassword" required></div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Close</button>
        <button class="btn btn-primary" type="submit">Add new Admin</button>
      </div>

      <% if ("addAdminModal".equals(request.getAttribute("modalToOpen"))) { %>
	    <div class="alert alert-danger m-3">
	        <%= request.getAttribute("errorMessage") %>
	    </div>
	<% } %>
    </form>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
// Prefill Edit Service modal
document.getElementById('editServiceModal').addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget;

    document.getElementById('edit-id').value = button.getAttribute("data-id");
    document.getElementById('edit-name').value = button.getAttribute("data-name");
    document.getElementById('edit-category').value = button.getAttribute("data-category");
    document.getElementById('edit-description').value = button.getAttribute("data-description");
    document.getElementById('edit-price').value = button.getAttribute("data-price");
    document.getElementById('edit-duration').value = button.getAttribute("data-duration");

    let active = button.getAttribute("data-status") === "t";
    document.getElementById('edit-active').checked = active;
});

// Prefill Edit Customer modal
document.getElementById('editCustomerModal').addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget;

    document.getElementById('edit-customer-id').value = button.getAttribute("data-id");
    document.getElementById('edit-customer-name').value = button.getAttribute("data-name");
    document.getElementById('edit-customer-email').value = button.getAttribute("data-email");
    document.getElementById('edit-customer-payment').value = button.getAttribute("data-payment");
});

//Prefill Edit Booking modal with row data
document.getElementById('editBookingModal').addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget;

    document.getElementById('edit-booking-id').value           = button.getAttribute("data-id");
    document.getElementById('edit-booking-name').value         = button.getAttribute("data-name");
    document.getElementById('edit-booking-relationship').value = button.getAttribute("data-relationship");
    document.getElementById('edit-booking-date').value         = button.getAttribute("data-visit-date");
    document.getElementById('edit-booking-time').value         = button.getAttribute("data-visit-time");
    document.getElementById('edit-booking-purpose').value      = button.getAttribute("data-purpose");
});

// Prefill Edit Feedback modal
document.getElementById('editFeedbackModal').addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget;

    document.getElementById('edit-feedback-id').value = button.getAttribute("data-id");
    document.getElementById('edit-feedback-customer').value = button.getAttribute("data-customer");
    document.getElementById('edit-feedback-message').value = button.getAttribute("data-message");
    document.getElementById('edit-feedback-rating').value = button.getAttribute("data-rating");
});

// Auto-show Add Admin modal if there was an error
document.addEventListener("DOMContentLoaded", function () {
    var modalId = "<%= request.getAttribute("modalToOpen") %>";
    if (modalId && modalId !== "null") {
        var modalEl = document.getElementById(modalId);
        if (modalEl) {
            new bootstrap.Modal(modalEl).show();
        }
    }
});
</script>
<script>
// Prefill Edit Service modal
document.getElementById('editServiceModal').addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget;

    document.getElementById('edit-id').value = button.getAttribute("data-id");
    document.getElementById('edit-name').value = button.getAttribute("data-name");
    document.getElementById('edit-category').value = button.getAttribute("data-category");
    document.getElementById('edit-description').value = button.getAttribute("data-description");
    document.getElementById('edit-price').value = button.getAttribute("data-price");
    document.getElementById('edit-duration').value = button.getAttribute("data-duration");

    let active = button.getAttribute("data-status") === "t";
    document.getElementById('edit-active').checked = active;
});

</script>
</body>
</html>
