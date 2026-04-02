import java.util.*;
import java.awt.*;
public class appFunkcijas {
    public String vards;
    public String uzvards;
    public String segvards;
    public String ePasts;
    public String parole;
    
    public void registracija() {
        System.out.println("    Tu esi registracijas sadala!");
        System.out.println();
        Scanner answer = new Scanner(System.in);
        while(true) {
            System.out.print("Ievadiet savu vardu!    ");
            String vards = answer.nextLine();
            if(vards.matches("^[A-Za-zĀ-ž]{1,50}$")) {
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
            if(uzvards.matches("^[A-Za-zĀ-ž]{1,60}$")) {
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
            if(segvards.matches("^[A-Za-zĀ-ž]{1,50}$")) {
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
    }
}
