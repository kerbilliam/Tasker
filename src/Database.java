import java.net.ConnectException;
import java.sql.*;
import java.util.HashMap;

public class Database {

    private static final String databaseURL = "jdbc:sqlite:database.db";
    private static final String userTableName = "users";
    private static final String taskTableName = "tasks";
    private static Connection connection;
    private static ResultSet resultSet;
    private static Statement statement;
    private static PreparedStatement preparedStatement;


    public static void init(){
        connect();
        makeStatement();
        String sql = "CREATE TABLE IF NOT EXISTS "+userTableName+" (" +
                "id INTEGER PRIMARY KEY, " +
                "username TEXT NOT NULL UNIQUE, " +
                "first_name TEXT, " +
                "last_name TEXT, " +
                "password TEXT NOT NULL, " +
                "created DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";

        runStatement(sql);

        sql = "CREATE TABLE IF NOT EXISTS "+taskTableName+" (" +
                "id INTEGER PRIMARY KEY, " +
                "task TEXT NOT NULL, " +
                "due DATETIME, " +
                "assigned_users TEXT, " +
                "status TEXT, " +
                "priority TEXT, " +
                "created DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";

        runStatement(sql);
        
        closeCONN();
        closeSTMT();

       
        
/*         connect();
        String sql = "CREATE TABLE IF NOT EXISTS "+userTableName+" (?, ?, ?, ?, ?, ?);";
        createPreparedStatment(sql);
        try {
            preparedStatement.setString(1, "id INTEGER PRIMARY KEY");
            preparedStatement.setString(2, "username TEXT NOT NULL UNIQUE");
            preparedStatement.setString(3, "first_name TEXT");
            preparedStatement.setString(4, "last_name TEXT");
            preparedStatement.setString(5, "password TEXT NOT NULL");
            preparedStatement.setString(6, "created DATETIME DEFAULT CURRENT_TIMESTAMP");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLError(e);
            System.out.println("Error when making users table");
        }
        
        sql = "CREATE TABLE IF NOT EXISTS "+taskTableName+" (?, ?, ?, ?, ?, ?, ?);";
        createPreparedStatment(sql);
        try {
        preparedStatement.setString(1, "id INTEGER PRIMARY KEY");
        preparedStatement.setString(2, "task TEXT NOT NULL");
        preparedStatement.setString(3, "due DATETIME");
        preparedStatement.setString(4, "assigned_users TEXT");
        preparedStatement.setString(5, "status TEXT");
        preparedStatement.setString(6, "priority TEXT");
        preparedStatement.setString(7, "created DATETIME DEFAULT CURRENT_TIMESTAMP");
        preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLError(e);
            System.out.println("Error when making tasks table");
        } 
        */
    }

    // If you want to include multiple fields, separate them with a comma.
    public static void printTable(String table_name, String field, String fieldValue) {
        // String sql = "SELECT * FROM "+table_name+" WHERE "+field+" = '"+fieldValue+"';";
        // outputTable(runSQLQuery(sql), table_name);
        String[] input = {table_name, field, fieldValue};
        String sql = "SELECT * FROM ? WHERE ? = '?';";
        connect();
        createPreparedStatment(sql);
        prepareStrings(input);
        closeCONN();
        closePSTMT();
    }

    public static void printTable(String table_name) {
        if (table_name.equals("tasks") || table_name.equals("users")){
            String sql = "SELECT * FROM "+table_name+";";
            outputTable(runSQLQuery(sql), table_name);
        } else {
            System.out.println("Please choose 'tasks' or 'users'");
        }

    }



    private static void printFormatedTaskTable(ResultSet rs) {
        try {
            System.out.printf("%-25s%-15s%-15s%-15s%-15s%s%n",
                    "Task",
                    "Due",
                    "Assigned to:",
                    "Status",
                    "Priority",
                    "Created"
            );
            while (rs.next()) {
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
        }
    }

    private static void outputTable(Object[] result, String table) {
        if (result != null) {
            Connection conn = (Connection) result [0];
            Statement stmt = (Statement) result[1];
            ResultSet query = (ResultSet) result[2];

            if (query != null) {
                if (table.equals("tasks")) {
                    printFormatedTaskTable(query);
                } else if (table.equals("users")) {
                    printFormattedUserTable(query);
                }


                try {
                    query.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e ){
                    printSQLError(e);
                }
            }
        }
    }

    // TASK TABLE METHODS

    public static void addTask(String task_name, String due_date, String assigned_users, String status, String priority) {
        String sql = "INSERT INTO "+taskTableName+" (task, due, assigned_users, status, priority) " +
                "VALUES ('" + task_name + "', '"+due_date+"', '"+assigned_users+"', '"+status+"', '"+priority+"');";
        runSQLStatement(sql);
        System.out.println("Task: " + task_name + " has been successfully added.");
    }

    // When updating time, it has to be in ISO 8601 format: 'YYYY-MM-DD HH:MM:SS'
    public static void updateTasks(String where, String isThis, String field, String value) {
        if (field.equals("assigned_users")) {
            System.out.print("use assignUser to assign users to a task");
            System.exit(1);
        }
        String sql = "UPDATE "+taskTableName+" SET "+field+" = " +
                "'"+value+"' WHERE "+where+" = '"+isThis+"';";
        runSQLStatement(sql);
        System.out.println(field + " updated to ["+value+"] for all "+where+" = '"+isThis+"'");
    }

    public static void assignUser(String task_name, String username) {
        String sql = "UPDATE "+taskTableName+" SET assigned_users = '"+username+"' " +
                "WHERE task = '"+task_name+"';";
        runSQLStatement(sql);
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

    private static void printFormattedUserTable(ResultSet rs) {
        try {
            System.out.printf("%-15s%-25s%-25s%s%n",
                    "Username",
                    "First",
                    "Last",
                    "Created"
            );
            while (rs.next()) {
                System.out.printf("%-15s%-25s%-25s%s%n",
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("created")
                );
            }
        } catch(SQLException e) {
            printSQLError(e);
        }
    }
    
    public static HashMap<String, String> getAccounts() {
        HashMap<String, String> map = new HashMap<>();
        // get users ResultSet
        while (rs.next()) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            map.put(username, password);
        }
    }

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
            connection = DriverManager.getConnection(databaseURL);
        } catch (SQLException e) {
            printSQLError(e);
        }
    }

    private static void makeStatement() {
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
    
    private static void createPreparedStatment(String sql) {
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

    private static void getRS(String sql) {
        try {
            if (connection != null) {
            resultSet = statement.executeQuery(sql);
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