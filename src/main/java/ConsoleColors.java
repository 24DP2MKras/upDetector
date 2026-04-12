public class ConsoleColors {
    // ANSI escape kodi konsoles teksta krāsošanai.
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

    // funkcija apply pieņem String tipa vērtību message un String tipa vērtību colorCode un atgriež String tipa vērtību rezultatu
    // Pievieno ANSI krāsas kodu tekstam, ja krāsu shēma ir ieslēgta (App.colors == 1).
    // Ja krāsas ir izslēgtas, atgriež tekstu bez izmaiņām.
    public static String apply(String message, String colorCode) {
        if (App.colors == 1) {
            return colorCode + message + RESET;
        }
        return message;
    }

    // funkcija println pieņem String tipa vērtību message un String tipa vērtību colorCode un atgriež void tipa vērtību nav
    // Izvada tekstu konsolē ar rindu pārnesumt, lietojot norādīto krāsu (ja krāsas ir ieslēgtas).
    public static void println(String message, String colorCode) {
        System.out.println(apply(message, colorCode));
    }

    // funkcija print pieņem String tipa vērtību message un String tipa vērtību colorCode un atgriež void tipa vērtību nav
    // Izvada tekstu konsolē bez rindu pārneses, lietojot norādīto krāsu (ja krāsas ir ieslēgtas).
    public static void print(String message, String colorCode) {
        System.out.print(apply(message, colorCode));
    }
}
