package model;

public class VerifyPassword {
    private boolean success;
    private String message;

    public VerifyPassword(boolean success, String message) {
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
