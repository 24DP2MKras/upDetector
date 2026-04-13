import java.io.File;
import java.util.Scanner;

public class App {
    // Globāls krāsu shēmas karogs: 1 = krāsains teksts ieslēgts, 0 = krāsains teksts izslēgts.
    public static int colors = 0;
    appFunkcijas app = new appFunkcijas();

    // funkcija safeReadLine pieņem String tipa vērtību prompt un Scanner tipa vērtību scanner un atgriež String tipa vērtību rezultatu
    // Droši nolasa rindu no skenera ar kļūdu apstrādi. Atgriež ievadīto tekstu vai tukšu virkni kļūdas gadījumā.
    private String safeReadLine(String prompt, Scanner scanner) {
        try {
            System.out.print(prompt);
            return scanner.nextLine();
        } catch (Exception e) {
            ConsoleColors.println("[Ievades kluda: " + e.getMessage() + "]", ConsoleColors.RED);
            return "";
        }
    }

    // funkcija App pieņem nav parametru un atgriež void tipa vērtību nav
    // Galvenā izvēlne lietotnei, kas apstrādā reģistrāciju, pierakstīšanos, krāsu shēmu un programmas izslēgšanu.
    // Cilpa darbojas, līdz lietotājs izvēlas iziet vai izslēgt programmu.
    public void App() {
        app.clear();
        Scanner answer = new Scanner(System.in);
        while (true) {
            System.out.println("                         -Programma upDetector-      ");
            System.out.println(" _______________________________________________________________________");
            System.out.println("|Izvelne|                                                               |");
            System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
            System.out.println("|Sveicinati, ko velaties sodien darit? (Spied ENTER lai beigtu darbibas)|");
            System.out.println("| Registresanas (1)                                                     |");
            System.out.println("| Pierakstisanas (2)                                                    |");
            System.out.println("| Krasu shema (3)                                                       |");
            System.out.println("| Izslegt programmu (4)                                                 |");
            System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
            String userAnswer = safeReadLine("|Atbilde: ", answer);

            if (userAnswer.equals("")) {
                // Tukša ievade (ENTER) iziet no galvenās cilpas.
                app.clear();
                break;

            } else if (userAnswer.equals("1")) {
                // Izveido jaunu reģistrācijas objektu, reģistrē lietotāju un pievieno to CSV.
                app.clear();
                appFunkcijas register = new appFunkcijas();
                Lietotajs newUser = register.registracija();
                if (newUser != null) {
                    register.add(newUser);
                    ConsoleColors.println("[Registracija veiksmiga!]", ConsoleColors.GREEN);
                }

            } else if (userAnswer.equals("2")) {
                // Izveido jaunu pierakstīšanās objektu un apstrādā pieslēgšanos.
                app.clear();
                appFunkcijas login = new appFunkcijas();
                login.pierakstisanas();

            } else if (userAnswer.equals("3")) {
                app.clear();
                // Šī cilpa apstrādā krāsu shēmas izvēli un atkārtoti jautā, ja ievade nav derīga.
                while (true) {
                    System.out.println("       -Programma upDetector-  ");
                    System.out.println(" __________________________________");
                    System.out.println("|    Vai velies tekstu ar krasam?  |");
                    System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
                    System.out.println("| Ja (1)                           |");
                    System.out.println("| Ne (2)                           |");
                    System.out.println("| Atpakal (3)                      |");
                    System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
                    String colorAnswer = safeReadLine("|Atbilde: ", answer);
                    System.out.println("____________________________________");
                    if (colorAnswer.equals("1")) {
                        // Ieslēdz krāsaino tekstu, iestatot globālo karogu uz 1.
                        App.colors = 1;
                        app.clear();
                        ConsoleColors.println("[Komanda izpildita]", ConsoleColors.GREEN);
                        System.out.println();
                        break;
                    } else if (colorAnswer.equals("2")) {
                        // Izslēdz krāsaino tekstu, iestatot globālo karogu uz 0.
                        App.colors = 0;
                        app.clear();
                        ConsoleColors.println("[Komanda izpildita]", ConsoleColors.GREEN);
                        System.out.println();
                        break;
                    } else if (colorAnswer.equals("3")) {
                        app.clear();
                        break;
                    } else {
                        app.clear();
                        ConsoleColors.println("[Kluda: Nederiga izvele! Vajadzeja izvelieties 1, 2 vai 3.]", ConsoleColors.RED);
                        System.out.println();
                    }
                }

            } else if (userAnswer.equals("4")) {
                app.clear();
                // Šī cilpa apstrādā programmas aizvēršanas apstiprinājumu.
                while (true) {
                    System.out.println("       -Programma upDetector-  ");
                    System.out.println(" ___________________________________");
                    System.out.println("|    Vai velies izslegt programmu?  |");
                    System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
                    System.out.println("| Ja (1)                            |");
                    System.out.println("| Ne (2)                            |");
                    System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
                    String exitAnswer = safeReadLine("|Atbilde: ", answer);
                    System.out.println("____________________________________");
                    if (exitAnswer.equals("1")) {
                        // Apstiprina izslēgšanu un izbeidz programmu.
                        app.clear();
                        app.exit();
                        return;
                    } else if (exitAnswer.equals("2")) {
                        ConsoleColors.println("[Programma nav izslegta!]", ConsoleColors.YELLOW);
                        app.clear();
                        break;
                    } else {
                        app.clear();
                        ConsoleColors.println("[Kluda: Nederiga izvele! Vajadzeja izvelieties 1 vai 2.]", ConsoleColors.RED);
                        System.out.println();
                    }
                }

            } else {
                app.clear();
                ConsoleColors.println("[Nederiga izvele! Ludzu izvelieties 1-4 vai spiediet ENTER, lai izietu.]", ConsoleColors.RED);
                System.out.println();
            }
        }
    }

    // funkcija main pieņem String[] tipa vērtību args un atgriež void tipa vērtību nav
    // Programmas ieejas punkts. Pārbauda un nodrošina datu mapes esamību, tad palaiž galveno izvēlni.
    public static void main(String[] args) {
        // Nodrošina ka "data" mape eksistē pirms programmas palaišanas.
        CsvFileHandler.ensureDataFolder();
        File folder = CsvFileHandler.ensureDataFolder();
        System.out.println("Data folder path: " + folder.getAbsolutePath());
        System.out.println("Exists: " + folder.exists());
        System.out.println("Is directory: " + folder.isDirectory());
        App tests = new App();
        tests.App();
    }
}
