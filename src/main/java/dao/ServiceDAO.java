package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ServiceResult;
import model.ServiceStatus;
import util.connectDatabase;
import model.Service;


  public class ServiceDAO {
    public List <ServiceResult> getService(int limit, int offset) {
      List<ServiceResult> service = new ArrayList<>();
      Connection conn = null;
      PreparedStatement ps = null;
  
      try {
          // Connect to DB
          conn = connectDatabase.getConnection();
  
          // Call stored procedure (OUT params version)
          ps = conn.prepareStatement("SELECT s.service_id, s.service_name, sc.category_name, s.description, s.price, s.duration_minutes, s.active FROM service s JOIN service_category sc ON s.category_id = sc.category_id ORDER BY s.service_id LIMIT ? OFFSET ?");
          ps.setInt(1,  limit);
          ps.setInt(2, offset);
          ResultSet rs = ps.executeQuery();

          while (rs.next()) {
            service.add (
                new ServiceResult(
                    rs.getString("service_id"),
                    rs.getString("service_name"),
                    rs.getString("category_name"),
                    rs.getString("description"),
                    rs.getString("price"),
                    rs.getString("duration_minutes"),
                    rs.getString("active")
                )
            );
          }
          
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          try { if (ps != null) ps.close(); } catch (Exception e) {}
          try { if (conn != null) conn.close(); } catch (Exception e) {}
      }
  
      return service;
    }
    
    public int countServices() {
      Connection conn = null;
      PreparedStatement ps = null;

      try {
        // Connect to DB
        conn = connectDatabase.getConnection();
        ps = conn.prepareStatement("SELECT COUNT(*) FROM service");
        ResultSet rs = ps.executeQuery();
           
        if (rs.next()) return rs.getInt(1);

      } catch (Exception e) {
          e.printStackTrace();
      }

      return 0;
    }

    public ServiceStatus addService(String name, String category, String description, double price, int duration, boolean active) {
      boolean status = false;
      String message = null;
      Connection conn = null;
      PreparedStatement ps = null;
  
      try {
          // Connect to DB
          conn = connectDatabase.getConnection();
  
          // Call stored procedure (OUT params version)
          ps = conn.prepareStatement("SELECT * FROM addservice(?, ?, ?, ?, ?, ?)");
          ps.setString(1, name);
          ps.setString(2, category);
          ps.setString(3, description);
          ps.setDouble(4, price);
          ps.setInt(5, duration);
          ps.setBoolean(6, active);
  
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
    
    public ServiceStatus editservice(int id, String name, String category, String description, double price, int duration, boolean active) {
      boolean status = false;
      String message = null;
      Connection conn = null;
      PreparedStatement ps = null;
  
      try {
          // Connect to DB
          conn = connectDatabase.getConnection();
  
          // Call stored procedure (OUT params version)
          ps = conn.prepareStatement("SELECT * FROM editservice(?, ?, ?, ?, ?, ?, ?)");
          ps.setInt(1, id);
          ps.setString(2, name);
          ps.setString(3, category);
          ps.setString(4, description);
          ps.setDouble(5, price);
          ps.setInt(6, duration);
          ps.setBoolean(7, active);
  
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
    
    public ServiceStatus deleteService(int id) {
      boolean status = false;
      String message = null;
      Connection conn = null;
      PreparedStatement ps = null;
  
      try {
          // Connect to DB
          conn = connectDatabase.getConnection();
  
          // Call stored procedure (OUT params version)
          ps = conn.prepareStatement("SELECT * FROM deleteservice(?);");
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
    
    public List<Service> getServicesByCategory(String cat_name) {
      List<Service> services = new ArrayList<>();

      try (Connection conn = connectDatabase.getConnection();
           PreparedStatement ps = conn.prepareStatement(
              "SELECT s.service_id, sc.category_name, s.service_name, s.description, s.price FROM service_category sc JOIN service s ON sc.category_id=s.category_id WHERE sc.category_name=?;"
           )) {

          ps.setString(1, cat_name);
          ResultSet rs = ps.executeQuery();

          while (rs.next()) {
              Service svc = new Service();
              svc.setServiceId(rs.getInt("service_id"));
              svc.setServiceName(rs.getString("service_name"));
              svc.setCategoryName(rs.getString("category_name"));
              svc.setDescription(rs.getString("description"));
              svc.setPrice(rs.getDouble("price"));

              services.add(svc);
          }

      } catch (Exception e) {
          e.printStackTrace();
      }

      return services;
    }

    public ServiceResult getServicesById(int service_id) {
      // check if service_id has a value
      if (service_id <= 0) {
        return null;
      }
      
      String id = null;
      String name = null;
      String price = null;
      String duration = null;
      Connection conn = null;
      PreparedStatement ps = null;

      try {
          conn = connectDatabase.getConnection();
          ps = conn.prepareStatement("SELECT * FROM service WHERE service_id=?;");

          ps.setInt(1, service_id);
          ResultSet rs = ps.executeQuery();

          if (!rs.next()) {
            return null;
          }
          id = rs.getString("service_id");
          name = rs.getString("service_name");
          price = rs.getString("price");
          duration = rs.getString("duration_minutes");

      } catch (Exception e) {
          e.printStackTrace();
      } finally {
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
      }
      
      return new ServiceResult(id, name, null, null, price, duration, null);
    }
    
    public ServiceStatus updateStatus(String status, int id) {
      // check if service_id has a value
      if (status.trim().isEmpty() || status == null) {
        return new ServiceStatus(false, "status is invalid");
      }

      Connection conn = null;
      PreparedStatement ps = null;

      try {
          conn = connectDatabase.getConnection();
          ps = conn.prepareStatement("UPDATE booking_service SET status = ? WHERE id=?");

          ps.setString(1, status);
          ps.setInt(2, id);
          int rowsAffected = ps.executeUpdate();

          if (rowsAffected == 0) {
            return new ServiceStatus(false, "No record found to update");
          }
          
          return new ServiceStatus(true, "successful update");

      } catch (Exception e) {
          e.printStackTrace();
          return new ServiceStatus(false, "Database error occurred");
      } finally {
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
      }
    }
}
