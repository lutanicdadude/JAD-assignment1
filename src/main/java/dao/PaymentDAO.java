package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import util.connectDatabase;

public class PaymentDAO {

  public boolean insertPayment(String email, String serviceName, String serviceDate, String serviceStart, String serviceEnd, double duration, String additionalNotes, int numOfParticipants, String location) {

      String sql = """
          INSERT INTO booking_service
          (email, service_name, service_date, service_start, service_end, service_duration, additional_notes, status, numOfParticipants, location)
          VALUES (?, ?, ?, ?, ?, ?, ?, 'pending', ?, ?)
      """;

      try (Connection conn = connectDatabase.getConnection();
           PreparedStatement ps = conn.prepareStatement(sql)) {
        
        /*
         * System.out.println("email: " + email); System.out.println("serviceName: " + serviceName);
         * System.out.println("serviceDate: " + serviceDate); System.out.println("serviceStart: " +
         * serviceStart); System.out.println("serviceEnd: " + serviceEnd);
         * System.out.println("duration: " + duration); System.out.println("additionalNotes: " +
         * additionalNotes);
         */

          ps.setString(1, email);
          ps.setString(2, serviceName);
          ps.setString(3, serviceDate);
          ps.setString(4, serviceStart);
          ps.setString(5, serviceEnd);
          ps.setDouble(6, duration);
          ps.setString(7, additionalNotes);
          ps.setInt(8, numOfParticipants);
          ps.setString(9, location);

          return ps.executeUpdate() > 0; // true if inserted

      } catch (Exception e) {
          e.printStackTrace();
      }

      return false;
  }
 
}
