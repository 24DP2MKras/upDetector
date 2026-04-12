import java.io.File;
import java.util.Scanner;

public class App {
    public static int colors;
    appFunkcijas app = new appFunkcijas();

    private String safeReadLine(String prompt, Scanner scanner) {
        try {
            System.out.print(prompt);
            return scanner.nextLine();
        } catch (Exception e) {
            ConsoleColors.println("[Ievades kluda: " + e.getMessage() + "]", ConsoleColors.RED);
            return "";
        }
    }

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
                app.clear();
                break;

            } else if (userAnswer.equals("1")) {
                app.clear();
                appFunkcijas register = new appFunkcijas();
                Lietotajs newUser = register.registracija();
                if (newUser != null) {
                    register.add(newUser);
                    ConsoleColors.println("[Registracija veiksmiga!]", ConsoleColors.GREEN);
                }

            } else if (userAnswer.equals("2")) {
                app.clear();
                appFunkcijas login = new appFunkcijas();
                login.pierakstisanas();

            } else if (userAnswer.equals("3")) {
                app.clear();
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
                        colors = 1;
                        app.clear();
                        ConsoleColors.println("[Komanda izpildita]", ConsoleColors.GREEN);
                        System.out.println();
                        break;
                    } else if (colorAnswer.equals("2")) {
                        colors = 0;
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

    public static void main(String[] args) {
        CsvFileHandler.ensureDataFolder();
        File folder = CsvFileHandler.ensureDataFolder();
        System.out.println("Data folder path: " + folder.getAbsolutePath());
        System.out.println("Exists: " + folder.exists());
        System.out.println("Is directory: " + folder.isDirectory());
        App tests = new App();
        tests.App();
    }
}