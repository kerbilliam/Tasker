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

        connection.close();
        statement.close();

/*         connect();
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
 */
       
        
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

    public static void printTable(String table_name, String field, String fieldValue) {
        String[] input = {table_name, field, fieldValue};
        String sql = "SELECT * FROM ? WHERE ? = '?';";
        connect();
        createPreparedStatement(sql);
        prepareStrings(input);
        makeResultSet();
        outputTable(table_name);
        closeCONN();
        closePSTMT();
        closeRS();
    }

    public static void printTable(String table_name) {
        if (table_name.equals("tasks") || table_name.equals("users")){
            String[] input = {table_name};
            String sql = "SELECT * FROM ?;";
            connect();
            createPreparedStatement(sql);
            prepareStrings(input);
            makeResultSet();
            outputTable(table_name);
        } else {
            System.out.println("Please choose 'tasks' or 'users'");
        }

        closeCONN();
        closePSTMT();
        closeRS();
    }



    private static void printFormatedTaskTable() {
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
        }
    }

    private static void outputTable(String tableName) {
        if (resultSet != null) {
            if (tableName.equals("tasks")) {
                printFormatedTaskTable();
            } else if (tableName.equals("users")) {
                printFormattedUserTable();
            }


/*             if (query != null) {
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
            } */
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

    private static void printFormattedUserTable() {
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