import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HttpPing {
    private String segvards;
    private String ping;
    private String timestamp;
    private String urlString;

    public HttpPing(String segvards, String ping, String timestamp, String urlString) {
        this.segvards = segvards;
        this.ping = ping;
        this.timestamp = timestamp;
        this.urlString = urlString;
    }
    public String toString() {
        return "|Atrums: " + this.ping + " ms " + "|Datums/Laiks: " + this.timestamp + "|segvards: " + segvards + " |";
    }
    public String getSegVards() {
        return this.segvards;
    }
    public String getPing() { 
        return this.ping; 
    }
    public String getTimeStamp() { 
        return this.timestamp; 
    }
    public String getUrlString() { 
        return this.urlString; 
    }
    
}