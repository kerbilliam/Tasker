package CLI;
import Colors.StrColor;

import java.util.Arrays;

public class HelpOutput {
    public static void printHelp() {
        Arguments[] commands = Commands.getAll();
        Arguments[] flags = Flags.getAll();

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

    private static void printSeparator(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("-");
        }
    }
}   