import java.util.Scanner;

public class RegisteredUserUi {
    appFunkcijas app = new appFunkcijas();
    App sakums = new App();
    httpPing pingTests = new httpPing();
    public static int colors = 1;

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
            pingTests.httpPinger(userAnswer);
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
            System.out.println("Ievadiet vietni, kuru gribat atrast");
            System.out.println("Atbilde: ");
                userAnswer = answer.nextLine();

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
}
