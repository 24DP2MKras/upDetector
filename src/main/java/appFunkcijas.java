import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class appFunkcijas {
    public List<Lietotajs> users; 

    public appFunkcijas(){
        this.users = new ArrayList<>();
    }


    Scanner answer = new Scanner(System.in);

    private String safeReadLine(String prompt) {
        try {
            System.out.print(prompt);
            return answer.nextLine();
        } catch (Exception e) {
            ConsoleColors.println("[Ievades kluda: " + e.getMessage() + "]", ConsoleColors.RED);
            return "";
        }
    }

    // Method to read password securely (hidden input)
    private String readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword(prompt);
            return new String(passwordChars);
        } else {
            // Fallback for environments without console (like some IDEs)
            return safeReadLine(prompt);
        }
    }
    public Lietotajs registracija() {
        clear();
        System.out.println("    Tu esi registracijas sadala!");
        System.out.println();
        
        String vards = "", uzvards = "", segvards = "", ePasts = "", parole = "";
        while(true) {
            vards = safeReadLine("Ievadiet savu vardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if(vards.isEmpty()) return null;
            if(vards.matches("^[A-Za-zĀ-ž]{3,50}$")) {
                ConsoleColors.println("[Dati ievaditi]", ConsoleColors.GREEN);
                break;
            } else {
                ConsoleColors.println("[Ludzu ievadiet derigu vardu (piemeram: Janis)!]", ConsoleColors.RED);
            }
        }
        while(true) {
            uzvards = safeReadLine("Ievadiet savu uzvardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if(uzvards.isEmpty()) return null;
            if(uzvards.matches("^[A-Za-zĀ-ž]{4,60}$")) {
                ConsoleColors.println("[Dati ievaditi]", ConsoleColors.GREEN);
                break;
            } else {
                ConsoleColors.println("[Ludzu ievadiet derigu uzvardu (piemeram: Berzins)!]", ConsoleColors.RED);
            }
        }
        while(true) {
            segvards = safeReadLine("Ievadiet savu unikalo segvardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if(segvards.isEmpty()) return null;
            if (CsvFileHandler.checkUserExists(segvards) == true) {
                ConsoleColors.println("[Segvards jau eksiste, ludzu izvelieties citu segvardu!]", ConsoleColors.RED);
                continue;
            }
            if(segvards.matches("^(?![!@#$]+$)[A-Za-z0-9!@#$]{4,20}$")) {
                ConsoleColors.println("[Dati ievaditi]", ConsoleColors.GREEN);
                break;
            } else {
                ConsoleColors.println("[Ludzu ievadiet derigu segvardu (piemeram: ShadowX99)!]", ConsoleColors.RED);
            }
        }
        while(true) {
            ePasts = safeReadLine("Ievadiet savu E-pastu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if(ePasts.isEmpty()) return null;
            if (ePasts.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {                
                ConsoleColors.println("[Dati ievaditi]", ConsoleColors.GREEN);
                break;
            } else {
                ConsoleColors.println("[Ludzu ievadiet derigu E-pastu (piemeram: example@gmail.com )!]", ConsoleColors.RED);
            }
        }
        while(true) {
            parole = readPassword("Ievadiet savu unikalo paroli vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if(parole.isEmpty()) return null;
            if(parole.matches("^(?=.*\\d)[A-Za-z\\d-/.!@#$]{8,20}$")) {
                String parolesParbaude = readPassword("Ievadiet savu unikalo paroli velreiz: ");
                if(parole.equals(parolesParbaude)) {
                    ConsoleColors.println("[Dati ievaditi]", ConsoleColors.GREEN);
                    break;
                }
                else {
                    ConsoleColors.println("[Ludzu ievadiet tadu pasu paroli (piemeram: qwertyu7, parbaude: qwertyu7 )!]", ConsoleColors.RED);
                }
            }
            else {
                ConsoleColors.println("[Ludzu ievadiet derigu paroli (piemeram: qwertyu7 )!]", ConsoleColors.RED);
            }
        }
        return new Lietotajs(vards, uzvards, segvards, ePasts, parole);
    }
    public void add(Lietotajs user) {
        users.add(user);
        CsvFileHandler.saveLine("UserData.csv", user.toString(), "Vards,Uzvards,Segvards,Epasts,Parole,Datums/Laiks");
    }
        
    
    public void pierakstisanas() {
        clear();
        System.out.println("    Tu esi pierakstisanas sadala!   ");
        while(true) {
            String SegvardaLauks = safeReadLine("Ievadiet sava konta Segvardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if(SegvardaLauks.isEmpty()) break;
        if(CsvFileHandler.checkUserExists(SegvardaLauks)) {
            String ParolesLauks = readPassword("Ievadiet savu paroli: ");
            if(CsvFileHandler.checkUserLogin(SegvardaLauks, ParolesLauks)) {
                RegisteredUserUi ui = new RegisteredUserUi(SegvardaLauks, ParolesLauks);
                ConsoleColors.println("[Pierakstisanas veiksmiga!]", ConsoleColors.GREEN);
                ui.RegisteredUserUi();
            } else {
                ConsoleColors.println("[Nepareiza parole!]", ConsoleColors.RED);
            }
        } else {
            ConsoleColors.println("[Segvards nesakrit vai nav registrets, parliecinies ka ievadiji to pareizi!]", ConsoleColors.RED);
        }
        }
    }
    public void exit() {
        System.exit(0);
    }
    public void clear() {
        System.out.print("\033[2J\033[H");
    }
}