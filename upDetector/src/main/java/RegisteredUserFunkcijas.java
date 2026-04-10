import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
            // Make sure URL has a scheme
            if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
                urlString = "https://" + urlString;
            }
            URI uri = new URI(urlString);
            this.urlString = uri.toURL();

            long start = System.currentTimeMillis();
            HttpURLConnection conn = (HttpURLConnection) this.urlString.openConnection();
            conn.setRequestMethod("GET");
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

            HttpPing result = new HttpPing(segvards, ping, timestamp, urlString);
            ieraksts.add(result);

        } catch (Exception e) {
            ConsoleColors.println("Kluda: majaslapa nav sasniedzama! (" + e.getMessage() + ")", ConsoleColors.RED);
        }
    }

    public HttpPing pedejoReiziSkatits() {
        HttpPing result = new HttpPing(segvards, ping, timestamp, urlString.toString());
        // Header: Segvards,URL,PingAtrums(ms),Datums/Laiks
        CsvFileHandler.saveLine("Vietnes.csv", segvards + "," + urlString.toString() + "," + ping + " ms" + "," + timestamp, "Segvards,URL,PingAtrums(ms),Datums/Laiks");
        return result;
    }

    public HttpPing pedejoReiziSkatits1() {
    HttpPing result = new HttpPing(segvards, ping, timestamp, urlString.toString());
    CsvFileHandler.saveLine("MilakasVietnes.csv", result.getSegVards() + "," + result.getUrlString(), "Segvards,URL");
    return result;
}

    @Override
    public String toString() {
        return this.segvards + "," + this.urlString + "," + this.ping + " ms" + "," + this.timestamp;
    }

    public String toString1() {
        return this.segvards + "," + this.urlString;
    }
}