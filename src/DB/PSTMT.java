package DB;
import java.sql.*;

public class PSTMT {
    private PreparedStatement preparedStatement;
    
    private void createPreparedStatement(Connection connection, String sql) {
        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
                System.out.println("Prepared Statement created successfully!");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception when creating Preapared Statement");
            System.err.println(e);
            System.exit(1);
        }
    }

    public void setNrunStatement(String[] input) {
        try {
            int length = input.length;
            for (int i = 1; i <= length; i++) {
                preparedStatement.setString(i, input[i - 1]);
            }
            System.out.println("Prepared set successfully");
            preparedStatement.executeUpdate();
            System.out.println("Prepared statement executed successfully");
        } catch (SQLException e) {
            System.err.println("SQL Exception when setting or executing Preapared Statement");
            System.err.println(e);
            System.exit(1);
        }
    }

    public void setStatement(String[] input) {
        try {
            int length = input.length;
            for (int i = 1; i <= length; i++) {
                preparedStatement.setString(i, input[i - 1]);
            }
            System.out.println("Prepared set successfully");
        } catch (SQLException e) {
            System.err.println("SQL Exception when setting Preapared Statement");
            System.err.println(e);
            System.exit(1);
        }
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void close() {
        try {
            preparedStatement.close();
            System.out.println("Prepared Statement closed successfully");
        } catch (SQLException e) {
            System.err.println("SQL Exception when closing Prepared Statement");
            System.err.println(e);
            System.exit(1);
        }
    }
    
    public PSTMT(Connection connection, String sql) {
        createPreparedStatement(connection, sql);
    }
}
