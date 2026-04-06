import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class httpPing {
    public void httpPinger(String URL) {
        try {
            URI uri = new URI(URL);
            URL url = uri.toURL();

            long start = System.currentTimeMillis();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int code = conn.getResponseCode();

            long end = System.currentTimeMillis();

            long duration = end - start;

            System.out.println("Code: " + code);
            System.out.println("Time: " + duration + " ms");

        } catch (Exception e) {
            System.out.println("\u001B[31mKÄŒÅ«da: mÄjaslapa nav sasniedzama! \u001B[0m");
        }
    }
    public static void main(String[] args) {
        httpPing ping = new httpPing();
        ping.httpPinger("https://githbb.com");
}
}
