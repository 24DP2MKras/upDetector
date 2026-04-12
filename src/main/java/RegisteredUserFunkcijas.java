import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RegisteredUserFunkcijas {
    // Saraksts ar visiem pārbaudes ierakstiem šīs sesijas laikā.
    public List<HttpPing> ieraksts;

    // Pēdējās pārbaudes rezultātu lauki.
    public String ping;
    public URL urlString;
    public String timestamp;
    public String segvards;

    // funkcija RegisteredUserFunkcijas pieņem nav parametru un atgriež void tipa vērtību nav
    // Konstruktors - inicializē tukšu ierakstu sarakstu HTTP ping rezultātiem.
    public RegisteredUserFunkcijas() {
        this.ieraksts = new ArrayList<>();
    }

    // funkcija httpPinger pieņem String tipa vērtību urlString un String tipa vērtību username un atgriež void tipa vērtību nav
    // Veic HTTP GET pieprasījumu uz norādīto URL un mēra atbildes laiku milisekundēs.
    // Saglabā ping rezultātu, laika zīmogu un URL objekta laukos, pievieno ierakstu sarakstam.
    // Kļūdas gadījumā (vietne nav sasniedzama, taimauts) izvada kļūdas ziņojumu.
    public void httpPinger(String urlString, String username) {
        try {
            // Pārliecinās, ka URL satur protokola daļu, citādi pievieno "https://".
            if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
                urlString = "https://" + urlString;
            }
            URI uri = new URI(urlString);
            this.urlString = uri.toURL();

            // Fiksē laiku pirms savienojuma un pēc atbildes, lai aprēķinātu ping ms.
            long start = System.currentTimeMillis();
            HttpURLConnection conn = (HttpURLConnection) this.urlString.openConnection();
            conn.setRequestMethod("GET");
            // Savienojuma un lasīšanas taimauts: 5 sekundes katram.
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            int code = conn.getResponseCode();
            long end = System.currentTimeMillis();

            this.ping = String.valueOf(end - start);
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.segvards = username;

            System.out.println("Code: " + code);
            System.out.println("Atrums: " + this.ping + " ms ");

            // Izveido HttpPing ierakstu un pievieno to sesijas sarakstam.
            HttpPing result = new HttpPing(segvards, ping, timestamp, urlString);
            ieraksts.add(result);

        } catch (Exception e) {
            ConsoleColors.println("Kluda: majaslapa nav sasniedzama! (" + e.getMessage() + ")", ConsoleColors.RED);
        }
    }

    // funkcija pedejoReiziSkatits pieņem nav parametru un atgriež HttpPing tipa vērtību rezultatu
    // Saglabā pēdējo ping rezultātu "Vietnes.csv" datnē un atgriež HttpPing objektu.
    // CSV ierakstā saglabā: segvardu, URL, ping ātrumu un laika zīmogu.
    public HttpPing pedejoReiziSkatits() {
        HttpPing result = new HttpPing(segvards, ping, timestamp, urlString.toString());
        // Galvene CSV ierakstam: Segvards,URL,PingAtrums(ms),Datums/Laiks
        CsvFileHandler.saveLine("Vietnes.csv", segvards + "," + urlString.toString() + "," + ping + " ms" + "," + timestamp, "Segvards,URL,PingAtrums(ms),Datums/Laiks");
        return result;
    }

    // funkcija pedejoReiziSkatits1 pieņem nav parametru un atgriež HttpPing tipa vērtību rezultatu
    // Saglabā pēdējo vietni "MilakasVietnes.csv" datnē kā favorītu un atgriež HttpPing objektu.
    // CSV ierakstā saglabā tikai: segvardu un URL (bez ping datiem).
    public HttpPing pedejoReiziSkatits1() {
        HttpPing result = new HttpPing(segvards, ping, timestamp, urlString.toString());
        CsvFileHandler.saveLine("MilakasVietnes.csv", result.getSegVards() + "," + result.getUrlString(), "Segvards,URL");
        return result;
    }

    // funkcija toString pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež pēdējās ping pārbaudes datus kā CSV rindas formātā: segvards,URL,ping ms,laiks.
    @Override
    public String toString() {
        return this.segvards + "," + this.urlString + "," + this.ping + " ms" + "," + this.timestamp;
    }

    // funkcija toString1 pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež tikai segvardu un URL bez ping un laika datiem (izmanto favorītu saglabāšanai).
    public String toString1() {
        return this.segvards + "," + this.urlString;
    }
}
