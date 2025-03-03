package DB;
import java.sql.*;

public class RS {
    private ResultSet resultSet;

    private void createResultSet(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
             resultSet = preparedStatement.executeQuery();
            // System.out.println("ResultSet Query executed successfully!");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception when creating ResultSet or executing Query");
            System.err.println(e);
            System.exit(1);
        }
    }

    private void createResultSet(Statement statement, String sql) {
        try {
            resultSet = statement.executeQuery(sql);
            // System.out.println("ResultSet Query executed successfully!");
        } catch (SQLException e) {
            System.err.println("SQL Exception when creating ResultSet or executing Query");
            System.err.println(e);
            System.exit(1);
        }
    }
    
    public ResultSet getResultSet() {
        return resultSet;
    }
    
    public void close() {
        try {
            resultSet.close();
            // System.out.println("ResultSet Closed Successfully");
        } catch (SQLException e) {
            System.err.println("SQL Exception when closing ResultSet");
            System.err.println(e);
            System.exit(1);
        }
    }
    public RS(PreparedStatement preparedStatement) {
        createResultSet(preparedStatement);
    }

    public RS(Statement statement, String sql) {
        createResultSet(statement, sql);
    }
}
