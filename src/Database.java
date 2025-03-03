import java.net.ConnectException;
import java.sql.*;
import DB.*;
import java.util.HashMap;

public class Database {
    private static final String userTableName = "users";
    private static final String taskTableName = "tasks";

    public static void init(){
        Conn connection = new Conn();
        STMT statement = new STMT(connection.getConnection());
        String sql = "CREATE TABLE IF NOT EXISTS "+userTableName+" (" +
                "id INTEGER PRIMARY KEY, " +
                "username TEXT NOT NULL UNIQUE, " +
                "first_name TEXT, " +
                "last_name TEXT, " +
                "password TEXT NOT NULL, " +
                "created DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";
        statement.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS "+taskTableName+" (" +
                "id INTEGER PRIMARY KEY, " +
                "task TEXT NOT NULL, " +
                "due DATETIME, " +
                "assigned_users TEXT, " +
                "status TEXT, " +
                "priority TEXT, " +
                "created DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";
        statement.executeUpdate(sql);

        statement.close();
        connection.close();
    }

    public static void printTable(String table_name, String field, String fieldValue) {
        String[] input = {fieldValue};
        String sql = "SELECT * FROM "+table_name+" WHERE "+field+" = ?;";
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setStatement(input);
        RS resultSet = new RS(pstmt.getPreparedStatement());
        
        if (table_name.equals("tasks")) {
            printFormattedTaskTable(resultSet.getResultSet());
        } else if (table_name.equals("users")) {
            printFormattedUserTable(resultSet.getResultSet());
        }
        
        resultSet.close();
        pstmt.close();
        conn.close();
    }

    public static void printTable(String table_name) {
        String sql = "SELECT * FROM "+table_name+";";
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        RS resultSet = new RS(pstmt.getPreparedStatement());
        
        if (table_name.equals("tasks")) {
            printFormattedTaskTable(resultSet.getResultSet());
        } else if (table_name.equals("users")) {
            printFormattedUserTable(resultSet.getResultSet());
        }
        
        resultSet.close();
        pstmt.close();
        conn.close();
    }



    private static void printFormattedTaskTable(ResultSet resultSet) {
        try {
            ResultSet rs = resultSet;
            System.out.printf("%-25s%-15s%-15s%-15s%-15s%s%n",
                    "Task",
                    "Due",
                    "Assigned to:",
                    "Status",
                    "Priority",
                    "Created"
            );
            while (resultSet.next()) {
                System.out.printf("%-25s%-15s%-15s%-15s%-15s%s%n",
                        rs.getString("task"),
                        rs.getDate("due"),
                        rs.getString("assigned_users"),
                        rs.getString("status"),
                        rs.getString("priority"),
                        rs.getDate("created")
                );
            }
        } catch(SQLException e) {
            printSQLError(e);
            System.exit(1);
        }
    }

    private static void printFormattedUserTable(ResultSet rs) {
        try {
            ResultSet resultSet = rs;
            System.out.printf("%-15s%-25s%-25s%s%n",
                    "Username",
                    "First",
                    "Last",
                    "Created"
            );
            while (resultSet.next()) {
                System.out.printf("%-15s%-25s%-25s%s%n",
                        resultSet.getString("username"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("created")
                );
            }
        } catch(SQLException e) {
            printSQLError(e);
        }
    }


    // TASK TABLE METHODS
    // When updating time, it has to be in ISO 8601 format: 'YYYY-MM-DD HH:MM:SS'

    public static void addTask(String task_name, String due_date, String assigned_users, String status, String priority) {
        String sql = "INSERT INTO "+taskTableName+" (task, due, assigned_users, status, priority) " +
                "VALUES (?, ?, ?, ?, ?);";
        String[] values = {task_name, due_date, assigned_users, status, priority};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);
        
        pstmt.close();
        conn.close();

        System.out.println("Task: " + task_name + " has been successfully added.");
    }

    public static void updateTasks(String where, String isThis, String field, String value) {
        if (field.equals("assigned_users")) {
            System.out.print("use assignUser to assign users to a task");
            System.exit(1);
        }
        String sql = "UPDATE "+taskTableName+" SET "+field+" = ? WHERE "+where+" = ?;";
        String[] values = {field, isThis};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);
        
        pstmt.close();
        conn.close();

        System.out.println(field + " updated to ["+value+"] for all "+where+" = '"+isThis+"'");
    }

