package CLI;
import java.util.HashMap;

public class Parser {
    private HashMap<String, String> map = new HashMap<>();
    // Table Parameters
    private String table = null;
    
    // Task Parameters
    private String task = null;
    private String due = "2999:01:01 00:00:00";
    private String assigned_users = "";
    private String status = "";
    private String priority = "";

    // User Parameters
    private String username = null;
    private String first_name = "";
    private String last_name = "";
    private String password = null;

    // Command called to return
    private String command = null;

    public Parser() {
        map.put("task", task);
        map.put("due", due);
        map.put("assigned_users", assigned_users);
        map.put("status", status);
        map.put("priority", priority);

        map.put("username", username);
        map.put("first_name", first_name);
        map.put("last_name", last_name);
        map.put("password", password);
        
        map.put("command", command);
    }
    
    public void parse(String[] args) {
        Arguments[] flags = Flags.getAll();
        Arguments[] commands = Commands.getAll();

        if (args.length == 0) {
            // print help
        }
        
        for (Arguments command : commands) {
            if (command.isAlias(args[0])) {
                try {
                    map.put("command", command.getName());
                } catch (Exception e) {
                    System.err.println("Error occured when parsing arguments");
                    System.err.println(e);
                }
            }
        }
        
        for (int i = 1; i < args.length; i++) {
            for (Arguments flag : flags) {

                if (flag.isAlias(args[i])) {

                    try {

                        map.put(flag.getName(), args[i + 1]);
                        i++;

                    } catch (Exception e) {
                        System.err.println("No value given for flag: " + args[i]);
                        System.exit(1);
                    }
                }
            }
        }
    }
    
    public HashMap<String, String> getMap() {
        return map;
    }
}
