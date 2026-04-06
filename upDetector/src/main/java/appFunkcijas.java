import java.util.*;
public class appFunkcijas {
    public String vards;
    public String uzvards;
    public String segvards;
    public String ePasts;
    public String parole;
    public String parolesParbaude;

    public List<Lietotajs> registracija; 

    public appFunkcijas(){
        this.registracija = new ArrayList<>();
    }


    Scanner answer = new Scanner(System.in);
    public void registracija() {
        System.out.println("    Tu esi registracijas sadala!");
        System.out.println();
        while(true) {
            System.out.print("Ievadiet savu vardu!    ");
            String vards = answer.nextLine();
            if(vards.matches("^[A-Za-zĀ-ž]{3,50}$")) {
                this.vards = vards;
                if (App.colors == 1) {
                    System.out.println("\u001B[32m[Dati ievaditi]\u001B[0m");
                    break;
                } else {
                    System.out.println("[Dati ievaditi]");
                    break;
                }
            } else {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Lūdzu ievadiet derīgu vārdu (piemeram: Janis)!]\u001B[0m");
                } else {
                    System.out.println("[Lūdzu ievadiet derīgu vārdu (piemeram: Janis)!]");
                }
                
            }
        }
        while(true) {
            System.out.print("Ievadiet savu uzvardu!    ");
            String uzvards = answer.nextLine();
            if(uzvards.matches("^[A-Za-zĀ-ž]{4,60}$")) {
                this.uzvards = uzvards;
                if (App.colors == 1) {
                    System.out.println("\u001B[32m[Dati ievaditi]\u001B[0m");
                    break;
                } else {
                    System.out.println("[Dati ievaditi]");
                    break;
                }
            } else {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Lūdzu ievadiet derīgu uzvārdu (piemeram: Bērziņš)!]\u001B[0m");
                } else {
                    System.out.println("[Lūdzu ievadiet derīgu uzvārdu (piemeram: Bērziņš)!]");
                }
                
            }
        }
        while(true) {
            System.out.println("Ievadiet savu unikalo segvardu!    ");
            String segvards = answer.nextLine();
            if(segvards.matches("^(?![!@#$]+$)[A-Za-z0-9!@#$]{4,20}$")) {
                this.segvards = segvards;
                if (App.colors == 1) {
                    System.out.println("\u001B[32m[Dati ievaditi]\u001B[0m");
                    break;
                } else {
                    System.out.println("[Dati ievaditi]");
                    break;
                }
            } else {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Lūdzu ievadiet derīgu segvārdu (piemeram: ShadowX99)!]\u001B[0m");
                } else {
                    System.out.println("[Lūdzu ievadiet derīgu segvārdu (piemeram: ShadowX99)!]");
                }
                
            }
        }
        while(true) {
            System.out.println("Ievadiet savu E-pastu!    ");
            String ePasts = answer.nextLine();
            if (ePasts.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {                this.ePasts = ePasts;
                if (App.colors == 1) {
                    System.out.println("\u001B[32m[Dati ievaditi]\u001B[0m");
                    break;
                } else {
                    System.out.println("[Dati ievaditi]");
                    break;
                }
            } else {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Lūdzu ievadiet derīgu E-pastu (piemeram: example@gmail.com )!]\u001B[0m");
                } else {
                    System.out.println("[Lūdzu ievadiet derīgu E-pastu (piemeram: example@gmail.com )!]");
                }
                
            }
        }
        while(true) {
            System.out.print("Ievadiet savu unikalo paroli!    ");
            String parole = answer.nextLine();
            if(parole.matches("^(?=.*\\d)[A-Za-z\\d!@#$]{8,20}$")) {
                System.out.print("Ievadiet savu unikalo paroli velreiz!   ");
                String parolesParbaude = answer.nextLine();
                if(parole.equals(parolesParbaude)) {
                    this.parole = parole;
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
                        System.out.println("\u001B[31m[Lūdzu ievadiet tadu pašu paroli (piemeram: qwertyu7, parbaude: qwertyu7 )!]\u001B[0m");
                    }else {
                        System.out.println("[Lūdzu ievadiet tadu pašu paroli (piemeram: qwertyu7, parbaude: qwertyu7 )!]");
                    }    
                }
            }
            else {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Lūdzu ievadiet derīgu paroli (piemeram: qwertyu7 )!]\u001B[0m");
                } else {
                    System.out.println("[Lūdzu ievadiet derīgu paroli (piemeram: qwertyu7 )!]");
                }
                
            }
        }
    }
    public void add() {
        Lietotajs jaunsLietotajs = new Lietotajs(this.vards, this.uzvards, this.segvards, this.ePasts, this.parole);
        registracija.add(jaunsLietotajs);
        CsvFileHandler.addToCSV(registracija);
    }
        
    
    public void pierakstisanas() {
        System.out.println("    Tu esi pierakstisanas sadala!   ");
        while(true) {
        System.out.print("Ievadiet sava konta Segvardu: ");
        String SegvardaLauks = answer.nextLine(); 
        if(CsvFileHandler.checkUserExists(SegvardaLauks)) {
            System.out.print("Ievadiet savu paroli: ");
            String ParolesLauks = answer.nextLine();
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
}