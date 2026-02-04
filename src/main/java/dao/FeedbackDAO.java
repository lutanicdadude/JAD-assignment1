package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Feedback;
import util.connectDatabase;

public class FeedbackDAO {

    // INSERT a new feedback row
    public boolean insertFeedback(int userId, String title, int rating, String comments) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDatabase.getConnection();
            String sql = "INSERT INTO feedback (user_id, title, rating, comments) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setInt(3, rating);
            ps.setString(4, comments);

            int rows = ps.executeUpdate();
            success = (rows > 0);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return success;
    }

    // GET all feedback for a given user (client view)
    public List<Feedback> getFeedbackByUserId(int userId) {
        List<Feedback> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDatabase.getConnection();
            String sql =
                "SELECT id, user_id, title, comments, rating " +
                "FROM feedback " +
                "WHERE user_id = ? " +
                "ORDER BY id DESC";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("comments"),    // maps to message
                        rs.getInt("rating"),
                        ""                          // no created_at column in table
                );
                list.add(fb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return list;
    }

    // NEW: get ONE feedback by ID (used for permission check)
    public Feedback getFeedbackById(int id) {
        Feedback fb = null;
        String sql =
            "SELECT id, user_id, title, comments, rating " +
            "FROM feedback WHERE id = ?";

        try (Connection conn = connectDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fb = new Feedback(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("title"),
                            rs.getString("comments"),
                            rs.getInt("rating"),
                            ""  // createdAt (not stored)
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fb;
    }

    // Admin: get feedback list (paged)
    public List<Feedback> getFeedback(int limit, int offset) {
        List<Feedback> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDatabase.getConnection();
            String sql =
                "SELECT id, user_id, title, comments, rating " +
                "FROM feedback " +
                "ORDER BY id DESC " +
                "LIMIT ? OFFSET ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ps.setInt(2, offset);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String title = rs.getString("title");
                String comments = rs.getString("comments");
                int rating = rs.getInt("rating");

                String createdAt = "";            // not stored in DB
                String customerName = "User #" + userId;
                boolean resolved = false;         // no column; always treated as open

                Feedback fb = new Feedback(
                        id,
                        userId,
                        title,
                        comments,
                        rating,
                        createdAt,
                        customerName,
                        resolved
                );
                list.add(fb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return list;
    }

    // total feedback count (for pagination)
    public int countFeedback() {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDatabase.getConnection();
            String sql = "SELECT COUNT(*) AS cnt FROM feedback";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return count;
    }

    // count "unread" feedback for KPI – with current schema, treat all as unread
    public int countUnreadFeedback() {
        return countFeedback();
    }

    // ✅ Update feedback rating + message (matches current table: no 'resolved')
    public boolean updateFeedback(int id, int rating, String message) {
        String sql =
            "UPDATE feedback " +
            "SET rating = ?, comments = ? " +
            "WHERE id = ?";

        try (Connection conn = connectDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rating);
            ps.setString(2, message);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete feedback by ID
    public boolean deleteFeedback(int id) {
        String sql = "DELETE FROM feedback WHERE id = ?";

        try (Connection conn = connectDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
