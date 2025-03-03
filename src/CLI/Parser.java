package CLI;

public class Parser {
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


    public Parser(String[] args) {
        Arguments[] flags = Flags.getAll();
        for (int i = 0; i < args.length; i++) {
            
        }
    }
}
