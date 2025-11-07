import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class connectDatabase {
  public static void main(String[] args) {
    connectDatabase.connect();
  }

  public static void connect() {
    // Connection con = null;
    try {
      Class.forName("org.postgresql.Driver");

      String url =
          "jdbc:postgresql://ep-young-lake-a1sz71ln-pooler.ap-southeast-1.aws.neon.tech/neondb";

      Properties props = new Properties();
      props.setProperty("user", "neondb_owner");
      props.setProperty("password", "Lutanic@dude245");
      props.setProperty("ssl", "false");
      Connection conn = DriverManager.getConnection(url, props);
      Statement stmt = conn.createStatement();
      // Step 5: Execute SQL Command
      String sqlStr = "SELECT * FROM admin";
      ResultSet rs = stmt.executeQuery(sqlStr);

      while (rs.next()) {

        String name = rs.getString("email");
        System.out.println("name:" + name);
      }
      rs.close();
      // Step 7: Close connection
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }
}
