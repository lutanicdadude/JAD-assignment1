package model;

public class VerifyUser {
    private boolean success;
    private String message;

    public VerifyUser(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
