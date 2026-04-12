import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CsvFileHandler {

    // funkcija ensureDataFolder pieņem nav parametru un atgriež File tipa vērtību rezultatu
    // Atrod projekta saknes mapi pēc "upDetector.marker" faila un atgriež "data" apakšmapi.
    // Ja mape neeksistē, izveido to. Ja projekta sakne netiek atrasta, atgriež "data" mapi no pašreizējā direktorija.
    public static File ensureDataFolder() {
        File dir = new File(System.getProperty("user.dir"));
        // Meklē augšup pa direktoriju koku līdz 10 līmeņiem, lai atrastu projekta sakni pēc marker faila.
        for (int i = 0; i < 10; i++) {
            File marker = new File(dir, "upDetector.marker");
            if (marker.exists()) {
                File dataFolder = new File(dir, "data");
                if (!dataFolder.exists()) {
                    // Ja data mape neeksistē, mēģina to izveidot un paziņo par rezultātu.
                    if (dataFolder.mkdirs()) {
                        System.out.println("[Data mape izveidota: " + dataFolder.getAbsolutePath() + "]");
                    } else {
                        System.out.println("[Neizdevas izveidot data mapi: " + dataFolder.getAbsolutePath() + "]");
                    }
                }
                return dataFolder;
            }
            if (dir.getParentFile() != null) {
                dir = dir.getParentFile();
            } else break;
        }
        // Fallback risinājums, ja projekta sakne netiek atrasta 10 līmeņu dziļumā.
        return new File(System.getProperty("user.dir"), "data");
    }

    // funkcija addUsersToCSV pieņem List<Lietotajs> tipa vērtību users un String tipa vērtību fileName un atgriež void tipa vērtību nav
    // Saglabā lietotāju sarakstu CSV datnē. Ja datne ir jauna, ieraksta galveni ar lauku nosaukumiem.
    public static void addUsersToCSV(List<Lietotajs> users, String fileName) {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                ConsoleColors.println("[Datu mape nav atrasta.]", ConsoleColors.RED);
                return;
            }
            String filePath = new File(dataFolder, fileName).getAbsolutePath();
            File file = new File(filePath);
            // Pārbauda vai datne jau eksistē, lai izlemtu vai jāraksta galvene.
            boolean isNew = !file.exists();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                if (isNew) {
                    writer.write("Vards,Uzvards,Segvards,Epasts,Parole,timestamp");
                    writer.newLine();
                }
                // Pieraksta katru lietotāju jaunā rindā CSV datnē.
                for (Lietotajs u : users) {
                    writer.write(u.toString());
                    writer.newLine();
                }
                ConsoleColors.println("[Lietotajs ir registrets]", ConsoleColors.GREEN);
            }
        } catch (IOException e) {
            ConsoleColors.println("[Kluda saglabajot lietotaju: " + e.getMessage() + "]", ConsoleColors.RED);
            e.printStackTrace();
        }
    }

    // funkcija addToCSV pieņem List<Lietotajs> tipa vērtību users un atgriež void tipa vērtību nav
    // Saglabā lietotāju sarakstu noklusētajā "UserData.csv" datnē, izsaucot addUsersToCSV.
    public static void addToCSV(List<Lietotajs> users) {
        addUsersToCSV(users, "UserData.csv");
    }

    // funkcija checkUserExists pieņem String tipa vērtību segvards un atgriež boolean tipa vērtību rezultatu
    // Pārbauda vai lietotājs ar doto segvardu eksistē noklusētajā "UserData.csv" datnē.
    public static boolean checkUserExists(String segvards) {
        return checkUserExists(segvards, "UserData.csv");
    }

    // funkcija checkUserLogin pieņem String tipa vērtību segvards un String tipa vērtību parole un atgriež boolean tipa vērtību rezultatu
    // Pārbauda pierakstīšanās datus noklusētajā "UserData.csv" datnē.
    public static boolean checkUserLogin(String segvards, String parole) {
        return checkUserLogin(segvards, parole, "UserData.csv");
    }

    // funkcija saveLine pieņem String tipa vērtību fileName un String tipa vērtību data un String tipa vērtību header un atgriež void tipa vērtību nav
    // Pievieno vienu rindu CSV datnei. Ja datne ir jauna un header nav tukšs, vispirms ieraksta galvenes rindu.
    public static void saveLine(String fileName, String data, String header) {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                ConsoleColors.println("[Datu mape nav atrasta.]", ConsoleColors.RED);
                return;
            }
            String filePath = new File(dataFolder, fileName).getAbsolutePath();
            File file = new File(filePath);
            // Ja datne ir jauna un galvene nav tukša, ieraksta galvenes rindu pirms datiem.
            boolean isNew = !file.exists();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                if (isNew && header != null && !header.isEmpty()) {
                    writer.write(header);
                    writer.newLine();
                }
                writer.write(data);
                writer.newLine();
            }
            ConsoleColors.println("[Dati saglabati]", ConsoleColors.GREEN);
        } catch (IOException e) {
            ConsoleColors.println("[Kluda saglabajot: " + e.getMessage() + "]", ConsoleColors.RED);
        }
    }

    // funkcija saveLine pieņem String tipa vērtību fileName un String tipa vērtību data un atgriež void tipa vērtību nav
    // Pievieno rindu CSV datnei bez galvenes (pārslogo saveLine ar trīs parametriem).
    public static void saveLine(String fileName, String data) {
        saveLine(fileName, data, null);
    }

    // funkcija removeFromCSV pieņem String tipa vērtību fileName un String tipa vērtību identifier un int tipa vērtību fieldIndex un atgriež void tipa vērtību nav
    // Noņem ierakstu no CSV datnes pēc identifikatora norādītajā lauka indeksā.
    // Izmanto pagaidu datni, lai droši pārrakstītu saturu bez dzēstā ieraksta.
    public static void removeFromCSV(String fileName, String identifier, int fieldIndex) {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                ConsoleColors.println("[Datu mape nav atrasta.]", ConsoleColors.RED);
                return;
            }
            String filePath = new File(dataFolder, fileName).getAbsolutePath();
            // Izveido pagaidu datnes ceļu, aizstājot ".csv" ar "_temp.csv".
            String tempFile = filePath.replace(".csv", "_temp.csv");
            try (BufferedReader br = new BufferedReader(new FileReader(filePath));
                 PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tempFile)))) {
                String line;
                // Nolasījums ar lauku salīdzinājumu: katru rindu, kas neatbilst identifikatoram, pieraksta atpakaļ.
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");
                    if (row.length > fieldIndex && !row[fieldIndex].trim().equals(identifier.trim())) {
                        pw.println(line);
                    }
                }
            } catch (Exception e) {
                ConsoleColors.println("[Kluda lasot CSV failu]", ConsoleColors.RED);
                e.printStackTrace();
                return;
            }

            // Pārkopē pagaidu datni atpakaļ uz oriģinālo datni un dzēš pagaidu datni.
            try {
                BufferedReader reader = new BufferedReader(new FileReader(tempFile));
                FileWriter writer = new FileWriter(filePath);
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line + "\n");
                }
                reader.close();
                writer.close();
                new File(tempFile).delete();
                ConsoleColors.println("[Ieraksts nonemts! CSV atjaunots.]", ConsoleColors.GREEN);
            } catch (IOException e) {
                ConsoleColors.println("[Kluda aizvietojot failu: " + e.getMessage() + "]", ConsoleColors.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // funkcija editRecord pieņem String tipa vērtību fileName un String tipa vērtību identifier un int tipa vērtību fieldIndex un String[] tipa vērtību fieldNames un atgriež void tipa vērtību nav
    // Rediģē ierakstu CSV datnē pēc identifikatora. Atrod rindu ar norādīto identifikatoru,
    // parāda lauku sarakstu, ļauj izvēlēties un ievadīt jaunu vērtību, tad pārraksta datni.
    public static void editRecord(String fileName, String identifier, int fieldIndex, String[] fieldNames) {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                System.out.println("Data folder not found");
                return;
            }
            String filePath = new File(dataFolder, fileName).getAbsolutePath();
            String tempFile = filePath.replace(".csv", "_temp.csv");

            try (BufferedReader br = new BufferedReader(new FileReader(filePath));
                 PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tempFile)))) {

                Scanner input = new Scanner(System.in);
                String line;
                boolean recordFound = false;
                // Lasīšana un pārrakstīšana pa rindu: katra saglabātā rinda tiek pārbaudīta un, ja nepieciešams, mainīta.
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");
                    if (row.length > fieldIndex && row[fieldIndex].trim().equals(identifier.trim())) {
                        recordFound = true;
                        ConsoleColors.println("Ieraksts atrasts!", ConsoleColors.YELLOW);

                        // Izvada numurētu lauku sarakstu, lai lietotājs varētu izvēlēties kuru rediģēt.
                        for (int i = 0; i < fieldNames.length && i < row.length; i++) {
                            System.out.println((i + 1) + " - " + fieldNames[i]);
                        }
                        System.out.print("Izvelaties ko mainit: ");
                        String choiceInput = input.nextLine().trim();
                        int choice = -1;
                        try {
                            choice = Integer.parseInt(choiceInput);
                        } catch (NumberFormatException e) {
                            ConsoleColors.println("[Nepareiza izvele!]", ConsoleColors.RED);
                        }

                        // Pārbauda vai izvēlētais skaitlis ir derīgs lauka indekss.
                        if (choice >= 1 && choice <= fieldNames.length && choice <= row.length) {
                            int fieldToEdit = choice - 1;
                            System.out.print("Ievadiet jauno vertibu: ");
                            String newValue = input.nextLine();
                            row[fieldToEdit] = newValue;
                            ConsoleColors.println("[Vertiba atjaunota!]", ConsoleColors.GREEN);
                        } else {
                            ConsoleColors.println("[Nepareiza izvele!]", ConsoleColors.RED);
                        }

                        // Saliek atjaunoto rindu atpakaļ, savienojot laukus ar komatu.
                        StringBuilder updatedLine = new StringBuilder();
                        for (int i = 0; i < row.length; i++) {
                            if (i > 0) updatedLine.append(",");
                            updatedLine.append(row[i]);
                        }
                        pw.println(updatedLine.toString());
                    } else {
                        pw.println(line);
                    }
                }

                if (!recordFound) {
                    ConsoleColors.println("[Ieraksts nav atrasts!]", ConsoleColors.RED);
                    new File(tempFile).delete();
                    return;
                }

            } catch (Exception e) {
                ConsoleColors.println("[Kluda apstradejot CSV failu]", ConsoleColors.RED);
                e.printStackTrace();
                return;
            }

            // Pārkopē pagaidu datni atpakaļ uz oriģinālo un dzēš pagaidu datni.
            try {
                BufferedReader reader = new BufferedReader(new FileReader(tempFile));
                FileWriter writer = new FileWriter(filePath);
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line + "\n");
                }
                reader.close();
                writer.close();
                new File(tempFile).delete();
                ConsoleColors.println("[Redigesana pabeigta!]", ConsoleColors.GREEN);
            } catch (IOException e) {
                ConsoleColors.println("[Kluda aizvietojot failu: " + e.getMessage() + "]", ConsoleColors.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // funkcija readCSV pieņem String tipa vērtību fileName un atgriež void tipa vērtību nav
    // Nolasa un izvada visas rindiņas no norādītās CSV datnes konsolē.
    public static void readCSV(String fileName) {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                ConsoleColors.println("[Datu mape nav atrasta.]", ConsoleColors.RED);
                return;
            }
            String filePath = new File(dataFolder, fileName).getAbsolutePath();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            ConsoleColors.println("[Kluda lasot CSV failu]", ConsoleColors.RED);
            e.printStackTrace();
        }
    }

    // funkcija checkUserExists pieņem String tipa vērtību segvards un String tipa vērtību fileName un atgriež boolean tipa vērtību rezultatu
    // Pārbauda, vai lietotājs ar norādīto segvardu eksistē konkrētajā CSV datnē.
    // Izlaiž pirmo (galvenes) rindu un salīdzina segvardu ar trešo lauku (indekss 2) katrā rindā.
    public static boolean checkUserExists(String segvards, String fileName) {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                return false;
            }
            File file = new File(dataFolder, fileName);
            if (!file.exists()) {
                return false;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    // Izlaiž galvenes rindu, lai nesalīdzinātu ar lauku nosaukumiem.
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] parts = line.split(",");
                    // Segvards atrodas 3. kolonnā (indekss 2), salīdzina bez ievades atstarpes.
                    if (parts.length >= 3 && parts[2].trim().equals(segvards.trim())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            ConsoleColors.println("[Kluda parbaudot lietotaju: ]" + e.getMessage(), ConsoleColors.RED);
        }
        return false;
    }

    // funkcija listCsvFiles pieņem nav parametru un atgriež void tipa vērtību nav
    // Izvada visu CSV datņu nosaukumus, kas atrodas datu mapē.
    public static void listCsvFiles() {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null || !dataFolder.isDirectory()) {
                ConsoleColors.println("[Datu mape nav atrasta.]", ConsoleColors.RED);
                return;
            }
            // Filtrē tikai ".csv" datnes no datu mapes satura.
            File[] files = dataFolder.listFiles((dir, name) -> name.endsWith(".csv"));
            if (files != null && files.length > 0) {
                System.out.println("CSV faili data mapē:");
                for (File file : files) {
                    System.out.println("- " + file.getName());
                }
            } else {
                ConsoleColors.println("[Nav atrastu CSV failu data mapē.]", ConsoleColors.YELLOW);
            }
        } catch (Exception e) {
            ConsoleColors.println("[Kluda izvadot CSV failus: ]" + e.getMessage(), ConsoleColors.RED);
        }
    }

    // funkcija checkUserLogin pieņem String tipa vērtību segvards un String tipa vērtību parole un String tipa vērtību fileName un atgriež boolean tipa vērtību rezultatu
    // Pārbauda, vai pieslēgšanās dati (segvards un parole) atbilst ierakstam konkrētajā CSV datnē.
    // Salīdzina segvardu ar 3. kolonnu (indekss 2) un paroli ar 5. kolonnu (indekss 4).
    public static boolean checkUserLogin(String segvards, String parole, String fileName) {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                return false;
            }
            File file = new File(dataFolder, fileName);
            if (!file.exists()) {
                return false;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    // Izlaiž galvenes rindu.
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] parts = line.split(",");
                    // Pārbauda vai segvards (2. indekss) un parole (4. indekss) sakrīt ar ievadītajiem datiem.
                    if (parts.length >= 5 && parts[2].trim().equals(segvards.trim()) && parts[4].trim().equals(parole.trim())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            ConsoleColors.println("[Kluda parbaudot pierakstisanos ]" + e.getMessage(), ConsoleColors.RED);
        }
        return false;
    }

    // funkcija loadUsers pieņem nav parametru un atgriež List<Map<String, String>> tipa vērtību rezultatu
    // Nolasa visus lietotājus no "UserData.csv" un atgriež tos kā sarakstu ar Map objektiem,
    // kur katrs Map satur lauku nosaukumu-vērtību pārus (Vards, Uzvards, Segvards, Epasts, Parole).
    public static List<Map<String, String>> loadUsers() {
        List<Map<String, String>> users = new ArrayList<>();
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                return users;
            }
            File file = new File(dataFolder, "UserData.csv");
            if (!file.exists()) return users;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine(); // Izlaiž galveni
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    // Katrā rindā izveido Map ar lauku nosaukumiem kā atslēgām.
                    if (parts.length >= 5) {
                        Map<String, String> user = new HashMap<>();
                        user.put("Vards", parts[0]);
                        user.put("Uzvards", parts[1]);
                        user.put("Segvards", parts[2]);
                        user.put("Epasts", parts[3]);
                        user.put("Parole", parts[4]);
                        users.add(user);
                    }
                }
            }
        } catch (IOException e) {
            ConsoleColors.println("[Kluda ieladejot lietotajus: ]" + e.getMessage(), ConsoleColors.RED);
        }
        return users;
    }

    // funkcija loadWebsites pieņem nav parametru un atgriež List<Map<String, String>> tipa vērtību rezultatu
    // Nolasa visus vietņu ierakstus no "Vietnes.csv" un atgriež tos kā sarakstu ar Map objektiem,
    // kur katrs Map satur: Segvards, URL, Ping (ms), Datums/Laiks.
    public static List<Map<String, String>> loadWebsites() {
        List<Map<String, String>> websites = new ArrayList<>();
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                return websites;
            }
            File file = new File(dataFolder, "Vietnes.csv");
            if (!file.exists()) return websites;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine(); // Izlaiž galveni
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        Map<String, String> website = new HashMap<>();
                        website.put("Segvards", parts[0]);
                        website.put("URL", parts[1]);
                        website.put("Ping", parts[2]);
                        website.put("Datums/Laiks", parts[3]);
                        websites.add(website);
                    }
                }
            }
        } catch (IOException e) {
            ConsoleColors.println("[Kluda palaizot csv failus: ]" + e.getMessage(), ConsoleColors.RED);
        }
        return websites;
    }

    // funkcija loadFavorites pieņem nav parametru un atgriež List<Map<String, String>> tipa vērtību rezultatu
    // Nolasa visus favorītu ierakstus no "MilakasVietnes.csv" un atgriež tos kā sarakstu ar Map objektiem,
    // kur katrs Map satur: Segvards un VietnesNosaukums.
    public static List<Map<String, String>> loadFavorites() {
        List<Map<String, String>> favorites = new ArrayList<>();
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                return favorites;
            }
            File file = new File(dataFolder, "MilakasVietnes.csv");
            if (!file.exists()) return favorites;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine(); // Izlaiž galveni
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        Map<String, String> favorite = new HashMap<>();
                        favorite.put("Segvards", parts[0]);
                        favorite.put("VietnesNosaukums", parts[1]);
                        favorites.add(favorite);
                    }
                }
            }
        } catch (IOException e) {
            ConsoleColors.println("[Kluda palaizot milakos ierakstus: ]" + e.getMessage(), ConsoleColors.RED);
        }
        return favorites;
    }

    // funkcija getUserWebsites pieņem String tipa vērtību segvards un atgriež List<Map<String, String>> tipa vērtību rezultatu
    // Atgriež visus vietņu ierakstus no "Vietnes.csv", kas pieder konkrētajam lietotājam pēc segvarda.
    public static List<Map<String, String>> getUserWebsites(String segvards) {
        List<Map<String, String>> userWebsites = new ArrayList<>();
        List<Map<String, String>> allWebsites = loadWebsites();
        // Filtrē vietnes, salīdzinot segvardu ar katras vietnes ieraksta segvarda lauku.
        for (Map<String, String> website : allWebsites) {
            if (segvards.equals(website.get("Segvards"))) {
                userWebsites.add(website);
            }
        }
        return userWebsites;
    }

    // funkcija getUserFavorites pieņem String tipa vērtību segvards un atgriež List<String> tipa vērtību rezultatu
    // Atgriež URL sarakstu ar lietotāja favorītajām vietnēm no "MilakasVietnes.csv" pēc segvarda.
    public static List<String> getUserFavorites(String segvards) {
        List<String> favorites = new ArrayList<>();
        List<Map<String, String>> allFavorites = loadFavorites();
        // Filtrē favorītus pēc segvarda un izveido sarakstu tikai ar vietņu nosaukumiem.
        for (Map<String, String> fav : allFavorites) {
            if (segvards.equals(fav.get("Segvards"))) {
                favorites.add(fav.get("VietnesNosaukums"));
            }
        }
        return favorites;
    }

    // funkcija getAllData pieņem nav parametru un atgriež Map<String, Map<String, Object>> tipa vērtību rezultatu
    // Apvieno datus no visām trim CSV datnēm (lietotāji, vietnes, favorīti) vienā struktūrā.
    // Atgriež Map, kur atslēga ir segvards, bet vērtība ir Map ar "user", "websites" un "favorites" atslēgām.
    public static Map<String, Map<String, Object>> getAllData() {
        Map<String, Map<String, Object>> allData = new HashMap<>();

        List<Map<String, String>> users = loadUsers();
        List<Map<String, String>> websites = loadWebsites();
        List<Map<String, String>> favorites = loadFavorites();

        // Vispirms izveido ierakstu katram lietotājam ar tukšiem vietnu un favorītu sarakstiem.
        for (Map<String, String> user : users) {
            String segvards = user.get("Segvards");
            Map<String, Object> userData = new HashMap<>();
            userData.put("user", user);
            userData.put("websites", new ArrayList<Map<String, String>>());
            userData.put("favorites", new ArrayList<String>());
            allData.put(segvards, userData);
        }

        // Pievieno katras vietnes ierakstu pie atbilstošā lietotāja pēc segvarda.
        for (Map<String, String> website : websites) {
            String segvards = website.get("Segvards");
            if (allData.containsKey(segvards)) {
                @SuppressWarnings("unchecked")
                List<Map<String, String>> userWebsites = (List<Map<String, String>>) allData.get(segvards).get("websites");
                userWebsites.add(website);
            }
        }

        // Pievieno katru favorītu pie atbilstošā lietotāja pēc segvarda.
        for (Map<String, String> fav : favorites) {
            String segvards = fav.get("Segvards");
            if (allData.containsKey(segvards)) {
                @SuppressWarnings("unchecked")
                List<String> userFavorites = (List<String>) allData.get(segvards).get("favorites");
                userFavorites.add(fav.get("VietnesNosaukums"));
            }
        }

        return allData;
    }
}
