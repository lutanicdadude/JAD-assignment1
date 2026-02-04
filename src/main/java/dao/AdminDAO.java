package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.RegisterResult;
import util.connectDatabase;

public class AdminDAO {
  public int countAdmin() {
    Connection conn = null;
    PreparedStatement ps = null;

    try {
      // Connect to DB
      conn = connectDatabase.getConnection();
      ps = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE user_type='admin'");
      ResultSet rs = ps.executeQuery();
         
      if (rs.next()) return rs.getInt(1);

    } catch (Exception e) {
        e.printStackTrace();
    }

    return 0;
  }
  
  public RegisterResult registerAdmin(String email, String password1, String password2) {
    boolean status = false;
    String message = null;
    Connection conn = null;
    PreparedStatement ps = null;

    try {
        // Connect to DB
        conn = connectDatabase.getConnection();

        // Call stored procedure (OUT params version)
        ps = conn.prepareStatement("SELECT * FROM registeradmin(?, ?, ?)");
        ps.setString(1, email);
        ps.setString(2, password1);
        ps.setString(3, password2);

        ResultSet rs = ps.executeQuery();

        // retrieve results
        if (rs.next()) {
            status = rs.getBoolean("success");
            message = rs.getString("message");
            
            System.out.println("status: " + status); System.out.println("message: " + message);
             
        } else {
            System.out.println("No User is found.");
        }
        
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    return new RegisterResult(status, message);
    }
}