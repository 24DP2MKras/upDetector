public class ConsoleColors {

    // ANSI escape kodi konsoles teksta krāsošanai (RESET atgriež noklusēto krāsu)
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";

    // funkcija apply pieņem String tipa vērtību message un String tipa vērtību colorCode
    // un atgriež String tipa vērtību rezultātu.
    //
    // Pievieno ANSI krāsas kodu tekstam, ja krāsu shēma ir ieslēgta (App.colors == 1).
    // Ja krāsas ir izslēgtas, atgriež tekstu bez izmaiņām.
    public static String apply(String message, String colorCode) {
    

    if (App.colors == 1 && colorCode != null) {
        return colorCode + message + RESET;
    }
    return message;
}

    // funkcija println pieņem String tipa vērtību message un String tipa vērtību colorCode
    // un atgriež void tipa vērtību (nav).
    //
    // Izvada tekstu konsolē ar rindas pārnesumu, lietojot norādīto krāsu (ja krāsas ir ieslēgtas).
    public static void println(String message, String colorCode) {
        System.out.println(apply(message, colorCode));
    }

    // funkcija print pieņem String tipa vērtību message un String tipa vērtību colorCode
    // un atgriež void tipa vērtību (nav).
    //
    // Izvada tekstu konsolē bez rindas pārnesuma, lietojot norādīto krāsu (ja krāsas ir ieslēgtas).
    // flush() tiek izmantots, lai nodrošinātu tūlītēju izvadi konsolē (īpaši Windows vidē).
    public static void print(String message, String colorCode) {
        System.out.print(apply(message, colorCode));
        System.out.flush(); // helps on Windows
    }
}
