import DB.Database;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class TaskerMethods {
    private static final String LOGIN_CACHE_FILE = "login_cache.txt";
    private static final String INFORMATION_FILE = "information.txt";
    private static String[] credentials;

    // check user in database
    public static boolean authenticate(String username, String password) {
        // Get accounts from database
        HashMap<String, String> accounts = Database.getAccounts();

        // Check if username exists
        if (!accounts.containsKey(username)) {
            return false;
        }

        // Check if password matches
        return accounts.get(username).equals(password);
    }

     // jack method
     public static boolean isRegisteredUser(String inputUsername, String inputPassword) {
         HashMap<String, String> accounts = Database.getAccounts();

         return accounts.containsKey(inputUsername) && accounts.get(inputUsername).equals(inputPassword);
     }

    // print username to login cache
    public static void logUserLogin(String username) {
        try {
            // Open the login cache file with PrintStream (overwrite mode)
            PrintStream ps = new PrintStream(LOGIN_CACHE_FILE);

            // Write just the username to the file
            ps.println(username);
            ps.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to login cache: " + e.getMessage());
        }
    }

    // print username/password into file
    public static void writeUserCredentials(String username, String password) {
        try {
            // Create PrintStream for the information file (overwrite mode)
            PrintStream ps = new PrintStream(INFORMATION_FILE);

            // Write just the username and password without any formatting
            ps.println(username + " " + password);

            ps.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to information file: " + e.getMessage());
        }
    }

    // current user that is logged in
    public static String getCurrentUser() {
        try {
            File file = new File(LOGIN_CACHE_FILE);
            if (!file.exists()) {
                return null;
            }

            Scanner scanner = new Scanner(file);
            String username = null;

            if (scanner.hasNextLine()) {
                username = scanner.nextLine();
            }

            scanner.close();
            return username;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    // reader for log in cache
    public static String readLoginCache() {
        String currentUser = getCurrentUser();

        if (currentUser != null && !currentUser.trim().isEmpty()) {
            System.out.println("Currently logged in: " + currentUser);
            return currentUser;
        } else {
            System.out.println("No user is currently logged in");
            return null;
        }
    }

    // reader for info file
    public static String[] readUserCredentials() {
        try {
            File file = new File(INFORMATION_FILE);
            if (!file.exists()) {
                System.out.println("No credentials file found");
                return null;
            }

            Scanner scanner = new Scanner(file);
            credentials = new String[2];

            if (scanner.hasNextLine()) {
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
                System.out.println("Credentials found for user: " + credentials[0]);
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

    public static String printWhoIsLoggedOn(){
        String credentialsToPrint = "";
        for(int i = 1; i <= credentials.length; i ++){
            if(i == 1)credentialsToPrint += "Username: ";
            else credentialsToPrint += "Password: ";
            credentialsToPrint += credentials[i] + " ";
        }
        return "Logged on: " + readLoginCache() + credentialsToPrint;
    }

   // final login method -> combined w/ jacks
    public static void login(String username, String password) {
        if (username == null || password == null) {
            System.out.println("Error: Username and password required");
            System.exit(1);
        }

        if (isRegisteredUser(username, password)) {
            // Update the current user in login_cache.txt
            logUserLogin(username);

            // Write credentials to information.txt
            writeUserCredentials(username, password);

            System.out.println("Successfully logged in as " + username);
        } else {
            System.out.println("Error: Invalid username or password");
            System.exit(1);
        }
    }
}