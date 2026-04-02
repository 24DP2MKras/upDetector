import java.util.Scanner;

public class RegisteredUserUi {
    public static int colors = 1;

     public void RegisteredUserUi(){
        Scanner answer = new Scanner(System.in);
        System.out.println("        -Programma upDetector-      ");
        System.out.println();
        System.out.println("Izvelne");
        while(true) {
        System.out.println("Sveicinati, ko velaties sodien darit? (Spied ENTER lai beigtu darbības)");
        System.out.println("Vietnes parbaude (1)  ");
        System.out.println("Vietnes meklesana (2)");
        System.out.println("Konts (3)  ");
        System.out.println("Izrakstities (4)");
        System.out.print("Atbilde: ");
        String userAnswer = answer.nextLine();
        if(userAnswer.equals("1")) {
            //Te jabut Vietnes parbaudes redirekcija
            System.out.println("Esat vietnes parbaudes sadala!");
            System.out.println("Ierakstat vietni kuru gribat parbaudit");
            System.out.println("Atbilde: ");
                userAnswer = answer.nextLine();
            
            break;
        }
        if(userAnswer.equals("2")){
            // Te jabut vietnes meklesana redirekcija
            System.out.println("Esat vietnes meklesana sadala!");
            System.out.println("Ievadiet vietni, kuru gribat atrast");
            System.out.println("Atbilde: ");
                userAnswer = answer.nextLine();

        }
        if(userAnswer.equals("3")) {
            //Te jabut Konta redirekcija
            System.out.println("Esat sava konta sadala!");
            System.out.println("Konta redigesana (1)");
            System.out.println("Konta dzesana (2)");
            System.out.println("Atbilde: ");
                userAnswer = answer.nextLine();
            if(userAnswer.equals("1")){
                int colors = 1;
            }
            if(userAnswer.equals("2")){
                int colors = 0;
            }
            break;
        }
        if(userAnswer.equals("4")) {
            System.out.println("Esat sava konta izrakstisanas sadala!");
            System.out.println("    Vai tiesam velaties izrakstities?    ");
            System.out.println("Ja (1)");
            System.out.println("Ne (2)");
            System.out.print("Atbilde: ");
                userAnswer = answer.nextLine();
            if(userAnswer.equals("1")){
                int colors = 1;
                System.out.println("\u001B[32m[Komanda izpildita]\u001B[0m");
            }
            if(userAnswer.equals("2")) {
                int colors = 0;
                System.out.println("[Komanda izpildita]");
            }
            break; //vajag lai kods turpina darboties nevis beidzas un no jauna sakas
        }

        if (userAnswer.equals("")) {
            break;
            }
        }
    
     }
     public static void main(String[] args) {
        RegisteredUserUi ui = new RegisteredUserUi();
        ui.RegisteredUserUi();
     }
}
