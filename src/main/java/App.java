import java.io.File;
import java.util.Scanner;
public class App {
    public static int colors; // 1 ir krāsas, 0 nav krāsu
    public String todo() {
        return "This method must mean theres something to do";
        //make a main menu screen for the user
        //make it so the option the user makes redirect them to somewhere (not implemented yet)
        //make it all correctly row by row so the output of main menu isnt scrambled
        //remember to enjoy using java and like java!!
    }
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

    public void App(){
        app.clear();
        Scanner answer = new Scanner(System.in);
        while(true) {
            try {
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
        if(userAnswer.equals("1")) {
            //Te jabut Registracijai redirekcija
            app.clear();
            appFunkcijas register = new appFunkcijas();
            Lietotajs newUser = register.registracija();
            if(newUser != null) {
                register.add(newUser);
                ConsoleColors.println("[Registrācija veiksmīga!]", ConsoleColors.GREEN);
            }
            continue;
        }
        if(userAnswer.equals("2")) {
            //Te jabut Pierakstisanas redirekcija
            app.clear();
            appFunkcijas login = new appFunkcijas();
            login.pierakstisanas();
            continue;
        }
        if(userAnswer.equals("3")) {
            app.clear();
            System.out.println("       -Programma upDetector-  ");
            System.out.println(" __________________________________");
            System.out.println("|    Vai velies tekstu ar krasam?  |");
            System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
            System.out.println("| Ja (1)                           |");
            System.out.println("| Ne (2)                           |");
            System.out.println("| Atpakal (3)                      |");
            System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
            userAnswer = safeReadLine("|Atbilde: ", answer);
            System.out.println("____________________________________");
            if(userAnswer.equals("1")){
                colors = 1;
                ConsoleColors.println("[Komanda izpildita]", ConsoleColors.GREEN);
                app.clear();
                continue;
            } else if(userAnswer.equals("2")) {
                colors = 0;
                ConsoleColors.println("[Komanda izpildita]", ConsoleColors.GREEN);
                app.clear();
                continue;
            } else if(userAnswer.equals("3")){
                app.clear();
                continue;
            } else {
                ConsoleColors.println("[Nederiga izvele! Lūdzu izvēlieties 1, 2 vai 3.]", ConsoleColors.RED);
            }
             //vajag lai kods turpina darboties nevis beidzas un no jauna sakas
        }
        if (userAnswer.equals("4")) {
            //opcija iet uz sakumu jabut seit
             System.out.println("       -Programma upDetector-  ");
            System.out.println(" ___________________________________4");
            System.out.println("|    Vai velies izslegt programmu?  |");
            System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
            System.out.println("| Ja (1)                            |");
            System.out.println("| Ne (2)                            |");
            System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
            userAnswer = safeReadLine("|Atbilde: ", answer);
            System.out.println("____________________________________");
            if(userAnswer.equals("1")){
                app.clear();
                app.exit();
            } else if(userAnswer.equals("2")) {
                System.out.println("Programma nav izslegta!");
                app.clear();
                continue;
            } else {
                ConsoleColors.println("[Nederiga izvele! Lūdzu izvēlieties 1 vai 2.]", ConsoleColors.RED);
            }
        }
        if (!userAnswer.equals("1") && !userAnswer.equals("2") && !userAnswer.equals("3") && !userAnswer.equals("4") && !userAnswer.equals("")) {
            ConsoleColors.println("[Nederiga izvele! Lūdzu izvēlieties 1-4 vai spiediet ENTER, lai izietu.]", ConsoleColors.RED);
        }
        if (userAnswer.equals("")) {
            app.clear();
            break;
        }
            } catch (Exception e) {
                ConsoleColors.println("[Neparasta kluda: " + e.getMessage() + "]", ConsoleColors.RED);
                break;
            }
        }
        
    }
    
    public static void main(String[] args) {
        CsvFileHandler.ensureDataFolder();
        System.out.println(CsvFileHandler.ensureDataFolder().getAbsolutePath());
        File folder = CsvFileHandler.ensureDataFolder();
        System.out.println("Data folder path: " + folder.getAbsolutePath());
        System.out.println("Exists: " + folder.exists());
        System.out.println("Is directory: " + folder.isDirectory());
        App tests = new App();
        tests.App();
    }
}