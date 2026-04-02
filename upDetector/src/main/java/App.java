import java.util.*;
import java.awt.*;
public class App {
    public static int colors = 1; // 1 ir krāsas, 0 nav krāsu
    public String todo() {
        return "This method must mean theres something to do";
        //make a main menu screen for the user
        //make it so the option the user makes redirect them to somewhere (not implemented yet)
        //make it all correctly row by row so the output of main menu isnt scrambled
        //remember to enjoy using java and like java!!
    }

    public void App(){
        Scanner answer = new Scanner(System.in);
        System.out.println("        -Programma upDetector-      ");
        System.out.println();
        System.out.println("Izvelne");
        while(true) {
        System.out.println("Sveicinati, ko velaties sodien darit? (Spied ENTER lai beigtu darbības)");
        System.out.println("Registresanas (1)  ");
        System.out.println("Pierakstisanas (2)  ");
        System.out.println("Krasu shema (3)");
        System.out.println("Atgriezties uz sakuma ekranu (4)");
        System.out.print("Atbilde: ");
        String userAnswer = answer.nextLine();
        if(userAnswer.equals("1")) {
            //Te jabut Registracijai redirekcija
            appFunkcijas register = new appFunkcijas();
            register.registracija();
            register.add();
            break;
        }
        if(userAnswer.equals("2")) {
            //Te jabut Pierakstisanas redirekcija
            appFunkcijas login = new appFunkcijas();
            login.pierakstisanas();
            break;
        }
        if(userAnswer.equals("3")) {
            System.out.flush();
            System.out.println("    Vai velies tekstu ar krasam?   ");
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
        if (userAnswer.equals("4")) {
            //opcija iet uz sakumu jabut seit
            App tests = new App();
            tests.App();
        }
        if (userAnswer.equals("")) {
            break;
            }
        }
    }
    
    public static void main(String[] args) {
        App tests = new App();
        tests.App();
    }
}
