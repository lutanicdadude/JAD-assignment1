package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Booking;
import util.connectDatabase;

public class BookingDAO {

    // VISIT
    // Insert a row into booking_visit and return the generated id.
    public int insertBookingVisit(String name,
                                  String relationship,
                                  String visitDate,      // "YYYY-MM-DD"
                                  String visitTime,
                                  String purposeOfVisit) {

        String sql = "INSERT INTO booking_visit " +
                     "(name, relationship, visit_date, visit_time, purpose_of_visit) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = connectDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, relationship);
            ps.setDate(3, java.sql.Date.valueOf(visitDate)); // expects YYYY-MM-DD
            ps.setString(4, visitTime);
            ps.setString(5, purposeOfVisit);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Link a booking_visit to a user in the bookings table.
    public boolean linkVisitBookingToUser(int userId, int bookingVisitId) {

        String sql = "INSERT INTO bookings (user_id, booking_service_id, booking_visit_id) " +
                     "VALUES (?, NULL, ?)";

        try (Connection conn = connectDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, bookingVisitId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get bookings for a single user (used in user pages)
	public List<Booking> getBooking(int user_id /* ,int limit, int offset */) {
	    List<Booking> booking = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	
	    try {
	        conn = connectDatabase.getConnection();
	
	        String sql =
	            "SELECT b.user_id, b.booking_visit_id, " +
	            "       bv.name, bv.relationship, bv.visit_date, bv.visit_time, bv.purpose_of_visit " +
	            "FROM bookings b " +
	            "JOIN booking_visit bv ON b.booking_visit_id = bv.id " +
	            "WHERE b.user_id = ?";
	
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, user_id);
	
	        rs = ps.executeQuery();
	
	        while (rs.next()) {
	            // use your existing constructor
	            Booking b = new Booking(
	                    rs.getInt("user_id"),
	                    rs.getInt("booking_visit_id"),
	                    rs.getString("name"),
	                    rs.getDate("visit_date").toString(),
	                    rs.getString("visit_time"),
	                    rs.getString("purpose_of_visit")
	            );
	
	            // now ALSO set relationship
	            b.setRelationship(rs.getString("relationship"));
	
	            booking.add(b);
	        }
	
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs != null) rs.close(); } catch (Exception e) {}
	        try { if (ps != null) ps.close(); } catch (Exception e) {}
	        try { if (conn != null) conn.close(); } catch (Exception e) {}
	    }
	
	    return booking;
	}


    // NEW: Get bookings for admin (paged)
	public List<Booking> getBookings(int limit, int offset) {
	    List<Booking> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	
	    try {
	        conn = connectDatabase.getConnection();
	
	        String sql =
	            "SELECT b.id, b.user_id, b.booking_visit_id, " +
	            "       bv.name, bv.relationship, bv.visit_date, bv.visit_time, bv.purpose_of_visit " +
	            "FROM bookings b " +
	            "JOIN booking_visit bv ON b.booking_visit_id = bv.id " +
	            "ORDER BY bv.visit_date DESC, bv.visit_time DESC " +
	            "LIMIT ? OFFSET ?";
	
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, limit);
	        ps.setInt(2, offset);
	
	        rs = ps.executeQuery();
	
	        while (rs.next()) {
	            int id             = rs.getInt("id");
	            int userId         = rs.getInt("user_id");
	            int bookingVisitId = rs.getInt("booking_visit_id");
	            String name        = rs.getString("name");
	            String relationship= rs.getString("relationship");   // 👈 NEW
	            String visitDate   = rs.getDate("visit_date").toString();
	            String visitTime   = rs.getString("visit_time");
	            String purpose     = rs.getString("purpose_of_visit");
	
	            // No status column yet, default to "Pending"
	            String status = "Pending";
	
	            // use the constructor that includes relationship
	            Booking b = new Booking(
	                    id,
	                    userId,
	                    bookingVisitId,
	                    name,
	                    relationship,
	                    visitDate,
	                    visitTime,
	                    purpose,
	                    status
	            );
	            list.add(b);
	        }
	
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs != null) rs.close(); } catch (Exception e) {}
	        try { if (ps != null) ps.close(); } catch (Exception e) {}
	        try { if (conn != null) conn.close(); } catch (Exception e) {}
	    }
	
	    return list;
	}

    // NEW: total bookings count (for pagination)
    public int countBookings() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS cnt FROM bookings";

        try (Connection conn = connectDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    // NEW: count of "open" bookings (for KPI)
    // For now treat all bookings as open; adjust WHERE clause if you add a status column.
    public int countOpenBookings() {
        return countBookings();
        /*
        // Example if you add a status column:
        String sql = "SELECT COUNT(*) AS cnt FROM bookings WHERE status IN ('Pending', 'Approved')";
        ...
        */
    }
    
	// UPDATE a booking_visit row by booking_visit ID
	public boolean updateBookingVisit(
	        int bookingVisitId,
	        String name,
	        String relationship,
	        String visitDate,
	        String visitTime,
	        String purposeOfVisit
	) {
	    String sql =
	        "UPDATE booking_visit " +
	        "SET name = ?, relationship = ?, visit_date = ?, visit_time = ?, purpose_of_visit = ? " +
	        "WHERE id = ?";
	
	    try (Connection conn = connectDatabase.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	
	        ps.setString(1, name);
	        ps.setString(2, relationship);
	
	        // visit_date is a DATE column
	        if (visitDate != null && !visitDate.isEmpty()) {
	            ps.setDate(3, java.sql.Date.valueOf(visitDate));  
	        } else {
	            ps.setNull(3, java.sql.Types.DATE);
	        }
	
	        ps.setString(4, visitTime);
	        ps.setString(5, purposeOfVisit);
	
	        ps.setInt(6, bookingVisitId);
	
	        int rows = ps.executeUpdate();
	        return rows > 0;
	
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


    // DELETE booking_visit row AND its linked entry in bookings
	public boolean deleteBookingVisit(int bookingVisitId) {
	
	    String deleteLink = "DELETE FROM bookings WHERE booking_visit_id = ?";
	    String deleteVisit = "DELETE FROM booking_visit WHERE id = ?";
	
	    try (Connection conn = connectDatabase.getConnection()) {
	
	        conn.setAutoCommit(false); // transaction
	
	        // Step 1: Remove link from bookings table
	        try (PreparedStatement ps1 = conn.prepareStatement(deleteLink)) {
	            ps1.setInt(1, bookingVisitId);
	            ps1.executeUpdate();
	        }
	
	        // Step 2: Delete from booking_visit table
	        int rows = 0;
	        try (PreparedStatement ps2 = conn.prepareStatement(deleteVisit)) {
	            ps2.setInt(1, bookingVisitId);
	            rows = ps2.executeUpdate();
	        }
	
	        conn.commit();
	        return rows > 0;
	
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	// SERVICE
	// Get Service name
	public String getServiceName(int service_id) {
	  String serviceName = null;
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
  
      try {
          conn = connectDatabase.getConnection();
  
          String sql = "SELECT service_name FROM service WHERE service_id = ?";
  
          ps = conn.prepareStatement(sql);
          ps.setInt(1, service_id);
  
          rs = ps.executeQuery();
  
          if (rs.next()) {
              serviceName = rs.getString("service_name");
          }
  
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          try { if (rs != null) rs.close(); } catch (Exception e) {}
          try { if (ps != null) ps.close(); } catch (Exception e) {}
          try { if (conn != null) conn.close(); } catch (Exception e) {}
      }
  
      return serviceName;
    }
}
