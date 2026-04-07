import java.io.Console;
import java.util.*;
public class appFunkcijas {
    
    public List<Lietotajs> users; 

    public appFunkcijas(){
        this.users = new ArrayList<>();
    }


    Scanner answer = new Scanner(System.in);
    
    // Method to read password securely (hidden input)
    private String readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword(prompt);
            return new String(passwordChars);
        } else {
            // Fallback for environments without console (like some IDEs)
            System.out.print(prompt);
            return answer.nextLine();
        }
    }
    public Lietotajs registracija() {
        clear();
        System.out.println("    Tu esi registracijas sadala!");
        System.out.println();
        String vards = "", uzvards = "", segvards = "", ePasts = "", parole = "";
        while(true) {
            System.out.print("Ievadiet savu vardu!    ");
            vards = answer.nextLine();
            if(vards.matches("^[A-Za-zĀ-ž]{3,50}$")) {
                if (App.colors == 1) {
                    System.out.println("\u001B[32m[Dati ievaditi]\u001B[0m");
                    break;
                } else {
                    System.out.println("[Dati ievaditi]");
                    break;
                }
            } else {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Ludzu ievadiet derigu vardu (piemeram: Janis)!]\u001B[0m");
                } else {
                    System.out.println("[Ludzu ievadiet derigu vardu (piemeram: Janis)!]");
                }
            }
        }
        while(true) {
            System.out.print("Ievadiet savu uzvardu!    ");
            uzvards = answer.nextLine();
            if(uzvards.matches("^[A-Za-zĀ-ž]{4,60}$")) {
                if (App.colors == 1) {
                    System.out.println("\u001B[32m[Dati ievaditi]\u001B[0m");
                    break;
                } else {
                    System.out.println("[Dati ievaditi]");
                    break;
                }
            } else {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Ludzu ievadiet derigu uzvardu (piemeram: Berzins)!]\u001B[0m");
                } else {
                    System.out.println("[LLudzu ievadiet derigu uzvardu (piemeram: Berzins)!]");
                }
            }
        }
        while(true) {
            System.out.println("Ievadiet savu unikalo segvardu!    ");
            segvards = answer.nextLine();
            if(segvards.matches("^(?![!@#$]+$)[A-Za-z0-9!@#$]{4,20}$")) {
                if (App.colors == 1) {
                    System.out.println("\u001B[32m[Dati ievaditi]\u001B[0m");
                    break;
                } else {
                    System.out.println("[Dati ievaditi]");
                    break;
                }
            } else {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Ludzu ievadiet derigu segvardu (piemeram: ShadowX99)!]\u001B[0m");
                } else {
                    System.out.println("[Ludzu ievadiet derigu segvardu (piemeram: ShadowX99)!]");
                }
            }
        }
        while(true) {
            System.out.println("Ievadiet savu E-pastu!    ");
            ePasts = answer.nextLine();
            if (ePasts.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {                
                if (App.colors == 1) {
                    System.out.println("\u001B[32m[Dati ievaditi]\u001B[0m");
                    break;
                } else {
                    System.out.println("[Dati ievaditi]");
                    break;
                }
            } else {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Ludzu ievadiet derigu E-pastu (piemeram: example@gmail.com )!]\u001B[0m");
                } else {
                    System.out.println("[LLudzu ievadiet derigu E-pastu (piemeram: example@gmail.com )!]");
                }
            }
        }
        while(true) {
            parole = readPassword("Ievadiet savu unikalo paroli!    ");
            if(parole.matches("^(?=.*\\d)[A-Za-z\\d!@#$]{8,20}$")) {
                String parolesParbaude = readPassword("Ievadiet savu unikalo paroli velreiz!   ");
                if(parole.equals(parolesParbaude)) {
                    if (App.colors == 1) {
                        System.out.println("\u001B[32m[Dati ievaditi]\u001B[0m");
                        break;
                    } else {
                        System.out.println("[Dati ievaditi]");
                        break;
                    }
                }
                else {
                    if (App.colors == 1) {
                        System.out.println("\u001B[31m[Ludzu ievadiet tadu pasu paroli (piemeram: qwertyu7, parbaude: qwertyu7 )!]\u001B[0m");
                    }else {
                        System.out.println("[Ludzu ievadiet tadu pasu paroli (piemeram: qwertyu7, parbaude: qwertyu7 )!]");
                    }    
                }
            }
            else {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Ludzu ievadiet derigu paroli (piemeram: qwertyu7 )!]\u001B[0m");
                } else {
                    System.out.println("[LLudzu ievadiet derigu paroli (piemeram: qwertyu7 )!]");
                }
            }
        }
        return new Lietotajs(vards, uzvards, segvards, ePasts, parole);
    }
    public void add(Lietotajs user) {
        users.add(user);
        CsvFileHandler.addToCSV(users);
    }
        
    
    public void pierakstisanas() {
        clear();
        System.out.println("    Tu esi pierakstisanas sadala!   ");
        while(true) {
        System.out.print("Ievadiet sava konta Segvardu: ");
        String SegvardaLauks = answer.nextLine(); 
        if(CsvFileHandler.checkUserExists(SegvardaLauks)) {
            String ParolesLauks = readPassword("Ievadiet savu paroli: ");
            if(CsvFileHandler.checkUserLogin(SegvardaLauks, ParolesLauks)) {
                RegisteredUserUi ui = new RegisteredUserUi();
                if(App.colors == 1) {
                    System.out.println("\u001B[32m[Pierakstisanas veiksmiga!]\u001B[0m");
                    ui.RegisteredUserUi();
                } else {
                    System.out.println("[Pierakstisanas veiksmiga!]");
                    ui.RegisteredUserUi();
                }
            } else {
                if(App.colors == 1) {
                    System.out.println("\u001B[31m[Nepareiza parole!]\u001B[0m");
                } else {
                    System.out.println("[Nepareiza parole!]");
                }
            }
        } else {
            if(App.colors == 1) {
                System.out.println("\u001B[31m[Segvards nesakrit vai nav registrets, parliecinies ka ievadiji to pareizi!]\u001B[0m");
            } else {
                System.out.println("[Segvards nesakrit vai nav registrets, parliecinies ka ievadiji to pareizi!]");
            }
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