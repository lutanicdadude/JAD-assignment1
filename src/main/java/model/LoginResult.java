package model;
//Author: Lucas Dong
//Class: DIT/2B/21
//Description: ST0510 Pract5 - Part 2 
public class LoginResult {
    private boolean success;
    private String message;
    private String user_type;

    public LoginResult(boolean success, String message, String user_type) {
        this.success = success;
        this.message = message;
        this.user_type = user_type;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
    
    public String getUser_type() {
      return user_type;
  }
}
