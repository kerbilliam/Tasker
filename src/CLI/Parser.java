package CLI;
import java.util.HashMap;
import Cipher.Ciphers;
import Colors.StrColor;
import DB.Database;

public class Parser {
    private HashMap<String, String> map = new HashMap<>();
    // Table Parameters
    public static final String table = null;
    
    // Task Parameters
    public static final String task = null;
    public static final String due = "2999-01-01 00:00:00";
    public static final String assigned_users = "";
    public static final String status = "";
    public static final String priority = "";

    // User Parameters
    public static final String username = null;
    public static final String first_name = "";
    public static final String last_name = "";
    public static final String password = null;

    // Command called to return
    public static final String command = null;

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
    
    /**
     * Parses through given arguments and creates a map of values associated with each argument.
     * @param args An array of arguments
     */
    public void parse(String[] args) {
        Arguments[] flags = Flags.getAll();
        Arguments[] commands = Commands.getAll();

        if (args.length == 0) {
            HelpOutput.printArguments();
            System.exit(1);
        }
        
        for (Arguments command : commands) {
            if (command.isAlias(args[0])) {
                try {
                    map.put("command", command.getName());
                    break;
                } catch (Exception e) {
                    System.err.println("Error occured when parsing arguments");
                    System.err.println(e);
                }
            }
        }

        if (map.get("command") == null) {
            System.out.println(StrColor.RED + "Invalid command: "+StrColor.RESET+ args[0]);
            System.exit(1);
        }
        
        for (int i = 1; i < args.length; i++) {
            for (Arguments flag : flags) {

                if (flag.isAlias(args[i])) {

                    try {
                        if (flag.getName().equals(Database.DUE_DATE) || flag.getName().equals(Database.CREATED)){
                            map.put(flag.getName(), args[i + 1]);
                        } else {
                            map.put(flag.getName(), Ciphers.encrypt(args[i + 1], Ciphers.getKey()));
                        }

                        i++;

                    } catch (Exception e) {
                        System.err.println("No value given for flag: " + args[i]);
                        System.exit(1);
                    }
                }
            }
        }
    }
    
    /**
     * Gets the map of arg value pairs created when parsing args.
     * @return map of arg value pairs
     */
    public HashMap<String, String> getMap() {
        return map;
    }
}
