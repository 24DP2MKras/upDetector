import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RegisteredUserUi {
    App sakums = new App();
    appFunkcijas app = new appFunkcijas();
    RegisteredUserFunkcijas pingTests = new RegisteredUserFunkcijas();
    private String parole;
    private String currentSegvards;
    public String regex = "^(https?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(/.*)?$";
    private static final String NAME_REGEX = "^[A-Za-z\\u00C4-\\u017E]{3,50}$";
    private static final String SURNAME_REGEX = "^[A-Za-z\\u00C4-\\u017E]{4,60}$";
    private static final String USERNAME_REGEX = "^(?![!@#$]+$)[A-Za-z0-9!@#$]{4,20}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)[A-Za-z\\d-/.!@#$]{8,20}$";

    private String safeReadLine(String prompt, Scanner scanner) {
        try {
            System.out.print(prompt);
            return scanner.nextLine();
        } catch (Exception e) {
            ConsoleColors.println("[Ievades kluda: " + e.getMessage() + "]", ConsoleColors.RED);
            return "";
        }
    }

    private void updateUserField(int fieldIndex, String newValue) {
        if (currentSegvards == null || currentSegvards.isBlank()) {
            System.out.println("Segvards nav iestatits.");
            return;
        }

        File datafolder = CsvFileHandler.ensureDataFolder();
        File file = new File(datafolder, "UserData.csv");
        if (!file.exists()) {
            System.out.println("UserData.csv not found!");
            return;
        }

        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    lines.add(line);
                    firstLine = false;
                    continue;
                }
                String[] row = line.split(",");
                if (row.length > 2 && row[2].trim().equals(currentSegvards.trim())) {
                    if (fieldIndex >= 0 && fieldIndex < row.length) {
                        row[fieldIndex] = newValue;
                        updated = true;
                    }
                    lines.add(String.join(",", row));
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        if (!updated) {
            System.out.println("User not found!");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String outputLine : lines) {
                bw.write(outputLine);
                bw.newLine();
            }
            ConsoleColors.println("[Vertiba atjaunota!]", ConsoleColors.GREEN);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public RegisteredUserUi(String username, String parole) {
        this.currentSegvards = username;
        this.parole = parole;
    }

    public RegisteredUserUi() {
        this.currentSegvards = "unknown";
        this.parole = " ";
    }

    public void RegisteredUserUi() {
        Scanner answer = new Scanner(System.in);
        if (currentSegvards.equals("unknown")) {
            String segvards = safeReadLine("Ievadiet segvardu: ", answer);
            String parole = safeReadLine("Ievadiet paroli: ", answer);
            if (CsvFileHandler.checkUserLogin(segvards, parole)) {
                this.currentSegvards = segvards;
                this.parole = parole;
                ConsoleColors.println("[Pieslegties veiksmigi!]", ConsoleColors.GREEN);
            } else {
                ConsoleColors.println("[Nepareizs segvards vai parole!]", ConsoleColors.RED);
                return;
            }
        }

        while (true) {
            System.out.println(" ________________________________________________________________________ ");
            System.out.println("|                        -Programma upDetector-                          |");
            System.out.println(" ________________________________________________________________________ ");
            System.out.println("|Izvelne|                                                                |");
            System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
            System.out.println("|Sveicinati, ko velaties sodien darit? (Spied ENTER lai beigtu darbibas) |");
            System.out.println("| Vietnes parbaude (1)                                                   |");
            System.out.println("| Konts (2)                                                              |");
            System.out.println("| Izrakstities (3)                                                       |");
            System.out.println("| Izslegt programmu (4)                                                  |");
            System.out.println("| Milako vietnu parbaude (5)                                             |");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            String userAnswer = safeReadLine("|Atbilde: ", answer);

            if (userAnswer.equals("")) {
                app.clear();
                if (handleLogout(answer)) {
                    return;
                }

            } else if (userAnswer.equals("1")) {
                app.clear();
                handleWebsiteCheck(answer);

            } else if (userAnswer.equals("2")) {
                app.clear();
                handleAccountMenu(answer);

            } else if (userAnswer.equals("3")) {
                app.clear();
                if (handleLogout(answer)) {
                    return;
                }

            } else if (userAnswer.equals("4")) {
                app.clear();
                if (handleProgramExit(answer)) {
                    return;
                }

            } else if (userAnswer.equals("5")) {
                app.clear();
                handleFavorites(answer);

            } else {
                app.clear();
                System.out.println();
                ConsoleColors.println("Kluda! Nederiga atbilde! Vajadzeja izveleties 1. 2. 3. 4. vai 5. atbildi", ConsoleColors.RED);
                System.out.println();
            }
        }
    }

    private void handleWebsiteCheck(Scanner answer) {
        System.out.println("Esat vietnes parbaudes sadala!");
        System.out.println("Ievadiet vietni, kuru gribat ierakstit un saglabat");
        System.out.println("Ievadiet vietnes nosaukumu (HTTPS) (piemeram [https://www.e-klase.lv]): ");
        System.out.println("Lai izietu spied ENTER ");

        while (true) {
            String websiteInput = safeReadLine("", answer);

            if (websiteInput.equals("")) {
                app.clear();
                return;
            }

            if (!websiteInput.matches(regex)) {
                app.clear();
                ConsoleColors.println("[Kluda: Nederigs vietnes URL! Ludzu ievadiet URL formatu https://... ]", ConsoleColors.RED);
                System.out.println();
                System.out.println("Ievadiet vietnes nosaukumu (HTTPS) (piemeram [https://www.e-klase.lv]): ");
                System.out.println("Lai izietu spied ENTER ");
                continue;
            }

            pingTests.httpPinger(websiteInput, currentSegvards);
            HttpPing ieraksts = pingTests.pedejoReiziSkatits();
            System.out.println(ieraksts);
            System.out.println();

            // Ask about favourites ? loop until valid answer
            while (true) {
                System.out.println("Vai gribat pievienot so vietni milakajam vietnem?");
                System.out.println("Ja (1)");
                System.out.println("Ne (2)");
                String favoriteAnswer = safeReadLine("Atbilde: ", answer);
                if (favoriteAnswer.equals("1")) {
                    pingTests.pedejoReiziSkatits1();
                    ConsoleColors.println("[Vietne pievienota milakajam vietnem!]", ConsoleColors.GREEN);
                    break;
                } else if (favoriteAnswer.equals("2")) {
                    ConsoleColors.println("[Vietne netika pievienota milakajam vietnem!]", ConsoleColors.YELLOW);
                    break;
                } else {
                    ConsoleColors.println("[Nederiga izvele! Ludzu izvelieties 1 vai 2.]", ConsoleColors.RED);
                }
            }

            safeReadLine("Lai izietu spied ENTER ", answer);
            app.clear();
            return;
        }
    }

    private boolean promptForCurrentCredentials(Scanner answer) {
        while (true) {
            String enteredSegvards = safeReadLine("Ievadiet savu segvardu lai apstiprinatu (ENTER - atpakal): ", answer);
            if (enteredSegvards.isBlank()) {
                return false;
            }
            if (!enteredSegvards.trim().equals(this.currentSegvards.trim())) {
                ConsoleColors.println("[Nepareizs segvards! Ludzu meginiet velreiz vai spiediet ENTER, lai atgrieztos. ]", ConsoleColors.RED);
                continue;
            }
            String enteredParole = app.readPassword("Ievadiet sava konta paroli lai apstiprinatu (ENTER - atpakal): ");
            if (enteredParole.isBlank()) {
                return false;
            }
            if (this.parole == null || !enteredParole.trim().equals(this.parole.trim())) {
                ConsoleColors.println("[Nepareiza parole! Ludzu meginiet velreiz vai spiediet ENTER, lai atgrieztos. ]", ConsoleColors.RED);
                continue;
            }
            return true;
        }
    }

    private String promptForEditedField(int fieldIndex, Scanner answer) {
        String prompt;
        String invalidMessage;
        String regexToCheck = null;

        switch (fieldIndex) {
            case 0:
                prompt = "Ievadiet jauno vardu vai spiediet ENTER, lai atgrieztos: ";
                invalidMessage = "[Ludzu ievadiet derigu vardu (piemeram: Janis)!]";
                regexToCheck = NAME_REGEX;
                break;
            case 1:
                prompt = "Ievadiet jauno uzvardu vai spiediet ENTER, lai atgrieztos: ";
                invalidMessage = "[Ludzu ievadiet derigu uzvardu (piemeram: Berzins)!]";
                regexToCheck = SURNAME_REGEX;
                break;
            case 2:
                prompt = "Ievadiet jauno segvardu vai spiediet ENTER, lai atgrieztos: ";
                invalidMessage = "[Ludzu ievadiet derigu segvardu (piemeram: ShadowX99)!]";
                regexToCheck = USERNAME_REGEX;
                break;
            case 3:
                prompt = "Ievadiet jauno e-pastu vai spiediet ENTER, lai atgrieztos: ";
                invalidMessage = "[Ludzu ievadiet derigu E-pastu (piemeram: example@gmail.com)!]";
                regexToCheck = EMAIL_REGEX;
                break;
            case 4:
                prompt = "Ievadiet jauno paroli vai spiediet ENTER, lai atgrieztos: ";
                invalidMessage = "[Ludzu ievadiet derigu paroli (piemeram: qwertyu7)!]";
                regexToCheck = PASSWORD_REGEX;
                break;
            default:
                return "";
        }

        while (true) {
            String newValue = safeReadLine(prompt, answer);
            if (newValue.isBlank()) {
                return "";
            }
            if (!newValue.matches(regexToCheck)) {
                ConsoleColors.println(invalidMessage, ConsoleColors.RED);
                continue;
            }
            if (fieldIndex == 2) {
                if (!newValue.trim().equals(this.currentSegvards.trim()) && CsvFileHandler.checkUserExists(newValue.trim())) {
                    ConsoleColors.println("[Segvards jau eksiste, ludzu izvelieties citu segvardu!]", ConsoleColors.RED);
                    continue;
                }
            }
            if (fieldIndex == 4) {
                String confirmPassword = safeReadLine("Ievadiet paroli velreiz: ", answer);
                if (confirmPassword.isBlank()) {
                    return "";
                }
                if (!newValue.equals(confirmPassword)) {
                    ConsoleColors.println("[Paroles nesakrit! Ludzu meginiet velreiz vai spiediet ENTER, lai atgrieztos. ]", ConsoleColors.RED);
                    continue;
                }
            }
            return newValue;
        }
    }

    private void handleAccountMenu(Scanner answer) {
        while (true) {
            System.out.println("Esat sava konta sadala!");
            System.out.println("Konta redigesana (1)");
            System.out.println("Konta dzesana (2)");
            System.out.println("Lai izietu spied ENTER ");
            String userAnswer = safeReadLine("Atbilde: ", answer);

            if (userAnswer.equals("")) {
                app.clear();
                return;

            } else if (userAnswer.equals("1")) {
                app.clear();
                System.out.println("Esat sava konta redigesanas sadala!");
                if (!promptForCurrentCredentials(answer)) {
                    app.clear();
                    continue;
                }
                while (true) {
                    System.out.println("Ko jus velaties rediget? (Vards (1), Uzvards (2), Segvards (3), E-pasts (4), Parole (5))");
                    System.out.println("Spiediet ENTER, lai atgrieztos uz konta sadalu.");
                    String editChoice = safeReadLine("Atbilde: ", answer);
                    if (editChoice.equals("")) {
                        app.clear();
                        break;
                    }
                    int fieldIndex = -1;
                    switch (editChoice) {
                        case "1": fieldIndex = 0; break;
                        case "2": fieldIndex = 1; break;
                        case "3": fieldIndex = 2; break;
                        case "4": fieldIndex = 3; break;
                        case "5": fieldIndex = 4; break;
                        default:
                            ConsoleColors.println("[Nederiga atbilde!]", ConsoleColors.RED);
                            continue;
                    }
                    String newValue = promptForEditedField(fieldIndex, answer);
                    if (newValue.isBlank()) {
                        continue;
                    }
                    updateUserField(fieldIndex, newValue);
                    if (fieldIndex == 2) this.currentSegvards = newValue;
                    if (fieldIndex == 4) this.parole = newValue;
                    ConsoleColors.println("[Izmainas ir saglabatas.]", ConsoleColors.GREEN);
                    app.clear();
                }

            } else if (userAnswer.equals("2")) {
                app.clear();
                System.out.println("Esat sava konta dzesanas sadala!");

                while (true) {
                    System.out.println("Vai tiesam velaties dzest savu kontu?");
                    System.out.println("Ja (1)");
                    System.out.println("Ne (2)");
                    String deleteAnswer = safeReadLine("Atbilde: ", answer);
                    if (deleteAnswer.equals("1")) {
                        if (promptForCurrentCredentials(answer)) {
                            CsvFileHandler.removeFromCSV("UserData.csv", this.currentSegvards, 2);
                            ConsoleColors.println("[Konts dzests!]", ConsoleColors.GREEN);
                            return;
                        }
                        break;
                    } else if (deleteAnswer.equals("2")) {
                        ConsoleColors.println("[Konta dzesana atcelta.]", ConsoleColors.YELLOW);
                        app.clear();
                        break;
                    } else {
                        ConsoleColors.println("[Nederiga izvele! Ludzu izvelieties 1 vai 2.]", ConsoleColors.RED);
                    }
                }

            } else {
                ConsoleColors.println("[Nederiga izvele! Ludzu izvelieties 1, 2, vai spiediet ENTER.]", ConsoleColors.RED);
            }
        }
    }

    private boolean handleLogout(Scanner answer) {
        while (true) {
            System.out.println("Vai tiesam velaties izrakstities?");
            System.out.println("Ja (1)");
            System.out.println("Ne (2)");
            System.out.println("Spiediet ENTER, lai atgrieztos uz konta sadalu.");
            String userAnswer = safeReadLine("Atbilde: ", answer);

            if (userAnswer.equals("")) {
                app.clear();
                return false;

            } else if (userAnswer.equals("1")) {
                ConsoleColors.println("[Izrakstisanas veiksmiga!]", ConsoleColors.GREEN);
                currentSegvards = "unknown";
                this.parole = " ";
                return true;

            } else if (userAnswer.equals("2")) {
                ConsoleColors.println("[Izrakstisanas atcelta.]", ConsoleColors.YELLOW);
                app.clear();
                return false;

            } else {
                ConsoleColors.println("[Nederiga izvele! Vajadzeja izvelieties 1 vai 2, vai spiediet ENTER, lai atgrieztos. ]", ConsoleColors.RED);
                app.clear();
            }
        }
    }

    private boolean handleProgramExit(Scanner answer) {
        while (true) {
            System.out.println("Vai tiesam velaties izslegt programmu?");
            System.out.println("Ja (1)");
            System.out.println("Ne (2)");
            System.out.println("Spiediet ENTER, lai atgrieztos uz lietotaja sadalu.");
            String userAnswer = safeReadLine("Atbilde: ", answer);

            if (userAnswer.isBlank()) {
                app.clear();
                return false;

            } else if (userAnswer.equals("1")) {
                ConsoleColors.println("[[Programma tiek izslegta...]", ConsoleColors.GREEN);
                app.clear();
                app.exit();
                return true;

            } else if (userAnswer.equals("2")) {
                ConsoleColors.println("[Programma nav izslegta!]", ConsoleColors.YELLOW);
                app.clear();
                return false;

            } else {
                ConsoleColors.println("[Nederiga izvele! Ludzu izvelieties 1 vai 2 vai spiediet ENTER, lai atgrieztos. ]", ConsoleColors.RED);
            }
        }
    }

    private void handleFavorites(Scanner answer) {
        ConsoleColors.println("[Esat milako vietnu sadala!]", ConsoleColors.GREEN);
        RegisteredUserFunkcijas pingFave = new RegisteredUserFunkcijas();
        List<Map<String, String>> favorites = CsvFileHandler.loadFavorites();
        boolean found = false;
        for (Map<String, String> fav : favorites) {
            if (fav.get("Segvards").equals(currentSegvards)) {
                found = true;
                String user = fav.get("Segvards");
                String site = fav.get("VietnesNosaukums");
                System.out.println("Parbaude tiek veikta vietnei: " + site + " lietotajam: " + user);
                pingFave.httpPinger(site, user);
            }
        }
        if (found) {
            ConsoleColors.println("[Visas milakas vietnes ir parbauditas!]", ConsoleColors.GREEN);
        } else {
            ConsoleColors.println("[Nav milako vietnu parbaudei.]", ConsoleColors.YELLOW);
        }
        System.out.println();
        safeReadLine("Nospied ENTER lai tiktu atpakal: ", answer);
        app.clear();
    }

    public static void main(String[] args) {
        RegisteredUserUi ui = new RegisteredUserUi();
        ui.RegisteredUserUi();
    }
}