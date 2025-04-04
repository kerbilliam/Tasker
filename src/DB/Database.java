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
    public static final String IS_ADMIN  = "admin";
    
    // Default Admin Account
    public static final String DEFUALT_ADMIN_USERNAME = "admin";
    public static final String DEFAULT_ADMIN_PASSWORD = "PasswordDB123";

    // Table Constants
    public static final String userTableName = "users";
    public static final String taskTableName = "tasks";
    public static final String CREATED = "created";

    public static void init() throws Exception{
        Conn connection = new Conn();
        STMT statement = new STMT(connection.getConnection());
        String sql = "CREATE TABLE IF NOT EXISTS "+userTableName+" (" +
                "id INTEGER PRIMARY KEY, " +
                USERNAME+" TEXT NOT NULL UNIQUE, " +
                FIRST_NAME+" TEXT, " +
                LAST_NAME+" TEXT, " +
                PASSWORD+" TEXT NOT NULL, " +
                IS_ADMIN+" BIT DEFAULT 0, " +
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

        addAdmin(Ciphers.encrypt(DEFUALT_ADMIN_USERNAME, Ciphers.getKey()), Ciphers.encrypt(DEFAULT_ADMIN_PASSWORD, Ciphers.getKey()));
        System.out.println(StrColor.green("Database initialized successfully!"));
    }

    public static void printTable(String table_name, String where, String isThis) throws Exception { //+
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
            System.out.println(StrColor.yellow("Please choose either '"+taskTableName+"' or '"+userTableName+"'"));
        }
        
        resultSet.close();
        pstmt.close();
        conn.close();
    }

    public static void printTable(String table_name) throws Exception {  //+
        String sql = "SELECT * FROM "+table_name+";";
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        RS resultSet = new RS(pstmt.getPreparedStatement());
        
        if (table_name.equals(taskTableName)) {
            printFormattedTaskTable(resultSet.getResultSet());
        } else if (table_name.equals(userTableName)) {
            printFormattedUserTable(resultSet.getResultSet());
        } else {
            System.out.println(StrColor.yellow("Please choose either '"+taskTableName+"' or '"+userTableName+"'"));
        }
        
        resultSet.close();
        pstmt.close();
        conn.close();
    }



    private static void printFormattedTaskTable(ResultSet resultSet) throws Exception {  //+
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
                        resultSet.getDate(CREATED)
                );
            }
        } catch(SQLException e) {
            printSQLError(e);
            System.exit(1);
        }
    }

    private static void printFormattedUserTable(ResultSet resultSet) throws Exception {  //+
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

    public static void addTask(String task_name, String due_date, String assigned_users, String status, String priority) throws Exception {  //+
        String sql = "INSERT INTO "+taskTableName+" ("+TASK_NAME+", "+DUE_DATE+", "+ASSIGNED_USER+", "+STATUS+", "+PRIORITY+") " +
                "VALUES (?, ?, ?, ?, ?);";
        String[] values = {task_name, due_date, assigned_users, status, priority};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);
        
        pstmt.close();
        conn.close();

        System.out.println("Task: " + Ciphers.decrypt(task_name, Ciphers.getKey()) + " has been successfully added.");
    }

    public static void updateTasks(String where, String isThis, String field, String value) throws Exception { //+
        if (field.equals(ASSIGNED_USER)) {
            System.out.print(StrColor.yellow("Use assignUser to assign users to a task"));
            System.exit(1);
        }
        String sql = "UPDATE "+taskTableName+" SET "+field+" = ? WHERE "+where+" = ?;";
        String[] values = {value, isThis};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);
        
        pstmt.close();
        conn.close();

        if (field.equals(DUE_DATE) || field.equals(CREATED)) {
            System.out.println(field + " updated to ["+value+"] for all "+where+" = '"+Ciphers.decrypt(isThis, Ciphers.getKey())+"'");
        }else{
            System.out.println(field + " updated to ["+Ciphers.decrypt(value, Ciphers.getKey())+"] for all "+where+" = '"+Ciphers.decrypt(isThis, Ciphers.getKey())+"'");

        }
    }

    public static void assignUser(String where, String isThis, String username) throws Exception{ //+
        String sql = "UPDATE "+taskTableName+" SET "+ASSIGNED_USER+" = ? WHERE "+where+" = ?";
        String[] values = {username, isThis};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();

       System.out.println(Ciphers.decrypt(username, Ciphers.getKey()) + " Has been assigned to where " + where + " = " + Ciphers.decrypt(isThis, Ciphers.getKey())); 
    }

    public static void deleteTask(String task_name) throws Exception { //+
        String sql = "DELETE FROM "+taskTableName+" WHERE task = ?;";
        String[] values = {task_name};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();
        
        System.out.println(Ciphers.decrypt(task_name, Ciphers.getKey()) + " successfully deleted."); 
    }

    // USER TABLE METHODS

    public static void addUser(String username, String password, String first_name, String last_name) throws Exception { //+
        String sql = "INSERT INTO "+userTableName+" ("+USERNAME+", "+PASSWORD+", "+FIRST_NAME+", "+LAST_NAME+") VALUES (?, ?, ?, ?);";
        String[] values = {username, password, first_name, last_name};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();

        System.out.println("New user: [" + Ciphers.decrypt(username, Ciphers.getKey()) + "] created.");
    }
    
    private static void addAdmin(String username, String password) {
        String sql = "INSERT INTO "+userTableName+" ("+USERNAME+", "+PASSWORD+", "+FIRST_NAME+", "+LAST_NAME+", "+IS_ADMIN+") VALUES (?, ?, ?, ?, ?);";
        String[] values = {username, password, "", "", "1"};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();
    }

    public static void updateUser(String username, String field, String value) throws Exception { //+
        String sql = "UPDATE "+userTableName+" SET "+field+" = ? WHERE "+USERNAME+" = ?;";
        String[] values = {value, username};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();

        System.out.println(field + " updated to ["+ Ciphers.decrypt(value, Ciphers.getKey()) +"] for user: " + Ciphers.decrypt(username, Ciphers.getKey()));
    }

    public static void removeUser(String username) throws Exception { //+
        String sql = "DELETE FROM "+userTableName+" WHERE "+USERNAME+" = ?;";
        String[] values = {username};
        Conn conn = new Conn();
        PSTMT pstmt = new PSTMT(conn.getConnection(), sql);
        pstmt.setNrunStatement(values);

        pstmt.close();
        conn.close();

        System.out.println(StrColor.red(Ciphers.decrypt(username, Ciphers.getKey()) + " has been deleted successfully."));
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
            System.err.println(StrColor.red("SQL Exception when getting user accounts"));
            System.err.println(e);
            System.exit(1);
        }
        return map;
    }
    
    public static Boolean isAdmin(String username, String password) {
        String sql = "SELECT * FROM "+userTableName+";";
        Conn conn = new Conn();
        STMT stmt = new STMT(conn.getConnection());
        RS resultSet = new RS(stmt.getStatement(), sql);
        try {
            while (resultSet.getResultSet().next()) {
                String name = resultSet.getResultSet().getString(USERNAME);
                String pass = resultSet.getResultSet().getString(PASSWORD);
                byte isAdmin = resultSet.getResultSet().getByte(IS_ADMIN);
                if (name.equals(username) && pass.equals(password) && isAdmin == 1) {
                    conn.close();
                    stmt.close();
                    resultSet.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception when checking admin privilege");
            System.err.println(e);
            System.exit(1);
        }
        return false;
    }

    public static void printSQLError(SQLException e) {
        System.err.println(StrColor.red("Error: " + e.getMessage()));
    }
}