package CLI;

public class Commands {
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

    public static Arguments updateTask() {
        String name = "updateTask";
        String[] ali = {"update-task", "updateTask", "upt"};
        String desc = "Updates existing task with specified field values";
        return new Command(name, ali, desc);
    }
}
