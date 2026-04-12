public class ConsoleColors {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

    public static String apply(String message, String colorCode) {
        if (App.colors == 1) {
            return colorCode + message + RESET;
        }
        return message;
    }

    public static void println(String message, String colorCode) {
        System.out.println(apply(message, colorCode));
    }

    public static void print(String message, String colorCode) {
        System.out.print(apply(message, colorCode));
    }
}
