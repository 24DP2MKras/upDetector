import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HttpPing {
    private String segvards;
    private String ping;
    private String timestamp;
    private URL urlString;

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
            System.out.println("Time: " + this.ping + " ms");

        } catch (Exception e) {
            System.out.println("\u001B[31mKluda: majaslapa nav sasniedzama! \u001B[0m");
        }
    }

    @Override
    public String toString() {
        return this.segvards + "," + this.ping + "ms" + "," + this.timestamp + "," + this.urlString;
    }

    public static void main(String[] args) {
        HttpPing ping = new HttpPing();
        ping.httpPinger("https://github.com", "testuser");
        System.out.println(ping);
    }
}