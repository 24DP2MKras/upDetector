import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lietotajs {
    private String vards;
    private String uzvards;
    private String segvards;
    private String ePasts;
    private String parole;
    private String timestamp;

    // funkcija Lietotajs pieņem String tipa vērtību vards un String tipa vērtību uzvards un String tipa vērtību segvards un String tipa vērtību ePasts un String tipa vērtību parole un atgriež void tipa vērtību nav
    // Konstruktors - izveido jaunu lietotāja objektu ar visiem datiem un automātiski pievieno reģistrācijas laika zīmogu.
    public Lietotajs(String vards, String uzvards, String segvards, String ePasts, String parole) {
        this.vards = vards;
        this.uzvards = uzvards;
        this.segvards = segvards;
        this.ePasts = ePasts;
        this.parole = parole;
        // Reģistrācijas laika zīmogs tiek ģenerēts automātiski pašreizējā brīdī.
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // funkcija toString pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež lietotāja datus CSV rindas formātā: vards,uzvards,segvards,epasts,parole,laiks.
    public String toString() {
        return this.vards + "," + this.uzvards + "," + this.segvards + "," + this.ePasts + "," + this.parole + "," + this.timestamp;
    }

    // funkcija getVards pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež lietotāja vārdu.
    public String getVards() {
        return this.vards;
    }

    // funkcija getUzvards pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež lietotāja uzvārdu.
    public String getUzvards() {
        return this.uzvards;
    }

    // funkcija getSegvards pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež lietotāja segvardu (unikālo lietotājvārdu).
    public String getSegvards() {
        return this.segvards;
    }

    // funkcija getEpasts pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež lietotāja e-pasta adresi.
    public String getEpasts() {
        return this.ePasts;
    }

    // funkcija getParole pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež lietotāja paroli.
    public String getParole() {
        return this.parole;
    }

    // funkcija getTimestamp pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež konta reģistrācijas laika zīmogu formātā "yyyy-MM-dd HH:mm:ss".
    public String getTimestamp() {
        return this.timestamp;
    }
}
