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
                        resultSet.getString("task"),
                        resultSet.getDate("due"),
                        resultSet.getString("assigned_users"),
                        resultSet.getString("status"),
                        resultSet.getString("priority"),
                        resultSet.getDate("created")
                );
            }
        } catch(SQLException e) {
            printSQLError(e);
            System.exit(1);
        }
    }

    private static void printFormattedUserTable(ResultSet resultSet) {
        try {
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
        String sql = "DELETE FROM "+taskTableName+" WHERE task = ?;";
        String[] values = {task_name};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();
        
        System.out.println(task_name + " successfully deleted.");
    }

    // USER TABLE METHODS

    public static void addUser(String username, String password, String first_name, String last_name) {
        String sql = "INSERT INTO "+userTableName+" (username, password, first_name, last_name) VALUES (?, ?, ?, ?);";
        String[] values = {username, password, first_name, last_name};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();

        System.out.println("New user: [" + username + "] created.");
    }

    public static void updateUser(String username, String field, String value) {
        String sql = "UPDATE "+userTableName+" SET "+field+" = ? WHERE username = ?;";
        String[] values = {value, username};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();

        System.out.println(field + " updated to ["+value+"] for user: " + username);
    }

    public static void removeUser(String username) {
        String sql = "DELETE FROM "+userTableName+" WHERE username = ?;";
        String[] values = {username};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();

        System.out.println(username + " has been deleted successfully.");
    }

    
    public static HashMap<String, String> getAccounts() {
        HashMap<String, String> map = new HashMap<>();
        String sql = "SELECT * FROM users";
        Conn conn = new Conn();
        STMT stmt = new STMT(conn.getConnection());
        RS resultSet = new RS(stmt.getStatement(), sql);
        ResultSet rs = resultSet.getResultSet();
        try {
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                map.put(username, password);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception when getting user accounts");
            System.err.println(e);
            System.exit(1);
        }
        return map;
    }

    public static void printSQLError(SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}