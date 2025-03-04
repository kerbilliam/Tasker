package CLI;
import java.util.HashMap;

import DB.Database;

public class Parser {
    private HashMap<String, String> map = new HashMap<>();
    // Table Parameters
    private String table = null;
    
    // Task Parameters
    private String task = null;
    private String due = "2999-01-01 00:00:00";
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

    // Filter Fields
    private String where = null;
    private String isThis = null;
    private String field = null;
    private String value = null;

    public Parser() {
        map.put(Database.TASK_NAME, task);
        map.put(Database.DUE_DATE, due);
        map.put(Database.ASSIGNED_USER, assigned_users);
        map.put(Database.STATUS, status);
        map.put(Database.PRIORITY, priority);

        map.put(Database.USERNAME, username);
        map.put(Database.FIRST_NAME, first_name);
        map.put(Database.LAST_NAME, last_name);
        map.put(Database.PASSWORD, password);
        
        map.put("command", command);
        map.put("table", table);
    }
    
    public void parse(String[] args) {
        Arguments[] flags = Flags.getAll();
        Arguments[] commands = Commands.getAll();

        if (args.length == 0) {
            HelpOutput.printHelp();
            System.exit(1);
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
