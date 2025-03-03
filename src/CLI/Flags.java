package CLI;
import DB.Database;

public class Flags {
    public static Arguments task() {
        String name = Database.TASK_NAME;
        String[] ali = {"--name", "--title", "-n", "-t"};
        String desc = "Task title";
        String[] assoc = {"add-task"};
        return new Flag(name, ali, assoc, desc);
    }

    public static Arguments[] getAll() {
        Arguments[] list = {task()};
        return list;
    }
}
