package DB;
import java.sql.*;

public class Conn {
    private static final String dbURL = "jdbc:sqlite:database.db";
    private Connection connection = null;

    private void connect() {
        try {
            connection = DriverManager.getConnection(dbURL);
            // System.out.println("Connection established!");
        } catch (SQLException e) {
            System.err.println("SQL Exception thrown while attempting to connect to database.");
            System.err.println(e);
            System.exit(1);
        }
    }

    public void close() {
        try {
            connection.close();
            // System.out.println("Connection closed successfully!");
        } catch (SQLException e) {
            System.err.println("SQL Exception while closing connection!");
            System.err.println(e);
            System.exit(1);
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public Conn() {
        connect();
    }
}