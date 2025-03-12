package DB;
import CLI.HelpOutput;
import Colors.StrColor;
import Cipher.Ciphers;
import java.sql.*;
import java.util.HashMap;

public class Database {
    // Task Column Name Constants
    public static final String TASK_NAME = "task";
    public static final String DUE_DATE = "due";
    public static final String ASSIGNED_USER = "assigned_users";
    public static final String STATUS = "status";
    public static final String PRIORITY = "priority";

    // Users Column Name Constants
    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PASSWORD = "password";

    // Table Constants
    public static final String userTableName = "users";
    public static final String taskTableName = "tasks";
    public static final String CREATED = "created";

    public static void init(){
        Conn connection = new Conn();
        STMT statement = new STMT(connection.getConnection());
        String sql = "CREATE TABLE IF NOT EXISTS "+userTableName+" (" +
                "id INTEGER PRIMARY KEY, " +
                USERNAME+" TEXT NOT NULL UNIQUE, " +
                FIRST_NAME+" TEXT, " +
                LAST_NAME+" TEXT, " +
                PASSWORD+" TEXT NOT NULL, " +
                CREATED+" DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";
        statement.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS "+taskTableName+" (" +
                "id INTEGER PRIMARY KEY, " +
                TASK_NAME+" TEXT NOT NULL, " +
                DUE_DATE+" DATETIME, " +
                ASSIGNED_USER+" TEXT, " +
                STATUS+" TEXT, " +
                PRIORITY+" TEXT, " +
                CREATED+" DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";
        statement.executeUpdate(sql);

        statement.close();
        connection.close();
        System.out.println(StrColor.GREEN + "Database initialized successfully!"+ StrColor.RESET);
    }

    public static void printTable(String table_name, String where, String isThis) throws Exception {
        String[] input = {isThis};
        String sql = "SELECT * FROM "+table_name+" WHERE "+where+" = ?;";
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setStatement(input);
        RS resultSet = new RS(pstmt.getPreparedStatement());
        
        if (table_name.equals(taskTableName)) {
            printFormattedTaskTable(resultSet.getResultSet());
        } else if (table_name.equals(userTableName)) {
            printFormattedUserTable(resultSet.getResultSet());
        } else {
            System.out.println("Please choose either '"+taskTableName+"' or '"+userTableName+"'");
        }
        
        resultSet.close();
        pstmt.close();
        conn.close();
    }

    public static void printTable(String table_name) throws Exception {
        String sql = "SELECT * FROM "+table_name+";";
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        RS resultSet = new RS(pstmt.getPreparedStatement());
        
        if (table_name.equals(taskTableName)) {
            printFormattedTaskTable(resultSet.getResultSet());
        } else if (table_name.equals(userTableName)) {
            printFormattedUserTable(resultSet.getResultSet());
        } else {
            System.out.println("Please choose either '"+taskTableName+"' or '"+userTableName+"'");
        }
        
        resultSet.close();
        pstmt.close();
        conn.close();
    }



    private static void printFormattedTaskTable(ResultSet resultSet) throws Exception { //DECRYPTED
        try {
            System.out.println();
            System.out.printf("%-25s%-15s%-15s%-15s%-15s%s%n",
                    "Task",
                    "Due",
                    "Assigned to:",
                    "Status",
                    "Priority",
                    "Created"
            );
            HelpOutput.printSeparator(100);
            System.out.println();
            while (resultSet.next()) {
                System.out.printf("%-25s%-15s%-15s%-15s%-15s%s%n",
                        Ciphers.decrypt(resultSet.getString(TASK_NAME), Ciphers.getKey()), 
                        resultSet.getDate(DUE_DATE), 
                        Ciphers.decrypt(resultSet.getString(ASSIGNED_USER), Ciphers.getKey()),
                        Ciphers.decrypt(resultSet.getString(STATUS), Ciphers.getKey()),
                        Ciphers.decrypt(resultSet.getString(PRIORITY), Ciphers.getKey()),
                        Ciphers.decrypt(resultSet.getString(CREATED), Ciphers.getKey())
                );
            }
        } catch(SQLException e) {
            printSQLError(e);
            System.exit(1);
        }
    }

    private static void printFormattedUserTable(ResultSet resultSet) throws Exception { //DECRYPT
        try {
            System.out.println();
            System.out.printf("%-15s%-25s%-25s%s%n",
                    "Username",
                    "First",
                    "Last",
                    "Created"
            );
            HelpOutput.printSeparator(100);
            System.out.println();
            while (resultSet.next()) {
                System.out.printf("%-15s%-25s%-25s%s%n",
                        Ciphers.decrypt(resultSet.getString(USERNAME), Ciphers.getKey()), 
                        Ciphers.decrypt(resultSet.getString(FIRST_NAME), Ciphers.getKey()), 
                        Ciphers.decrypt(resultSet.getString(LAST_NAME), Ciphers.getKey()), 
                        resultSet.getDate(CREATED)
                );
            }
        } catch(SQLException e) {
            printSQLError(e);
        }
    }


    // TASK TABLE METHODS
    // When updating time, it has to be in ISO 8601 format: 'YYYY-MM-DD HH:MM:SS'

    public static void addTask(String task_name, String due_date, String assigned_users, String status, String priority) {
        String sql = "INSERT INTO "+taskTableName+" ("+TASK_NAME+", "+DUE_DATE+", "+ASSIGNED_USER+", "+STATUS+", "+PRIORITY+") " +
                "VALUES (?, ?, ?, ?, ?);";
        String[] values = {task_name, due_date, assigned_users, status, priority};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);
        
        pstmt.close();
        conn.close();

        System.out.println("Task: " + task_name + " has been successfully added.");
    }

