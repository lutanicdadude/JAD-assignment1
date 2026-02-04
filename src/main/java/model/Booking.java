package model;

public class Booking {

    // bookings.id (used only for admin listing if you need it)
    private int id;

    // bookings.user_id
    private int userId;

    // booking_visit.id – this is the real visit ID used for update/delete
    private int bookingVisitId;

    // booking_visit.name
    private String name;

    // NEW: booking_visit.relationship
    private String relationship;

    // booking_visit.visit_date as text (e.g. "2025-11-27")
    private String visitDate;

    // booking_visit.visit_time as text (e.g. "10:00–11:00 AM")
    private String visitTime;

    // booking_visit.purpose_of_visit
    private String purposeOfVisit;

    // For admin UI; still keep it though we no longer show it in the table
    private String status;   // e.g. "Pending", "Approved", etc.

    // --- Existing constructor (used in user pages) ---
    // NOTE: relationship is not passed here, so we default it to null.
    public Booking(int user_id,
                   int bookingVisitId,
                   String name,
                   String visitDate,
                   String visitTime,
                   String purposeOfVisit) {
        this.userId = user_id;
        this.bookingVisitId = bookingVisitId;
        this.name = name;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.purposeOfVisit = purposeOfVisit;
        this.relationship = null;      // can be set later if needed
        this.status = "Pending";
    }

    // --- Existing constructor for admin listing (includes id + status) ---
    // Still compatible with your current DAO call.
    public Booking(int id,
                   int userId,
                   int bookingVisitId,
                   String name,
                   String visitDate,
                   String visitTime,
                   String purposeOfVisit,
                   String status) {
        this.id = id; // bookings.id
        this.userId = userId;
        this.bookingVisitId = bookingVisitId;
        this.name = name;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.purposeOfVisit = purposeOfVisit;
        this.relationship = null;      // can be filled later if you join relationship
        this.status = (status != null ? status : "Pending");
    }

    // OPTIONAL: if later you want to build Booking with relationship directly:
    public Booking(int id,
                   int userId,
                   int bookingVisitId,
                   String name,
                   String relationship,
                   String visitDate,
                   String visitTime,
                   String purposeOfVisit,
                   String status) {
        this.id = id;
        this.userId = userId;
        this.bookingVisitId = bookingVisitId;
        this.name = name;
        this.relationship = relationship;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.purposeOfVisit = purposeOfVisit;
        this.status = (status != null ? status : "Pending");
    }

    // --- Getters used in JSP and servlets ---

    /** For admin JSP we can still use getId() – this is bookings.id */
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    // Old naming kept so existing code still works
    public int getUser_id() {
        return userId;
    }

    /** booking_visit.id – used by updateBookingVisit/deleteBookingVisit */
    public int getBookingVisitId() {
        return bookingVisitId;
    }

    public String getName() {
        return name;
    }

    // For admin JSP: getCustomerName()
    public String getCustomerName() {
        return name;
    }

    /** NEW: relationship getter for admin bookings tab */
    public String getRelationship() {
        return relationship;
    }

    public String getVisitDate() {
        return visitDate;
    }

    // For admin JSP: getDate()
    public String getDate() {
        return visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    // For admin JSP: getTime()
    public String getTime() {
        return visitTime;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    // For older admin JSP where we treated purpose as "service"
    public String getServiceName() {
        return purposeOfVisit;
    }

    public String getStatus() {
        return status;
    }

    // --- Setters (optional, if you later mutate from servlets/DAOs) ---

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBookingVisitId(int bookingVisitId) {
        this.bookingVisitId = bookingVisitId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
