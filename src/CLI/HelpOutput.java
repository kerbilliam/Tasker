package CLI;
import Colors.StrColor;
import DB.Database;

import java.util.Arrays;

public class HelpOutput {
    /**
     * Prints all Commands and Flags with their associated aliases and and descriptions in a formatted list. 
     */
    public static void printArguments() {
        Arguments[] commands = Commands.getAll();
        Arguments[] flags = Flags.getAll();

        System.out.println();
        System.out.print(StrColor.GREEN);
        printSeparator(31);
        System.out.print("COMMANDS");
        printSeparator(31);
        System.out.println(StrColor.RESET);
        System.out.printf("%-50s%s\n", "Command Aliases", "Description");
        printSeparator(70);
        System.out.println();
        for (Arguments command : commands) {
            System.out.printf("%-50s%s\n", Arrays.toString(command.getAlias()), command.getDescription());
        }

        System.out.print(StrColor.YELLOW);
        printSeparator(32);
        System.out.print("FLAGS");
        printSeparator(33);
        System.out.println(StrColor.RESET);
        // System.out.printf("%-20s%-30s%s\n", "Flag Aliases", "Description", "Used for");
        System.out.printf("%-50s%s\n", "Flag Aliases", "Description");
        printSeparator(70);
        System.out.println();
        for (Arguments flag : flags) {
            // System.out.printf("%-20s%-30s%s\n", flags[i].getAlias().toString(), flags[i].getDescription(), ((Flag) flags[i]).getAssocCommands().toString());
            System.out.printf("%-50s%s\n", Arrays.toString(flag.getAlias()), flag.getDescription());

        }
    }

    /**
     * Prints the column names used for the databases (formatted) and also runs <pre>printArguments()<pre>
     */
    public static void printHelp() {
        System.out.println();
        System.out.print(StrColor.CYAN);
        printSeparator(25);
        System.out.print("DATABASE FIELD NAMES");
        printSeparator(25);
        System.out.print(StrColor.RESET);
        System.out.println();
        System.out.println();
        printSeparator(25);
        System.out.print("TASKS TABLE");
        printSeparator(25);
        System.out.println();
        System.out.println();
        System.out.printf("%-5s"+Database.TASK_NAME+"%-5s|%-5s"+Database.DUE_DATE+"%5s|%-5s"+Database.ASSIGNED_USER+"%-5s|%-5s"+Database.STATUS+"%-5s|%-5s"+Database.PRIORITY+"%5s", "", "", "", "", "", "", "", "", "", "");
        System.out.println();
        System.out.println();

        printSeparator(25);
        System.out.print("USERS TABLE");
        printSeparator(25);
        System.out.println();
        System.out.println();
        System.out.printf("%-5s"+Database.USERNAME+"%-5s|%-5s"+Database.PASSWORD+"%-5s|%-5s"+Database.FIRST_NAME+"%-5s|%-5s"+Database.LAST_NAME, "", "", "", "", "", "", "");
        System.out.println();
        System.out.println();

        printArguments();
    }

    /**
     * Prints '-' repeating for n times. Does not create a new line.
     * @param n repeat {@code n} times.
     */
    public static void printSeparator(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("-");
        }
    }
}   