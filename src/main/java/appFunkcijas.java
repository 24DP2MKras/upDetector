import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class appFunkcijas {
    // Lietotāju saraksts operatīvajā atmiņā (izmantots pirms saglabāšanas CSV datnē).
    public List<Lietotajs> users;

    // funkcija appFunkcijas pieņem nav parametru un atgriež void tipa vērtību nav
    // Konstruktors - inicializē tukšu lietotāju sarakstu.
    public appFunkcijas() {
        this.users = new ArrayList<>();
    }

    Scanner answer = new Scanner(System.in);

    // funkcija safeReadLine pieņem String tipa vērtību prompt un atgriež String tipa vērtību rezultatu
    // Droši nolasa rindu no skenera ar kļūdu apstrādi. Atgriež ievadīto tekstu vai tukšu virkni kļūdas gadījumā.
    private String safeReadLine(String prompt) {
        try {
            System.out.print(prompt);
            return answer.nextLine();
        } catch (Exception e) {
            ConsoleColors.println("[Ievades kluda: " + e.getMessage() + "]", ConsoleColors.RED);
            return "";
        }
    }

    // funkcija readPassword pieņem String tipa vērtību prompt un atgriež String tipa vērtību rezultatu
    // Droši nolasa paroli no konsoles ar slēptu ievadi (rakstzīmes nav redzamas).
    // Ja konsole nav pieejama (piemēram, IDE vidē), izmanto parastu safeReadLine kā rezerves risinājumu.
    public String readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            // System.console() ir pieejams, izmanto slēpto paroles ievadi.
            char[] passwordChars = console.readPassword(prompt);
            return new String(passwordChars);
        } else {
            // Rezerves risinājums vidēm bez konsoles, piemēram, dažās IDE.
            return safeReadLine(prompt);
        }
    }

    // funkcija registracija pieņem nav parametru un atgriež Lietotajs tipa vērtību rezultatu
    // Vada lietotāju caur reģistrācijas procesu, validējot katru lauku ar regex.
    // Atgriež jaunu Lietotajs objektu, ja visi lauki ir derīgi, vai null, ja lietotājs atceļ ar ENTER.
    public Lietotajs registracija() {
        clear();
        System.out.println("    Tu esi registracijas sadala!");
        System.out.println();

        String vards = "", uzvards = "", segvards = "", ePasts = "", parole = "";

        // Katru lauku pieprasām no lietotāja un atkārtoti pārbaudām līdz derīgai ievadei vai atcelšanai.
        while (true) {
            vards = safeReadLine("Ievadiet savu vardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if (vards.isEmpty()) return null;
            // Vārds var saturēt tikai burtus (latīņu un latviešu), 3-50 rakstzīmes.
            if (vards.matches("^[A-Za-zĀ-ž]{3,50}$")) {
                ConsoleColors.println("[Dati ievaditi]", ConsoleColors.GREEN);
                break;
            } else {
                ConsoleColors.println("[Ludzu ievadiet derigu vardu (piemeram: Janis)!]", ConsoleColors.RED);
            }
        }
        while (true) {
            uzvards = safeReadLine("Ievadiet savu uzvardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if (uzvards.isEmpty()) return null;
            // Uzvārds var saturēt tikai burtus (latīņu un latviešu), 4-60 rakstzīmes.
            if (uzvards.matches("^[A-Za-zĀ-ž]{4,60}$")) {
                ConsoleColors.println("[Dati ievaditi]", ConsoleColors.GREEN);
                break;
            } else {
                ConsoleColors.println("[Ludzu ievadiet derigu uzvardu (piemeram: Berzins)!]", ConsoleColors.RED);
            }
        }
        while (true) {
            segvards = safeReadLine("Ievadiet savu unikalo segvardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if (segvards.isEmpty()) return null;
            // Pārbauda, vai segvards jau eksistē CSV datnē, pirms pieļaut to reģistrācijai.
            if (CsvFileHandler.checkUserExists(segvards) == true) {
                ConsoleColors.println("[Segvards jau eksiste, ludzu izvelieties citu segvardu!]", ConsoleColors.RED);
                continue;
            }
            // Segvards var saturēt burtus, ciparus un !@#$ simbolus, 4-20 rakstzīmes, bet nedrīkst būt tikai simboli.
            if (segvards.matches("^(?![!@#$]+$)[A-Za-z0-9!@#$]{4,20}$")) {
                ConsoleColors.println("[Dati ievaditi]", ConsoleColors.GREEN);
                break;
            } else {
                ConsoleColors.println("[Ludzu ievadiet derigu segvardu (piemeram: ShadowX99)!]", ConsoleColors.RED);
            }
        }
        while (true) {
            ePasts = safeReadLine("Ievadiet savu E-pastu vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if (ePasts.isEmpty()) return null;
            // E-pasta adresei jāatbilst standarta formātam: lietotājs@domēns.izsaukums.
            if (ePasts.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                ConsoleColors.println("[Dati ievaditi]", ConsoleColors.GREEN);
                break;
            } else {
                ConsoleColors.println("[Ludzu ievadiet derigu E-pastu (piemeram: example@gmail.com )!]", ConsoleColors.RED);
            }
        }
        while (true) {
            parole = readPassword("Ievadiet savu unikalo paroli vai spiediet ENTER lai atgrieztos uz izvelni: ");
            if (parole.isEmpty()) return null;
            // Parole: 8-20 rakstzīmes, jāsatur vismaz viens cipars, atļauti burti, cipari un -/.!@#$ simboli.
            if (parole.matches("^(?=.*\\d)[A-Za-z\\d-/.!@#$]{8,20}$")) {
                // Ja parole atbilst prasībām, prasa to vēlreiz apstiprināšanai.
                String parolesParbaude = readPassword("Ievadiet savu unikalo paroli velreiz: ");
                if (parole.equals(parolesParbaude)) {
                    ConsoleColors.println("[Dati ievaditi]", ConsoleColors.GREEN);
                    break;
                } else {
                    ConsoleColors.println("[Ludzu ievadiet tadu pasu paroli (piemeram: qwertyu7, parbaude: qwertyu7 )!]", ConsoleColors.RED);
                }
            } else {
                ConsoleColors.println("[Ludzu ievadiet derigu paroli (piemeram: qwertyu7 )!]", ConsoleColors.RED);
            }
        }
        return new Lietotajs(vards, uzvards, segvards, ePasts, parole);
    }

    // funkcija add pieņem Lietotajs tipa vērtību user un atgriež void tipa vērtību nav
    // Pievieno lietotāju operatīvajam sarakstam un saglabā to "UserData.csv" datnē.
    public void add(Lietotajs user) {
        users.add(user);
        CsvFileHandler.saveLine("UserData.csv", user.toString(), "Vards,Uzvards,Segvards,Epasts,Parole,Datums/Laiks");
    }

    // funkcija pierakstisanas pieņem nav parametru un atgriež void tipa vērtību nav
    // Apstrādā pieslēgšanās procesu: pieprasa segvardu un paroli, pārbauda tos CSV datnē
    // un, ja veiksmīgi, atver reģistrētā lietotāja izvēlni.
    public void pierakstisanas() {
        clear();
        System.out.println("    Tu esi pierakstisanas sadala!   ");
        // Šī cilpa atkārtoti pieprasa segvardu un paroli, līdz pierakstīšanās ir veiksmīga vai lietotājs atceļ.
        while (true) {
            try {
                System.out.println();
                String SegvardaLauks = safeReadLine("Ievadiet sava konta Segvardu vai spiediet ENTER lai atgrieztos uz izvelni: ");
                if (SegvardaLauks.isEmpty()) break;
                if (CsvFileHandler.checkUserExists(SegvardaLauks)) {
                    String ParolesLauks = readPassword("Ievadiet savu paroli: ");
                    if (CsvFileHandler.checkUserLogin(SegvardaLauks, ParolesLauks)) {
                        // Ja segvards un parole ir pareizi, atver reģistrētā lietotāja UI.
                        RegisteredUserUi ui = new RegisteredUserUi(SegvardaLauks, ParolesLauks);
                        ConsoleColors.println("[Pierakstisanas veiksmiga!]", ConsoleColors.GREEN);
                        ui.RegisteredUserUi();
                        break;
                    } else {
                        ConsoleColors.println("[Nepareiza parole!]", ConsoleColors.RED);
                    }
                } else {
                    ConsoleColors.println("[Segvards nesakrit vai nav registrets, parliecinies ka ievadiji to pareizi!]", ConsoleColors.RED);
                }
            } catch (Exception e) {
                ConsoleColors.println("[Ievades kluda: " + e.getMessage() + "]", ConsoleColors.RED);
                break;
            }
        }
    }

    // funkcija exit pieņem nav parametru un atgriež void tipa vērtību nav
    // Pabeidz programmas darbību ar System.exit(0).
    public void exit() {
        System.exit(0);
    }

    // funkcija clear pieņem nav parametru un atgriež void tipa vērtību nav
    // Notīra konsoles ekrānu, izmantojot ANSI escape kodu secību.
    public void clear() {
        System.out.print("\033[2J\033[H");
    }
}
