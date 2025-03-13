import DB.Database;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class TaskerMethods {
    private static final String LOGIN_CACHE_FILE = "login_cache.txt";
    private static String[] credentials;

     // jack method
     public static boolean isRegisteredUser(String inputUsername, String inputPassword) {
         HashMap<String, String> accounts = Database.getAccounts();

         return accounts.containsKey(inputUsername) && accounts.get(inputUsername).equals(inputPassword);
     }

    // print username/password into file
    public static void writeUserCredentials(String username, String password) {
        try {
            // Create PrintStream for the login cache file (overwrite mode)
            PrintStream ps = new PrintStream(LOGIN_CACHE_FILE);

            // Write username and pass
            ps.println(username + " " + password);

            ps.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to information file: " + e.getMessage());
        }
    }

    // current user that is logged in
    public static String getCurrentUser() {
        return credentials[0];
    }

    // reader for info file
    public static String[] readUserCredentials() {
        try {
            File file = new File(LOGIN_CACHE_FILE);
            if (!file.exists()) {
                System.out.println("No credentials file found");
                return null;
            }

            Scanner scanner = new Scanner(file);
            credentials = new String[2];

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ", 2); // Split on first space only

                if (parts.length >= 2) {
                    credentials[0] = parts[0]; // Username
                    credentials[1] = parts[1]; // Password
                } else {
                    System.out.println("Invalid credential format in file");
                    scanner.close();
                    return null;
                }
            }

            scanner.close();

            if (credentials[0] != null && credentials[1] != null) {
//                System.out.println("Credentials found for user: " + credentials[0]);
                return credentials;
            } else {
                System.out.println("Incomplete credentials in file");
                return null;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
            return null;
        }
    }

    // return hashmap for who is logged on
    public static HashMap<String,String> loggedOn(){
        HashMap <String, String> hMap = new HashMap<>();
        readUserCredentials();
         hMap.put(getCurrentUser(), credentials[1]);
         return hMap;
    }

   // final login method -> combined w/ jacks
    public static void login(String username, String password) {
        if (username == null || password == null) {
            System.out.println("Error: Username and password required");
            System.exit(1);
        }

        if (isRegisteredUser(username, password)) {

            // Write credentials to LOGIN_CACHE.txt
            writeUserCredentials(username, password);

            System.out.println("Successfully logged in as " + username);
        } else {
            System.out.println("Error: Invalid username or password");
            System.exit(1);
        }
    }
}