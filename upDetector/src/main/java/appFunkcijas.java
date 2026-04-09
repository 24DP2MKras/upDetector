import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class appFunkcijas {
    public List<Lietotajs> users; 

    public appFunkcijas(){
        this.users = new ArrayList<>();
    }


    Scanner answer = new Scanner(System.in);
    
    // Helper method to find the data folder by traversing up and down the directory tree
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
            System.out.print("Ievadiet savu vardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            vards = answer.nextLine();
            if(vards.isEmpty()) return null;
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
            System.out.print("Ievadiet savu uzvardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            uzvards = answer.nextLine();
            if(uzvards.isEmpty()) return null;
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
                    System.out.println("[Ludzu ievadiet derigu uzvardu (piemeram: Berzins)!]");
                }
            }
        }
        while(true) {
            System.out.print("Ievadiet savu unikalo segvardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            segvards = answer.nextLine();
            if(segvards.isEmpty()) return null;
            if (CsvFileHandler.checkUserExists(segvards) == true) {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Segvards jau eksiste, ludzu izvelieties citu segvardu!]\u001B[0m");
                } else {
                    System.out.println("[Segvards jau eksiste, ludzu izvelieties citu segvardu!]");
                }
                continue;
            }
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
            System.out.print("Ievadiet savu E-pastu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            ePasts = answer.nextLine();
            if(ePasts.isEmpty()) return null;
            if (ePasts.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {                
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
                    System.out.println("[Ludzu ievadiet derigu E-pastu (piemeram: example@gmail.com )!]");
                }
            }
        }
        while(true) {
            parole = readPassword("Ievadiet savu unikalo paroli vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if(parole.isEmpty()) return null;
            if(parole.matches("^(?=.*\\d)[A-Za-z\\d-/.!@#$]{8,20}$")) {
                String parolesParbaude = readPassword("Ievadiet savu unikalo paroli velreiz: ");
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
                    System.out.println("[Ludzu ievadiet derigu paroli (piemeram: qwertyu7 )!]");
                }
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
        System.out.print("Ievadiet sava konta Segvardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
        String SegvardaLauks = answer.nextLine(); 
        if(SegvardaLauks.isEmpty()) break;
        if(CsvFileHandler.checkUserExists(SegvardaLauks)) {
            String ParolesLauks = readPassword("Ievadiet savu paroli: ");
            if(CsvFileHandler.checkUserLogin(SegvardaLauks, ParolesLauks)) {
                RegisteredUserUi ui = new RegisteredUserUi(SegvardaLauks, ParolesLauks);
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