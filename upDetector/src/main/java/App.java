import java.util.*;
import java.awt.*;
public class App {
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
        System.out.print("Atbilde: ");
        String userAnswer = answer.nextLine();
        if(userAnswer.equals("")){
            break;
            }
        if(userAnswer.equals("1")) {
            //Te jabut Registracijai redirekcija
            registracija register = new registracija();
            register.registracija();
            break;
        }
        if(userAnswer.equals("2")) {
            //Te jabut Pierakstisanas redirekcija
            System.out.println("esi pierakstisanas sadala!");
            break;
        }
        if(userAnswer.equals("3")) {
            System.out.flush();
            System.out.println("    Vai velies tekstu ar krasam?   ");
            System.out.println("Ja (1)");
            System.out.println("Ne (2)");
            String userAnswer2 = answer.nextLine();
            if(userAnswer2.equals("1")){
                System.out.println("\u001B[32m[Komanda izpildita]\u001B[0m");
            }
            break;
        }
        }
    }
    
    public static void main(String[] args) {
        App tests = new App();
        tests.App();
    }
}
