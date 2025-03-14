package Colors;
/* Uses the ANSI color code
 * Only compatible with terminals that support ANSI escape sequences
 * 
 * This class was made with the help of generative AI
 */

public class StrColor {
    // ANSI color codes
    public static final String RESET = "\033[0m";  // Reset color
    public static final String RED = "\033[31m";  // Red color
    public static final String GREEN = "\033[32m"; // Green color
    public static final String YELLOW = "\033[33m"; // Yellow color
    public static final String BLUE = "\033[34m";  // Blue color
    public static final String MAGENTA = "\033[35m"; // Magenta color
    public static final String CYAN = "\033[36m"; // Cyan color
    public static final String WHITE = "\033[37m"; // White color
    public static final String BLACK = "\033[30m"; // Black color
    
    public static String red(String input) {
        return RED+input+RESET;
    }
    
    public static String green(String input) {
        return GREEN+input+RESET;
    }

    public static String yellow(String input) {
        return YELLOW+input+RESET;
    }

    public static String blue(String input) {
        return BLUE+input+RESET;
    }

    public static String magenta(String input) {
        return MAGENTA+input+RESET;
    }

    public static String cyan(String input) {
        return CYAN+input+RESET;
    }

    public static String white(String input) {
        return WHITE+input+RESET;
    }

    public static String black(String input) {
        return BLACK+input+RESET;
    }

}