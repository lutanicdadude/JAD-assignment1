//package dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import model.FamilyOuting;
//import util.connectDatabase;
//
//public class FamilyOutingDAO {
//
//  // This method retrieves all "Family Outings" records from the database
//  public ArrayList<FamilyOuting> listAllFamilyOuting() throws SQLException {
//
//    // Create a list to store FamilyOuting objects retrieved from the DB
//    ArrayList<FamilyOuting> familyOutingList = new ArrayList<>();
//
//    // Bean object to temporarily hold each row of data
//    FamilyOuting uBean = null;
//
//    // Database connection object
//    Connection conn = null;
//
//    try {
//      // Get a database connection from DBConnection class
//      conn = connectDatabase.getConnection();
//
//      // Only rows where service_name is 'Family Outings' will be returned
//      String sqlStr =
//          "SELECT id, service_name, service_date, additional_notes, " +
//          "service_duration, service_start, service_end, status " +
//          "FROM booking_service WHERE service_name = 'Family Outings'";
//
//      // Prepare the SQL statement for execution
//      PreparedStatement pstmt = conn.prepareStatement(sqlStr);
//
//      // Execute the query and store the result in ResultSet
//      ResultSet rs = pstmt.executeQuery();
//
//      // Loop through each row returned by the query
//      while (rs.next()) {
//
//        // Create a new FamilyOuting object for the current row
//        uBean = new FamilyOuting();
//
//        // Read data from ResultSet and set it into the bean
//        System.out.println("id from db: "+rs.getInt("id"));
//        uBean.setId(rs.getInt("id"));
//        uBean.setServiceName(rs.getString("service_name"));
//        uBean.setServiceDate(rs.getString("service_date"));
//        uBean.setAdditionalNotes(rs.getString("additional_notes"));
//        uBean.setServiceDuration(rs.getInt("service_duration"));
//        uBean.setServiceStart(rs.getString("service_start"));
//        uBean.setServiceEnd(rs.getString("service_end"));
//        uBean.setStatus(rs.getString("status"));
//
//        // Debug message to confirm data is written into the bean
//        System.out.println(".....done writing to bean!.....");
//
//        // Add the populated bean to the list
//        familyOutingList.add(uBean);
//      }
//
//    } catch (Exception e) {
//      // Catch and print any exception that occurs during DB operations
//      System.out.print("..........UserDetailsDB:" + e);
//
//    } finally {
//      // Close the connection to prevent resource leaks
//      // Check for null in case connection failed
//      if (conn != null) {
//        conn.close();
//      }
//    }
//
//    // Return the list of FamilyOuting objects
//    return familyOutingList;
//  }
//}
//