    public static void updateTasks(String where, String isThis, String field, String value) { //decrypt
        if (field.equals(ASSIGNED_USER)) {
            System.out.print("use assignUser to assign users to a task");
            System.exit(1);
        }
        String sql = "UPDATE "+taskTableName+" SET "+field+" = ? WHERE "+where+" = ?;";
        String[] values = {value, isThis};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);
        
        pstmt.close();
        conn.close();

        System.out.println(field + " updated to ["+value+"] for all "+where+" = '"+isThis+"'");
    }

    public static void assignUser(String where, String isThis, String username) { //decrypt
        String sql = "UPDATE "+taskTableName+" SET "+ASSIGNED_USER+" = ? WHERE "+where+" = ?";
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
        
        System.out.println(task_name + " successfully deleted."); //decrypt
    }

    // USER TABLE METHODS

    public static void addUser(String username, String password, String first_name, String last_name) {
        String sql = "INSERT INTO "+userTableName+" ("+USERNAME+", "+PASSWORD+", "+FIRST_NAME+", "+LAST_NAME+") VALUES (?, ?, ?, ?);";
        String[] values = {username, password, first_name, last_name};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();

        System.out.println("New user: [" + username + "] created.");
    }

    public static void updateUser(String username, String field, String value) {
        String sql = "UPDATE "+userTableName+" SET "+field+" = ? WHERE "+USERNAME+" = ?;";
        String[] values = {value, username};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();

        System.out.println(field + " updated to ["+value+"] for user: " + username);
    }

    public static void removeUser(String username) {
        String sql = "DELETE FROM "+userTableName+" WHERE "+USERNAME+" = ?;";
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
        String sql = "SELECT * FROM "+userTableName+";";
        Conn conn = new Conn();
        STMT stmt = new STMT(conn.getConnection());
        RS resultSet = new RS(stmt.getStatement(), sql);
        try {
            while (resultSet.getResultSet().next()) {
                String username = resultSet.getResultSet().getString(USERNAME);
                String password = resultSet.getResultSet().getString(PASSWORD);
                map.put(username, password);
            }
            resultSet.close();
            stmt.close();
            conn.close();
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