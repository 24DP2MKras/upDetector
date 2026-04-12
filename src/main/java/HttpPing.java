public class HttpPing {
    private String segvards;
    private String ping;
    private String timestamp;
    private String urlString;

    // funkcija HttpPing pieņem String tipa vērtību segvards un String tipa vērtību ping un String tipa vērtību timestamp un String tipa vērtību urlString un atgriež void tipa vērtību nav
    // Konstruktors - izveido HTTP ping pārbaudes ierakstu ar visiem nepieciešamajiem datiem.
    public HttpPing(String segvards, String ping, String timestamp, String urlString) {
        this.segvards = segvards;
        this.ping = ping;
        this.timestamp = timestamp;
        this.urlString = urlString;
    }

    // funkcija toString pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež ping ierakstu formātā: "[ping] ms , [laika zīmogs], [segvards]" (izmanto vēstures attēlošanai).
    public String toString() {
        return this.ping + " ms " + ", " + this.timestamp + ", " + segvards;
    }

    // funkcija toString1 pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež ping ierakstu formātā: "[ping] ms , [URL], [segvards]" (izmanto URL informācijas attēlošanai).
    public String toString1() {
        return this.ping + " ms " + ", " + this.urlString + ", " + this.segvards;
    }

    // funkcija getSegVards pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež ierakstam piesaistīto lietotāja segvardu.
    public String getSegVards() {
        return this.segvards;
    }

    // funkcija getPing pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež ping pārbaudes rezultātu milisekundēs kā tekstu.
    public String getPing() {
        return this.ping;
    }

    // funkcija getTimeStamp pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež pārbaudes veikšanas laiku formātā "yyyy-MM-dd HH:mm:ss".
    public String getTimeStamp() {
        return this.timestamp;
    }

    // funkcija getUrlString pieņem nav parametru un atgriež String tipa vērtību rezultatu
    // Atgriež pārbaudītās vietnes URL kā tekstu.
    public String getUrlString() {
        return this.urlString;
    }
}
