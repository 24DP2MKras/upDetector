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
    public static int colors = 1;
    String userAnswer = "";
    private String parole;
    private String currentSegvards;
    public String regex = "^(https?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(/.*)?$";
    private void updateUserField(int fieldIndex, String newValue) {
    if (currentSegvards == null || currentSegvards.isBlank()) {
        System.out.println("Segvards nav iestatits.");
        return;
    }
     File dataFolder = findDataFolder(new File(System.getProperty("user.dir")));
    if (dataFolder == null) {
        System.out.println("Data folder not found!");
        return;
    }

    File file = new File(dataFolder, "UserData.csv");
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
        System.out.println("[Vertiba atjaunota!]");
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
        System.out.println("        -Programma upDetector-      ");
        System.out.println();
        System.out.println("Izvelne");

        if (currentSegvards.equals("unknown")) {
            System.out.print("Ievadiet segvardu: ");
            String segvards = answer.nextLine();
            System.out.print("Ievadiet paroli: ");
            String parole = answer.nextLine();
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
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.print("|Atbilde: "); 
        String userAnswer = answer.nextLine();
        if(userAnswer.equals("1")) {
            //Te jabut Vietnes parbaudes redirekcija
            app.clear();
            System.out.println("Esat vietnes parbaudes sadala!");
            System.out.println("Ievadiet vietni, kuru gribat ierakstīt un saglabat");
            System.out.println("Ievadiet vietnes nosaukumu (HTTPS) (piemeram [https://www.e-klase.lv]: ");
            System.out.println("Lai izietu spied ENTER ");
            System.out.print("Atbilde: ");
            userAnswer = answer.nextLine();
            if(userAnswer.equals("")) {
                app.clear();
                continue;
            } else if (userAnswer.matches(regex)){
            pingTests.httpPinger(userAnswer, currentSegvards);
            HttpPing ieraksts = pingTests.pedejoReiziSkatits();
            System.out.println(ieraksts);
            System.out.println();
            System.out.println("Vai gribat pievienot so vietni milakajam vietnem?");
            System.out.println("Ja (1)");
            System.out.println("Ne (2)");
            System.out.print("Atbilde: ");
            userAnswer = answer.nextLine();
            if(userAnswer.equals("1")){
                HttpPing ieraksts1 = pingTests.pedejoReiziSkatits1();
                
            }
            if(userAnswer.equals("2")){
                System.out.println("Vietne nav pievienota milakajam vietnem!");
            }
            System.out.print("Lai izietu spied ENTER ");
            userAnswer = answer.nextLine();
            if(userAnswer.equals("")) {
                app.clear();
                exit();
            }
            
        }
    }
    if(userAnswer.equals("2")) {
    app.clear();
    while(true) {
        System.out.println("Esat sava konta sadala!");
        System.out.println("Konta redigesana (1)");
        System.out.println("Konta dzesana (2)");
        System.out.println("Lai izietu spied ENTER ");
        System.out.print("Atbilde: ");
        userAnswer = answer.nextLine();

        if(userAnswer.equals("")) {
            app.clear();
            break; // ← back to outer menu
        }

        if(userAnswer.equals("1")){
            app.clear();
            System.out.println("Esat sava konta redigesanas sadala!");
            System.out.print("Ievadiet savu segvardu lai apstiprinatu: ");
            String enteredSegvards = answer.nextLine();
            System.out.print("Spied ENTER lai atgriztos uz sakuma: ");
            if (!enteredSegvards.trim().equals(this.currentSegvards.trim())) {
                System.out.println("Nepareizs segvards!");
                app.clear();
                continue;
            }
            System.out.print("Ievadiet sava konta paroli lai apstiprinatu: ");
            String enteredParole = answer.nextLine();
            System.out.print("Spied ENTER lai atgrieztos uz sakuma: ");
            if(this.parole == null || !enteredParole.trim().equals(this.parole.trim())) {
                System.out.println("Nepareiza parole!");
                app.clear();
                continue;
            }
            System.out.println("Ko jus velaties rediget? (Vards (1), Uzvards (2), Segvards (3), E-pasts (4), Parole (5))");
            System.out.print("Atbilde: ");
            String editChoice = answer.nextLine();
            int fieldIndex = -1;
            switch (editChoice){
                case "1": fieldIndex = 0; break;
                case "2": fieldIndex = 1; break;
                case "3": fieldIndex = 2; break;
                case "4": fieldIndex = 3; break;
                case "5": fieldIndex = 4; break;
                default: System.out.println("Nederiga atbilde!"); continue;
            }
            System.out.print("Ievadiet jauno vertibu: ");
            String newValue = answer.nextLine();
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
            System.out.print("Atbilde: ");
            userAnswer = answer.nextLine();
            if(userAnswer.equals("1")){
                System.out.print("Ievadiet savu Segvardu: ");
                String segvards = answer.nextLine();
                System.out.print("Ievadiet savu paroli: ");
                String parole = answer.nextLine();
                if(CsvFileHandler.checkUserLogin(segvards, parole)){
                    CsvFileHandler.removeFromCSV("UserData.csv", segvards, 2);
                    sakums.App();
                } else {
                    System.out.println(colors == 1 ? "\u001B[31m[Nepareizs segvards vai parole!]\u001B[0m" : "[Nepareizs segvards vai parole!]");
                }
            } else {
                System.out.println("Konts nav dzests!");
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
            System.out.print("Atbilde: ");
            while(true) {
                userAnswer = answer.nextLine();
            if(userAnswer.equals("1")){
                colors = 1;
                System.out.println("\u001B[32m[Komanda izpildita]\u001B[0m");
                app.clear();
                sakums.App();
            }
            if(userAnswer.equals("2")) {
                colors = 0;
                System.out.println("[Komanda izpildita]");
                app.clear();
                RegisteredUserUi();

            }
        }
             //vajag lai kods turpina darboties nevis beidzas un no jauna sakas
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
     
     private static File findDataFolder(File startDir) {
        // First, search UP the directory tree
        File current = startDir;
        for (int i = 0; i < 10; i++) {
            File data = new File(current, "data");
            if (data.exists() && data.isDirectory()) {
                return data;
            }
            if (current.getParentFile() != null) {
                current = current.getParentFile();
            } else {
                break;
            }
        }
        
        // Then, search DOWN the directory tree
        return findDataFolderDown(startDir);
    }
    
    private static File findDataFolderDown(File dir) {
        File data = new File(dir, "data");
        if (data.exists() && data.isDirectory()) {
            return data;
        }
        
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File found = findDataFolderDown(file);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return null;
    }
     
}
