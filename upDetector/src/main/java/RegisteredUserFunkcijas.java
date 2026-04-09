import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RegisteredUserFunkcijas {
    public List<HttpPing> ieraksts;

    public String ping;
    public URL urlString;
    public String timestamp;
    public String segvards;

    public RegisteredUserFunkcijas() {
        this.ieraksts = new ArrayList<>();
    }

    public void httpPinger(String urlString, String username) {
        
        try {
            URI uri = new URI(urlString);
            this.urlString = uri.toURL();

            long start = System.currentTimeMillis();
            HttpURLConnection conn = (HttpURLConnection) this.urlString.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int code = conn.getResponseCode();
            long end = System.currentTimeMillis();

            this.ping = String.valueOf(end - start);
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.segvards = username;

            System.out.println("Code: " + code);
            System.out.println("Atrums: " + this.ping + " ms ");

            HttpPing result = new HttpPing(segvards, ping, timestamp, urlString);
            ieraksts.add(result);

        } catch (Exception e) {
            System.out.println("\u001B[31mKluda: majaslapa nav sasniedzama! \u001B[0m");
        }
    }
    public HttpPing pedejoReiziSkatits() {
        HttpPing result = new HttpPing(segvards, ping, timestamp, urlString.toString());
        CsvFileHandler.saveLine("Vietnes.csv", ieraksts.toString(), "Segvards,PingAtrums(ms),Datums/Laiks,URL");
        return result;
    }
    public HttpPing pedejoReiziSkatits1() {
    HttpPing result = new HttpPing(segvards, ping, timestamp, urlString.toString());
    for (HttpPing h : ieraksts) {
        CsvFileHandler.saveLine("MilakasVietnes.csv", h.toString1(),"Segvards,URL");
        }
    return result;
    }

     @Override
    public String toString() {
        return this.ping + " ms" + "," + this.timestamp + "," + this.segvards + "," + this.urlString;
        
    }
    public String toString1(){
        return this.segvards + " ms" + "," + this.urlString;
    }
}
