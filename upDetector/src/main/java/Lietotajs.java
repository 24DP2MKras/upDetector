import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Lietotajs {
    private String vards;
    private String uzvards;
    private String segvards;
    private String ePasts;
    private String parole;
    private String timestamp;

    public Lietotajs(String vards, String uzvards, String segvards, String ePasts, String parole){
        this.vards = vards;
        this.uzvards = uzvards;
        this.segvards = segvards;
        this.ePasts = ePasts;
        this.parole = parole;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public String toString() {
    return this.vards + "," + this.uzvards + "," + this.segvards + "," + this.ePasts + "," + this.parole + "," + this.timestamp;
    }

    public String getVards() {
    return this.vards;
    }
    public String getUzvards() { 
        return this.uzvards; 
    }
    public String getSegvards() { 
        return this.segvards; 
    }
    public String getEpasts() { 
        return this.ePasts; 
    }
    public String getParole(){
        return this.parole;
    }
    public String getTimestamp() { 
        return this.timestamp; 
    }
}
