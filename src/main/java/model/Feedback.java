package model;

public class Feedback {

    private int id;             // feedback ID (PK)
    private int userId;         // which user wrote it
    private String title;       // short title / subject
    private String message;     // full feedback text (comments)
    private int rating;         // 1–5
    private String createdAt;   // not stored in DB (optional display)

    // Admin extras (not stored in DB)
    private String customerName;
    private boolean resolved;   // always false with current schema

    public Feedback() {
    }

    // Used for user-side lists
    public Feedback(int id, int userId, String title, String message, int rating, String createdAt) {
        this(id, userId, title, message, rating, createdAt, null, false);
    }

    // Full constructor used on admin side
    public Feedback(int id,
                    int userId,
                    String title,
                    String message,
                    int rating,
                    String createdAt,
                    String customerName,
                    boolean resolved) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.rating = rating;
        this.createdAt = createdAt;
        this.customerName = customerName;
        this.resolved = resolved;
    }

    // --- Getters & Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Admin JSP uses getMessage()
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // For admin JSP: isResolved() / getResolved()
    public boolean isResolved() {
        return resolved;
    }

    public boolean getResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", rating=" + rating +
                ", createdAt='" + createdAt + '\'' +
                ", customerName='" + customerName + '\'' +
                ", resolved=" + resolved +
                '}';
    }
}
