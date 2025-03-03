package CLI;
import Colors.StrColor;

public class HelpOutput {
    public static void printHelp() {
        Arguments[] commands = Commands.getAll();
        Arguments[] flags = Flags.getAll();

        System.out.print(StrColor.GREEN);
        printSeparator(10);
        System.out.println("COMMANDS:");
        printSeparator(10);
        System.out.println(StrColor.RESET);
        System.out.printf("%-20s%-30s\n", "Command Aliases", "Description");
        for (int i = 0; i < commands.length; i++) {
            System.out.printf("%-20s%-30s\n", commands[i].getAlias().toString(), commands[i].getDescription());
        }

        System.out.print(StrColor.YELLOW);
        printSeparator(10);
        System.out.println("FLAGS:");
        printSeparator(10);
        System.out.println(StrColor.RESET);
        // System.out.printf("%-20s%-30s%s\n", "Flag Aliases", "Description", "Used for");
        System.out.printf("%-20s%-30s\n", "Flag Aliases", "Description");
        for (int i = 0; i < flags.length; i++) {
            // System.out.printf("%-20s%-30s%s\n", flags[i].getAlias().toString(), flags[i].getDescription(), ((Flag) flags[i]).getAssocCommands().toString());
            System.out.printf("%-20s%-30s\n", flags[i].getAlias().toString(), flags[i].getDescription());

        }
    }

    private static void printSeparator(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("-");
        }
    }
}   