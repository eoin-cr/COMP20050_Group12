import java.util.Arrays;

public class TestingConsole {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) {
        System.out.println(ANSI_RED + "This text is red!" + "||||\n" + "Hi" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "||||||" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "GREEN!" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Cyan!" + ANSI_RESET);


        System.out.println(ANSI_GREEN_BACKGROUND + "This text has a green background but default text!" + ANSI_RESET);
        System.out.println(ANSI_RED + "This text has red text but a default background!" + ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND + ANSI_RED + "This text has a green background and red text!" + ANSI_RESET);
        System.out.println(ANSI_RED + ANSI_RED_BACKGROUND + "||||" + ANSI_RESET);
        System.out.println("\033[38;2;255;82;197;48;2;155;106;0mHello");
        System.out.println("\033[48;2;84;130;53m");
        System.out.println("\033[48;2;198;224;180m");
        System.out.println("\033[48;5;202m");
        System.out.println("\033[48;2;255;51;255m");
        System.out.println("\033[48;2;153;102;51m");
        String one = "a\nb\nc\n";
        String two = "d\ne\nf\n";
    }
}