import java.io.*;
import java.util.*;

public class TaskerMethods {
    // file path
    private final String filePath = "src/information.txt";
    private final String loggedOn = "src/loggedOn.txt";

    // fields
    private String name;
    private Map <String,String> info = new HashMap<>();

    // constructor
    public TaskerMethods() throws FileNotFoundException{
        if(doesFileExist()) {
            readFile();
        }else{
            createFile();
            readFile();
        }
    }

    // register someone
    public void register(String name, String password){
        this.name = name.toLowerCase();
        if(!isRegisteredUser(info,name)){
            info.put(this.name,password);
        }
        System.out.println("Registered in the system!");
        writeToFile();
        writeToFile2();
    }

    // checks if person exists
    public void isRegisteredUser(String name){
        if (isRegisteredUser(info, name.toLowerCase())) {
            System.out.println("Welcome back " + name + "!");
        } else {
            System.out.println("User not found...");
        }
    }

    // enable person to use abilities
    public boolean setAbility(){
        return isRegisteredUser(info, name);
    }

    // check if user is registered
    public boolean isRegisteredUser(Map<String,String> info, String name){
        if(!(info == null)){
            for(String key : info.keySet()){
                if(name.equals(key)) return true;
            }
        }
        return false;
    }

    // write contents of map to file (overwrites the file with new data)
    public void writeToFile() {
        try {
            // Creating a PrintStream object in overwrite mode (not append)
            PrintStream printStream = new PrintStream(new FileOutputStream(filePath, false));

            // Iterating over the map and writing each entry (username, password) to the file
            for (Map.Entry<String, String> entry : info.entrySet()) {
                printStream.println(entry.getKey() + " " + entry.getValue()); // Write username and password
            }

            // Closing the PrintStream after use
            printStream.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found or could not be created.");
            e.printStackTrace();
        }
    }

    // write current user information
    public void writeToFile2(){
        try {
            // Creating a PrintStream object
            PrintStream printStream = new PrintStream(loggedOn);

            // Iterating over the map and writing each entry to the file
            printStream.println("Logged on --");
            printStream.println(name);

            // Closing the PrintStream after use
            printStream.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found or could not be created.");
            e.printStackTrace();
        }
    }

    // checks if file is in root directory
    public boolean doesFileExist() {
        File file = new File(filePath);
        return file.exists();
    }

    // creating file if not exist
    public void createFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // reading file
    public void readFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        int counter = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            // Skip any empty lines or lines without a colon
            if (line.isEmpty() || line.indexOf(':') == -1) {
                continue;
            }

            int colon = line.indexOf(':');

            // Extract name and password using the colon index
            String name = line.substring(0, colon).trim();
            String password = line.substring(colon + 2).trim(); // +2 to skip the colon and space

            // Store the user information in the map
            info.put(name, password);

            counter++;
        }
    }

    // delete people
    public void pop() {
        System.out.println("Name === Password");
        System.out.println();
        for(String key: info.keySet()){
            System.out.println(key + " :" + info.get(key));
        }
        System.out.print("Delete which name from the database? (exit to stop): ");
        Scanner input = new Scanner(System.in);
        String UI = input.nextLine().toLowerCase();
        while(!UI.equals("exit")) {
            if (!isRegisteredUser(info, UI)) {
                System.out.println("Person not found!!");
                System.out.print("Please try again (exit to stop): ");
                UI = input.nextLine().toLowerCase();
            } else {
                info.remove(UI);
                System.out.println("User has been successfully deleted!");
                list();
                writeToFile();
                break;
            }
        }
    }

    //  people in the database
    public void list(){
        System.out.println("Name --- Password");
        for(String key: info.keySet()){
            System.out.println(key + " :" + info.get(key));
        }
    }

    // current person logged on
    public String loggedOn(){
        return name;
    }
}

