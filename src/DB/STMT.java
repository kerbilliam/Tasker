package DB;
import java.sql.*;

public class STMT {
    private Statement statement;

    private void createStatement(Connection connection) {
        try {
            if (connection != null) {
                statement = connection.createStatement();
                // System.out.println("Statement successfully created!");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception thrown when creating a Statement");
            System.err.println(e);
            System.exit(1);
        }
    }
    
    public void executeUpdate(String sqlStatement) {
        try {
            statement.executeUpdate(sqlStatement);
            // System.out.println("Statment executed successfully!");
        } catch (SQLException e) {
            System.err.println("SQL Exception thrown when executing provided Statement");
            System.err.println(e);
            System.exit(1);
        }
    }

    public void close() {
        try {
            statement.close();
            // System.out.println("Statement closed successfully!");
        } catch (SQLException e) {
            System.err.println("SQL Exception thrown when closing Statement");
            System.err.println(e);
            System.exit(1);
        }
    }

    public Statement getStatement() {
        return statement;
    }

    public STMT(Connection connection) {
        createStatement(connection);
    }
}
