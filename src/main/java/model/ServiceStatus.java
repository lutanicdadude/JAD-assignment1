package model;

public class ServiceStatus {
  private boolean success;
  private String message;

  public ServiceStatus(boolean success, String message) {
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