    public static void assignUser(String where, String isThis, String username) {
        String sql = "UPDATE "+taskTableName+" SET assigned_users = ? WHERE "+where+" = ?";
        String[] values = {username, isThis};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();

       System.out.println(username + " Has been assigned to where " + where + " = " + isThis); 
    }

    public static void deleteTask(String task_name) {
        String sql = "DELETE FROM "+taskTableName+" WHERE task = '"+task_name+"';";
        runSQLStatement(sql);
        System.out.println(task_name + " successfully deleted.");
    }

    // USER TABLE METHODS

    public static void addUser(String username, String password) {
        String sql = "INSERT INTO "+userTableName+" (username, password) " +
                "VALUES ('"+username+"', '"+password+"');";
        runSQLStatement(sql);
        System.out.println("New user: [" + username + "] created.");
    }

    public static void updateUser(String username, String field, String value) {
        String sql = "UPDATE "+userTableName+" SET "+field+" = " +
                "'"+value+"' WHERE username = '"+username+"';";
        runSQLStatement(sql);
        System.out.println(field + " updated to ["+value+"] for user: " + username);
    }

    public static void removeUser(String username) {
        String sql = "DELETE FROM "+userTableName+" WHERE username = '"+username+"';";
        runSQLStatement(sql);
        System.out.println(username + " has been deleted successfully.");
    }

    
//    public static HashMap<String, String> getAccounts() {
//        HashMap<String, String> map = new HashMap<>();
//        // get users ResultSet
//        while (resultSet.next()) {
//            String username = resultSet.getString("username");
//            String password = resultSet.getString("password");
//            map.put(username, password);
//        }
//    }

    private static void runSQLStatement(String sql) {
        try (Connection conn = DriverManager.getConnection(databaseURL)) {
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            printSQLError(e);
        }
    }

    private static Object[] runSQLQuery(String sql) {
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                return new Object[]{conn, stmt, rs};
            }
        } catch (SQLException e) {
            printSQLError(e);
        }
        return null;
    }
    
    private static void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(databaseURL);
            }
        } catch (SQLException e) {
            printSQLError(e);
        }
    }

    private static void checkConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(databaseURL);
            }
        } catch (SQLException e) {
            printSQLError(e);
        }
    }

    private static void makeStatement() {
        checkConnection();
        try {
            if (connection != null) {
                statement = connection.createStatement();
            }
        } catch (SQLException e) {
            printSQLError(e);
        }
    }
    
    private static void runStatement(String sql) {
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            printSQLError(e);
        }
    }
    
    private static void createPreparedStatement(String sql) {
        checkConnection();
        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
            }
        } catch (SQLException e) {
            printSQLError(e);
        }
    }

    private static void prepareStrings(String[] statements) {
        int length = statements.length;
        try {
            for (int i = 1; i <= length; i++) {
                preparedStatement.setString(i, statements[i - 1]);
            }
        } catch (SQLException e) {
            printSQLError(e);
        }
    }

    private static void makeResultSet() {
        try {
            if (connection != null) {
            resultSet = preparedStatement.executeQuery();
            }
        } catch (SQLException e) {
            printSQLError(e);
        }
    }
    
    private static void closeCONN() {
        try {
            connection.close();
        } catch (SQLException e) {
            printSQLError(e);
        }
    }

    private static void closeSTMT() {
        try {
            statement.close();
        } catch (SQLException e) {
            printSQLError(e);
        }
    }

    private static void closePSTMT() {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            printSQLError(e);
        }
    }
   
    private static void closeRS() {
        try {
            resultSet.close();
        } catch (SQLException e) {
            printSQLError(e);
        }
    }

    public static void printSQLError(SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}