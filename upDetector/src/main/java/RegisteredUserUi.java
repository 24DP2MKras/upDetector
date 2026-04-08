import java.util.Scanner;
import java.util.List;
import java.io.File;
public class RegisteredUserUi {
    appFunkcijas app = new appFunkcijas();
    App sakums = new App();
    RegisteredUserFunkcijas pingTests = new RegisteredUserFunkcijas();
    public static int colors = 1;
    private String currentUsername;

    public RegisteredUserUi(String username) {
        this.currentUsername = username;
    }

    public RegisteredUserUi() {
        this.currentUsername = "unknown";
    }


     public void RegisteredUserUi(){
        Scanner answer = new Scanner(System.in);
        app.clear();
        System.out.println("        -Programma upDetector-      ");
        System.out.println();
        System.out.println("Izvelne");
        while(true) {
        System.out.println("Sveicinati, ko velaties sodien darit? (Spied ENTER lai beigtu darbības)");
        System.out.println("Vietnes parbaude (1) ");
        System.out.println("Vietnes meklesana (2) ");
        System.out.println("Konts (3) ");
        System.out.println("Izrakstities (4)");
        System.out.print("Atbilde: ");
        String userAnswer = answer.nextLine();
        if(userAnswer.equals("1")) {
            //Te jabut Vietnes parbaudes redirekcija
            app.clear();
            System.out.println("Esat vietnes parbaudes sadala!");
            System.out.println("Ierakstat vietni kuru gribat parbaudit");
            System.out.print("Atbilde: ");
            userAnswer = answer.nextLine();
            pingTests.httpPinger(userAnswer, currentUsername);
            System.out.println(pingTests);
            System.out.println();
            System.out.print("Lai izietu spied ENTER ");
            userAnswer = answer.nextLine();
            if(userAnswer.equals("")) {
                app.clear();
                exit();
            }
            
        }
        if(userAnswer.equals("2")){
            // Te jabut vietnes meklesana redirekcija
            app.clear();
            System.out.println("Esat vietnes meklesana sadala!");
            System.out.println("Ievadiet vietni, kuru gribat ierakstīt un saglabat");
            System.out.println("Ievadiet vietnes nosaukumu (HTTPS) (piemeram [https://www.e-klase.lv]: ");
            userAnswer = answer.nextLine();
            pingTests.httpPinger(userAnswer, currentUsername);
            HttpPing ieraksts = pingTests.pedejoReiziSkatits();
            System.out.println(ieraksts);
            

        }
        if(userAnswer.equals("3")) {
            //Te jabut Konta redirekcija
            app.clear();
            System.out.println("Esat sava konta sadala!");
            System.out.println("Konta redigesana (1)");
            System.out.println("Konta dzesana (2)");
            System.out.println("Atbilde: ");
                userAnswer = answer.nextLine();
            if(userAnswer.equals("1")){
                app.clear();
            }
            if(userAnswer.equals("2")){
                app.clear();
                System.out.println("Esat sava konta dzesanas sadala!");
                System.out.println("Vai tiesam velaties dzest savu kontu?");
                System.out.println("Ja (1)");
                System.out.println("Ne (2)");
                System.out.println("Atbilde: ");
                userAnswer = answer.nextLine();
                if(userAnswer.equals("1")){
                    System.out.println("Ievadiet savu Segvardu!    ");
                    String segvards = answer.nextLine();
                    System.out.println("Ievadiet savu paroli!    ");
                    String parole = answer.nextLine();
                    if(CsvFileHandler.checkUserLogin(segvards, parole)){
                        CsvFileHandler.removeFromCSV("UserData.csv", segvards, 2);
                    } else {
                        if(colors == 1){
                            System.out.println("\u001B[31m[Nepareizs segvards vai parole!]\u001B[0m");
                        } else {
                            System.out.println("[Nepareizs segvards vai parole!]");
                        }
                    }
            }
        }
    }
        if(userAnswer.equals("4")) {
            app.clear();
            System.out.println("Esat sava konta izrakstisanas sadala!");
            System.out.println("    Vai tiesam velaties izrakstities?    ");
            System.out.println("Ja (1)");
            System.out.println("Ne (2)");
            System.out.print("Atbilde: ");
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
