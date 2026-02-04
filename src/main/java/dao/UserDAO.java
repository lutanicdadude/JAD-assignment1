package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
import model.UpcomingAppointments;
import model.Customer;
import model.LoginResult;
import model.VerifyUser;
import model.RegisterResult;
import model.ServiceStatus;
import util.connectDatabase;


  public class UserDAO {
      /**
       * Validates a user using a stored procedure named `login`.
       * Returns true if the email/password combination exists.
       */
    public LoginResult validateUser(String email, String password) {
      boolean status = false;
      String message = null;
      String user_type = null;
      Connection conn = null;
      PreparedStatement ps = null;
  
      try {
          // Connect to DB
          conn = connectDatabase.getConnection();
  
          // Call stored procedure (OUT params version)
          ps = conn.prepareStatement("SELECT * FROM login(?, ?)");
          ps.setString(1, email);
          ps.setString(2, password);
  
          ResultSet rs = ps.executeQuery();

          if (rs.next()) {
              status = rs.getBoolean("success");
              message = rs.getString("message");
              user_type = rs.getString("user_type");
              /*
               * System.out.println("status: " + status); System.out.println("message: " + message);
               */
          } else {
              System.out.println("No User is found.");
          }
          
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          try { if (ps != null) ps.close(); } catch (Exception e) {}
          try { if (conn != null) conn.close(); } catch (Exception e) {}
      }
  
      return new LoginResult(status, message, user_type);
    }
    
    public VerifyUser verify_email(String email) {
      boolean status = false;
      String message = null;
      Connection conn = null;
      PreparedStatement ps = null;
  
      try {
          // Connect to DB
          conn = connectDatabase.getConnection();
  
          // Call stored procedure (OUT params version)
          ps = conn.prepareStatement("SELECT * FROM verify_email(?)");
          ps.setString(1, email);
  
          ResultSet rs = ps.executeQuery();

          if (rs.next()) {
              status = rs.getBoolean("success");
              message = rs.getString("message");
              /*
               * System.out.println("status: " + status); System.out.println("message: " + message);
               */
          } else {
              System.out.println("No User is found.");
          }
          
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          try { if (ps != null) ps.close(); } catch (Exception e) {}
          try { if (conn != null) conn.close(); } catch (Exception e) {}
      }
  
      return new VerifyUser(status, message);
    }
    
    public VerifyUser verify_password(String email, String new_password, String confirmed_password) {
      boolean status = false;
      String message = null;
      Connection conn = null;
      PreparedStatement ps = null;
  
      try {
          // Connect to DB
          conn = connectDatabase.getConnection();
  
          // Call stored function (OUT params version)
          ps = conn.prepareStatement("SELECT * FROM change_password(?, ?, ?)");
          ps.setString(1, email);
          ps.setString(2, new_password);
          ps.setString(3, confirmed_password);
  
          ResultSet rs = ps.executeQuery();

          if (rs.next()) {
              status = rs.getBoolean("success");
              message = rs.getString("message");
              /*
               * System.out.println("status: " + status); System.out.println("message: " + message);
               */
          } else {
              System.out.println("No User is found.");
          }
          
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          try { if (ps != null) ps.close(); } catch (Exception e) {}
          try { if (conn != null) conn.close(); } catch (Exception e) {}
      }
  
      return new VerifyUser(status, message);
    }
    
    public User getUserByEmail(String email) {
      User user = null;
      Connection conn = null;
      PreparedStatement ps = null;
  
      try {
          // Connect to DB
          conn = connectDatabase.getConnection();
  
          // Call stored procedure (OUT params version)
          ps = conn.prepareStatement("SELECT user_id, email, name FROM users WHERE email = ?");
          ps.setString(1, email);
  
          ResultSet rs = ps.executeQuery();

          if (rs.next()) {
            user = new User(
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("email")
            );
          } else {
              System.out.println("No User is found.");
          }
          
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          try { if (ps != null) ps.close(); } catch (Exception e) {}
          try { if (conn != null) conn.close(); } catch (Exception e) {}
      }
  
      return user;
    }
    
    public String getPreferredPayment(int userId) {
        String preferredPayment = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDatabase.getConnection();

            // Get the preferred_payment_method column for this user
            ps = conn.prepareStatement(
                "SELECT preferred_payment_method FROM users WHERE user_id = ?"
            );
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                preferredPayment = rs.getString("preferred_payment_method");
                // This might be null if user hasn't set it yet – that's OK
            } else {
                System.out.println("No User is found for preferred payment lookup.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return preferredPayment;
    }
    
    //this class is a "model" that calls the sql statement to update the data of the user's profile.
    public boolean updateUserProfile(int userId, String name, String email, String preferredPayment) {
    Connection conn = null;
    PreparedStatement ps = null;
    boolean success = false;

    System.out.println("=== UserDAO.updateUserProfile ===");
    System.out.println("userId = " + userId);
    System.out.println("name = " + name);
    System.out.println("email = " + email);
    System.out.println("preferredPayment = " + preferredPayment);

    try {
        conn = connectDatabase.getConnection();

        String sql =
            "UPDATE users " +
            "SET name = ?, " +
            "    email = ?, " +
            "    preferred_payment_method = ?::payment_method_t " +  // 👈 CAST HERE
            "WHERE user_id = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);

        if (preferredPayment == null || preferredPayment.trim().isEmpty()) {
            // If your column allows NULL:
            ps.setNull(3, java.sql.Types.VARCHAR);
        } else {
            // Must be exactly "Cash" or "Online"
            ps.setString(3, preferredPayment);
        }

        ps.setInt(4, userId);

        int rows = ps.executeUpdate();
        System.out.println("Rows updated = " + rows);

        success = (rows > 0);

    } catch (Exception e) {
        System.out.println("Exception in updateUserProfile:");
        e.printStackTrace();
    } finally {
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    return success;
    }
    
    public List<UpcomingAppointments> getBookingByEmail(String email) {
        List<UpcomingAppointments> bookings = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDatabase.getConnection();

            ps = conn.prepareStatement(
                "SELECT s.service_name, bd.scheduled_date " +
                "FROM bookings b " +
                "JOIN users u ON b.user_id = u.user_id " +
                "JOIN booking_details bd ON b.booking_id = bd.booking_id " +
                "JOIN service s ON bd.service_id = s.service_id " +
                "WHERE u.user_type = 'customer' AND u.email = ?"
            );
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            // collect ALL rows, not just the first
            while (rs.next()) {
                bookings.add(new UpcomingAppointments(
                        rs.getString("service_name"),
                        rs.getString("scheduled_date")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return bookings;
    }

    public RegisterResult registerUser(String email, String password1, String password2) {
      boolean status = false;
      String message = null;
      Connection conn = null;
      PreparedStatement ps = null;
  
      try {
          // Connect to DB
          conn = connectDatabase.getConnection();
  
          // Call stored procedure (OUT params version)
          ps = conn.prepareStatement("SELECT * FROM register(?, ?, ?)");
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
    
    public List<Customer> getCustomerData(int limit, int offset) {
      List<Customer> customers = new ArrayList<>();
      Connection conn = null;
      PreparedStatement ps = null;

      try {
          conn = connectDatabase.getConnection();

          ps = conn.prepareStatement(
              "SELECT user_id, name, email, preferred_payment_method, last_login, created_at FROM users WHERE user_type='customer' ORDER BY user_id LIMIT ? OFFSET ?"
          );
          
          ps.setInt(1,  limit);
          ps.setInt(2, offset);

          ResultSet rs = ps.executeQuery();

          // collect ALL rows, not just the first
          while (rs.next()) {
            customers.add(new Customer(
                      rs.getInt("user_id"),
                      rs.getString("name"),
                      rs.getString("email"),
                      rs.getString("preferred_payment_method"),
                      rs.getString("last_login"),
                      rs.getString("created_at")
              ));
          }

      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          try { if (ps != null) ps.close(); } catch (Exception e) {}
          try { if (conn != null) conn.close(); } catch (Exception e) {}
      }

      return customers;
    }
    
    public ServiceStatus deleteUser(int id) {
      boolean status = false;
      String message = null;
      Connection conn = null;
      PreparedStatement ps = null;
  
      try {
          // Connect to DB
          conn = connectDatabase.getConnection();
  
          // Call stored procedure (OUT params version)
          ps = conn.prepareStatement("SELECT * FROM deleteuser(?);");
          ps.setInt(1, id);
  
          ResultSet rs = ps.executeQuery();

          if (rs.next()) {
            status = rs.getBoolean("success");
            message = rs.getString("message");
              /*
               * System.out.println("status: " + status); System.out.println("message: " + message);
               */
          } else {
              System.out.println("No User is found.");
          }
          
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          try { if (ps != null) ps.close(); } catch (Exception e) {}
          try { if (conn != null) conn.close(); } catch (Exception e) {}
      }
  
      return new ServiceStatus(status, message);
    }
    
    public int countUsers() {
      Connection conn = null;
      PreparedStatement ps = null;

      try {
        // Connect to DB
        conn = connectDatabase.getConnection();
        ps = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE user_type='customer'");
        ResultSet rs = ps.executeQuery();
           
        if (rs.next()) return rs.getInt(1);

      } catch (Exception e) {
          e.printStackTrace();
      }

      return 0;
    }
    
    public boolean updateCustomer(int userId, String name, String email, String preferredPayment) {
    String sql =
        "UPDATE users " +
        "SET name = ?, " +
        "    email = ?, " +
        "    preferred_payment_method = ?::payment_method_t " +  // CAST to enum
        "WHERE user_id = ?";

    try (Connection conn = connectDatabase.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setString(2, email);

        // preferredPayment is either "Cash", "Online", or null
        if (preferredPayment == null || preferredPayment.trim().isEmpty()) {
            ps.setNull(3, java.sql.Types.OTHER);   // enum/null
        } else {
            ps.setString(3, preferredPayment.trim()); // must match enum label exactly
        }

        ps.setInt(4, userId);

        int rows = ps.executeUpdate();
        return rows > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


}
