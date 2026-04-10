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
    appFunkcijas app = new appFunkcijas();
    App sakums = new App();
    RegisteredUserFunkcijas pingTests = new RegisteredUserFunkcijas();
    String userAnswer = "";
    private String parole;
    private String currentSegvards;
    public String regex = "^(https?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(/.*)?$";

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
        this.parole =  parole;
    }

    public RegisteredUserUi() {
        this.currentSegvards = "unknown";
        this.parole = " ";
    }


     public void RegisteredUserUi(){
        Scanner answer = new Scanner(System.in);
        app.clear();
        if (currentSegvards.equals("unknown")) {
            String segvards = safeReadLine("Ievadiet segvardu: ", answer);
            String parole = safeReadLine("Ievadiet paroli: ", answer);
            if (CsvFileHandler.checkUserLogin(segvards, parole)) {
                this.currentSegvards = segvards;
                this.parole = parole;
                System.out.println("Pieslegties veiksmigi!");
            } else {
                System.out.println("Nepareizs segvards vai parole!");
                return;
            }
        }

        while(true) {
        System.out.println("                        -Programma upDetector-");
        System.out.println(" ________________________________________________________________________ ");
        System.out.println("|Izvelne|                                                                |");
        System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
        System.out.println("|Sveicinati, ko velaties sodien darit? (Spied ENTER lai beigtu darbības) |");
        System.out.println("| Vietnes parbaude (1)                                                   |");
        System.out.println("| Konts (2)                                                              |");
        System.out.println("| Izrakstities (3)                                                       |");
        System.out.println("| Izslegt programmu (4)                                                  |");
        System.out.println("| Milako vietnu parbaude (5)                                             |");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String userAnswer = safeReadLine("|Atbilde: ", answer);
        if(userAnswer.equals("1")) {
            app.clear();
            System.out.println("Esat vietnes parbaudes sadala!");
            System.out.println("Ievadiet vietni, kuru gribat ierakstīt un saglabat");
            System.out.println("Ievadiet vietnes nosaukumu (HTTPS) (piemeram [https://www.e-klase.lv]: ");
            System.out.println("Lai izietu spied ENTER ");
            while (true) {
                String websiteInput = safeReadLine("Atbilde: ", answer);
                if (websiteInput.equals("")) {
                    app.clear();
                    break;
                }
                if (!websiteInput.matches(regex)) {
                    ConsoleColors.println("[Nederigs vietnes URL! Lūdzu ievadiet URL formātā https://... ]", ConsoleColors.RED);
                    continue;
                }
                pingTests.httpPinger(websiteInput, currentSegvards);
                HttpPing ieraksts = pingTests.pedejoReiziSkatits();
                System.out.println(ieraksts);
                System.out.println();
                System.out.println("Vai gribat pievienot so vietni milakajam vietnem?");
                System.out.println("Ja (1)");
                System.out.println("Ne (2)");
                String favoriteAnswer = safeReadLine("Atbilde: ", answer);
                if(favoriteAnswer.equals("1")){
                    HttpPing ieraksts1 = pingTests.pedejoReiziSkatits1();
                    ConsoleColors.println("[Vietne pievienota milakajam vietnem!]", ConsoleColors.GREEN);
                } else if(favoriteAnswer.equals("2")){
                    ConsoleColors.println("[Vietne netika pievienota milakajam vietnem!]", ConsoleColors.YELLOW);
                } else {
                    ConsoleColors.println("[Nederiga izvele! Vietne netika pievienota.]", ConsoleColors.RED);
                }
                String exitAnswer = safeReadLine("Lai izietu spied ENTER ", answer);
                if(exitAnswer.equals("")) {
                    app.clear();
                    exit();
                }
                break;
            }
            continue;
    }
    if(userAnswer.equals("2")) {
    app.clear();
    while(true) {
        System.out.println("Esat sava konta sadala!");
        System.out.println("Konta redigesana (1)");
        System.out.println("Konta dzesana (2)");
        System.out.println("Lai izietu spied ENTER ");
        userAnswer = safeReadLine("Atbilde: ", answer);

        if(userAnswer.equals("")) {
            app.clear();
            break; // ← back to outer menu
        }

        if(userAnswer.equals("1")){
            app.clear();
            System.out.println("Esat sava konta redigesanas sadala!");
            String enteredSegvards = safeReadLine("Ievadiet savu segvardu lai apstiprinatu: ", answer);
            if (!enteredSegvards.trim().equals(this.currentSegvards.trim())) {
                System.out.println("Nepareizs segvards!");
                app.clear();
                continue;
            }
            String enteredParole = safeReadLine("Ievadiet sava konta paroli lai apstiprinatu: ", answer);
            if(this.parole == null || !enteredParole.trim().equals(this.parole.trim())) {
                System.out.println("Nepareiza parole!");
                app.clear();
                continue;
            }
            System.out.println("Ko jus velaties rediget? (Vards (1), Uzvards (2), Segvards (3), E-pasts (4), Parole (5))");
            String editChoice = safeReadLine("Atbilde: ", answer);
            int fieldIndex = -1;
            switch (editChoice){
                case "1": fieldIndex = 0; break;
                case "2": fieldIndex = 1; break;
                case "3": fieldIndex = 2; break;
                case "4": fieldIndex = 3; break;
                case "5": fieldIndex = 4; break;
                default: System.out.println("Nederiga atbilde!"); continue;
            }
            String newValue = safeReadLine("Ievadiet jauno vertibu: ", answer);
            updateUserField(fieldIndex, newValue);
            if(fieldIndex == 2) this.currentSegvards = newValue;
            if(fieldIndex == 4) this.parole = newValue;
            app.clear();
            continue; // ← back to account menu after edit
        }

        if(userAnswer.equals("2")){
            app.clear();
            System.out.println("Esat sava konta dzesanas sadala!");
            System.out.println("Vai tiesam velaties dzest savu kontu?");
            System.out.println("Ja (1)");
            System.out.println("Ne (2)");
            userAnswer = safeReadLine("Atbilde: ", answer);
            if(userAnswer.equals("1")){
                String segvards = safeReadLine("Ievadiet savu Segvardu: ", answer);
                String parole = safeReadLine("Ievadiet savu paroli: ", answer);
                if(CsvFileHandler.checkUserLogin(segvards, parole)){
                    CsvFileHandler.removeFromCSV("UserData.csv", segvards, 2);
                    ConsoleColors.println("[Konts dzests!]", ConsoleColors.GREEN);
                    sakums.App();
                } else {
                    ConsoleColors.println("[Nepareizs segvards vai parole!]", ConsoleColors.RED);
                }
            } else if(userAnswer.equals("2")) {
                ConsoleColors.println("[Konta dzesana atcelta.]", ConsoleColors.YELLOW);
            } else {
                ConsoleColors.println("[Nederiga izvele!]", ConsoleColors.RED);
            }
            continue; // ← back to account menu
        }
    }
    continue; // ← back to outer menu after breaking out
}
            if(userAnswer.equals("3")) {
            app.clear();
            System.out.println("Esat sava konta izrakstisanas sadala!");
            System.out.println("    Vai tiesam velaties izrakstities?    ");
            System.out.println("Ja (1)");
            System.out.println("Ne (2)");
            while(true) {
                userAnswer = safeReadLine("Atbilde: ", answer);
            if(userAnswer.equals("1")){
                App.colors = 1;
                ConsoleColors.println("[Izrakstīšanās veiksmīga!]", ConsoleColors.GREEN);
                currentSegvards = "unknown";
                parole = " ";
                app.clear();
                sakums.App();
            }
            if(userAnswer.equals("2")) {
                App.colors = 0;
                ConsoleColors.println("[Izrakstīšanās atcelta]", ConsoleColors.YELLOW);
                app.clear();
                RegisteredUserUi();

            } else {
                ConsoleColors.println("[Nederiga izvele!]", ConsoleColors.RED);
            }
        }
    }
            if(userAnswer.equals("4")) {
                System.out.println("Vai tiesam velaties izslegt programmu? ");
                System.out.println("Ja (1)");
                System.out.println("Ne (2)");
                userAnswer = safeReadLine("Atbilde: ", answer);
                if(userAnswer.equals("1")){
                    ConsoleColors.println("[Programma tiek izslēgta...]", ConsoleColors.GREEN);
                    app.clear();
                    app.exit();
                } else if(userAnswer.equals("2")) {
                    ConsoleColors.println("[Programma nav izslēgta!]", ConsoleColors.YELLOW);
                    app.clear();
                    RegisteredUserUi();
                } else {
                    ConsoleColors.println("[Nederiga izvele!]", ConsoleColors.RED);
                }
        }
            if (userAnswer.equals("5")) {
                app.clear();
                ConsoleColors.println("[Esat milako vietnu sadala!]", ConsoleColors.GREEN);
                RegisteredUserFunkcijas pingFave = new RegisteredUserFunkcijas();
                List<Map<String, String>> favorites = CsvFileHandler.loadFavorites();
                boolean found = false;
                for (Map<String, String> fav : favorites) {
                    if(fav.get("Segvards").equals(currentSegvards)) {
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
            userAnswer = safeReadLine("Nospied ENTER lai tiktu atpakal: ", answer);
            if(userAnswer.equals("")) {
                app.clear();
                RegisteredUserUi();
            }
             //vajag lai kods turpina darboties nevis beidzas un no jauna sakas
        }
        if(userAnswer.equals("4")) {
                System.out.println("Vai tiesam velaties izslegt programmu? ");
                
                System.out.println("Ja (1)");
                System.out.println("Ne (2)");
                userAnswer = safeReadLine("Atbilde: ", answer);
                if(userAnswer.equals("1")){
            app.clear();
            app.exit();
        } else if(userAnswer.equals("2")) {
            System.out.println("Programma nav izslegta!");
            app.clear();
            RegisteredUserUi();
        } else {
            ConsoleColors.println("[Nederiga izvele!]", ConsoleColors.RED);
        }
             }
        if (!userAnswer.equals("1") && !userAnswer.equals("2") && !userAnswer.equals("3") && !userAnswer.equals("4") && !userAnswer.equals("5") && !userAnswer.equals("")) {
            ConsoleColors.println("[Nederiga izvele! Lūdzu izvēlieties 1-5 vai spiediet ENTER, lai izietu.]", ConsoleColors.RED);
        }
        if (userAnswer.equals("")) {
            app.exit();
                } 
            }
        }
     public static void main(String[] args) {
        RegisteredUserUi ui = new RegisteredUserUi();
        ui.RegisteredUserUi();
     }

     public void exit() {
        RegisteredUserUi();
     } 
}
