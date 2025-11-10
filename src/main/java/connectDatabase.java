import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class connectDatabase {

    private static final String URL = "jdbc:postgresql://ep-young-lake-a1sz71ln-pooler.ap-southeast-1.aws.neon.tech/neondb";
    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "XiaozClan888!";

    static {
        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Returns a new database connection
    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        props.setProperty("ssl", "false");
        return DriverManager.getConnection(URL, props);
    }
}
