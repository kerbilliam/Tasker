package CLI;

/**
 * Specified Commands for {@code Parser}. Must include a name, aliases, and description. Example:
 * <pre>
 * private static Arguments command_name() {
 *      String name = "name";
 *      String[] ali = {"ali1", "ali2"};
 *      String desc = "Description for command_name";
 *      return new Flag(name, ali, desc);
 * }
 * </pre>
 * Once a flag has been specified, you must add it to the {@code String[]} returned by {@code getAll()}.
 */
public class Commands {
    private static Arguments help() {
        String name = "help";
        String[] ali = {"help", "-h", "-?","--help"};
        String desc = "Prints database/table structure and passable arguments";
        return new Command(name, ali, desc);
    }
    public static Arguments init() {
        String name = "init";
        String[] ali = {"init", "initialize", "initialize-database"};
        String desc= "Creates a new database named 'database.db' in the root project directory "+
            "initializes it with a 'users' and 'tasks' table.";
        return new Command(name, ali, desc);
    }
    
    public static Arguments printTable() {
        String name = "printTable";
        String[] ali = {"print", "display", "list", "ls"};
        String desc = "Prints specified table's contents to standard output.";
        return new Command(name, ali, desc);
    }
    
    public static Arguments addTask() {
        String name = "addTask";
        String[] ali = {"add-task", "addTask","at"};
        String desc = "Adds task to database. TASK TITLE IS REQUIRED";
        return new Command(name, ali, desc);
    }

    public static Arguments updateTasks() {
        String name = "updateTasks";
        String[] ali = {"update-task", "updateTask", "upt"};
        String desc = "Updates existing task with specified field values";
        return new Command(name, ali, desc);
    }
    
    public static Arguments assignUser() {
        String name = "assignUser";
        String[] ali = {"assign-user", "assignUser", "au"};
        String desc = "Assigns given user to specified existing task";
        return new Command(name, ali, desc);
    }
    
    public static Arguments deleteTask() {
        String name = "deleteTask";
        String[] ali = {"delete-task", "deleteTask", "dt"};
        String desc = "Deletes specified task from the database";
        return new Command(name, ali, desc);
    }
    
    public static Arguments addUser() {
        String name = "addUser";
        String[] ali = {"add-user", "addUser", "adu"};
        String desc = "Adds specified user to databse. Requires username and password flags";
        return new Command(name, ali, desc);
    }
    
    public static Arguments updateUser() {
        String name = "updateUser";
        String[] ali = {"update-user", "updateUser", "uu"};
        String desc = "Updates information for user";
        return new Command(name, ali, desc);
    }
    
    public static Arguments removeUser() {
        String name = "removeUser";
        String[] ali = {"remove-user", "removeUser", "ru"};
        String desc = "Removes specified user from database. Requires ADMIN privaleges";
        return new Command(name, ali, desc);
    }
    
    private static Arguments login() {
        String name = "login";
        String[] ali = {"login", "logon", "log-in", "log-on"};
        String desc = "Login with specified username and password. User must exist in database.";
        return new Command(name, ali, desc);
    }

    /**
     * Returns all Commands made in {@code Commands} as a list of {@code Arguments}.
     * @return {@code Arguments[]} of created commands.
     */
    public static Arguments[] getAll() {
        return new Arguments[]{help(), init(), printTable(), addTask(), updateTasks(), assignUser(), deleteTask(), addUser(), updateUser(), removeUser(), login()};
    }
}
