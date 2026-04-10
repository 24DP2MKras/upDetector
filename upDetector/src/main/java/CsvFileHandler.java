import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CsvFileHandler {

    public static File ensureDataFolder() {
        File dataFolder = new File(System.getProperty("user.dir"), "data");
    
        if (!dataFolder.exists()) {
            if (dataFolder.mkdirs()) {
                if (App.colors == 1) {
                    ConsoleColors.println("[Data mape izveidota: " + dataFolder.getAbsolutePath() + "]", ConsoleColors.GREEN);
                } else {
                    System.out.println("[Data mape izveidota: " + dataFolder.getAbsolutePath() + "]");
                }
            } else {
                System.out.println("[Neizdevas izveidot data mapi: " + dataFolder.getAbsolutePath() + "]");
            }
        }
        return dataFolder;
    }


    // Save users list to CSV (compatible with appFunkcijas)
    public static void addUsersToCSV(List<Lietotajs> users, String fileName) {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                System.out.println("Data folder not found");
                return;
            }
            String filePath = new File(dataFolder, fileName).getAbsolutePath();
            File file = new File(filePath);
            boolean isNew = !file.exists();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                if (isNew) {
                    writer.write("Vards,Uzvards,Segvards,Epasts,Parole,timestamp");
                    writer.newLine();
                }
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

    public static void addToCSV(List<Lietotajs> users) {
        addUsersToCSV(users, "UserData.csv");
    }

    public static boolean checkUserExists(String segvards) {
        return checkUserExists(segvards, "UserData.csv");
    }

    public static boolean checkUserLogin(String segvards, String parole) {
        return checkUserLogin(segvards, parole, "UserData.csv");
    }

    // Simple method to add a line to any CSV file
    public static void saveLine(String fileName, String data, String header) {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                System.out.println("Data folder not found");
                return;
            }
            String filePath = new File(dataFolder, fileName).getAbsolutePath();
            File file = new File(filePath);
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

    public static void saveLine(String fileName, String data) {
        saveLine(fileName, data, null);
    }

    // Remove record from CSV file by identifier (any field index)
    public static void removeFromCSV(String fileName, String identifier, int fieldIndex) {
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
                String line;
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

    // Edit record in CSV file by identifier
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

                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");
                    if (row.length > fieldIndex && row[fieldIndex].trim().equals(identifier.trim())) {
                        recordFound = true;

                        ConsoleColors.println("Ieraksts atrasts!", ConsoleColors.YELLOW);

                        for (int i = 0; i < fieldNames.length && i < row.length; i++) {
                            System.out.println((i + 1) + " - " + fieldNames[i]);
                        }
                        System.out.print("Izvelaties ko mainit: ");
                        int choice = input.nextInt();
                        input.nextLine();

                        if (choice >= 1 && choice <= fieldNames.length && choice <= row.length) {
                            int fieldToEdit = choice - 1;
                            System.out.print("Ievadiet jauno vertibu: ");
                            String newValue = input.nextLine();
                            row[fieldToEdit] = newValue;

                            ConsoleColors.println("[Vertiba atjaunota!]", ConsoleColors.GREEN);
                        } else {
                            ConsoleColors.println("[Nepareiza izvele!]", ConsoleColors.RED);
                        }

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

    // Read all records from CSV file
    public static void readCSV(String fileName) {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null) {
                System.out.println("Data folder not found");
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

    // Check if a user exists by segvards
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
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] parts = line.split(",");
                    if (parts.length >= 3 && parts[2].trim().equals(segvards.trim())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking user: " + e.getMessage());
        }
        return false;
    }

    // List all CSV files in the data folder
    public static void listCsvFiles() {
        try {
            File dataFolder = ensureDataFolder();
            if (dataFolder == null || !dataFolder.isDirectory()) {
                System.out.println("Data folder not found");
                return;
            }
            File[] files = dataFolder.listFiles((dir, name) -> name.endsWith(".csv"));
            if (files != null && files.length > 0) {
                System.out.println("CSV files in data folder:");
                for (File file : files) {
                    System.out.println("- " + file.getName());
                }
            } else {
                System.out.println("No CSV files found in data folder.");
            }
        } catch (Exception e) {
            System.out.println("Error listing CSV files: " + e.getMessage());
        }
    }

    // Check if user login is valid (segvards and parole match)
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
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] parts = line.split(",");
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

    // Load all users from UserData.csv
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
                String line = br.readLine(); // Skip header
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
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
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // Load all websites from Vietnes.csv
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
                String line = br.readLine(); // Skip header
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 7) {
                        Map<String, String> website = new HashMap<>();
                        website.put("VietnesID", parts[0]);
                        website.put("VietnesNosaukums", parts[1]);
                        website.put("Ping", parts[2]);
                        website.put("ParbaudesLaiks", parts[3]);
                        website.put("Statuss", parts[4]);
                        website.put("Iecienita", parts[5]);
                        website.put("Segvards", parts[6]);
                        websites.add(website);
                    }
                }
            }
        } catch (IOException e) {
            ConsoleColors.println("[Kluda palaizot csv failus: ]" + e.getMessage(), ConsoleColors.RED);
        }
        return websites;
    }

    // Load all favorites from MilakasVietnes.csv
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
                String line = br.readLine(); // Skip header
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

    // Get all websites for a specific user
    public static List<Map<String, String>> getUserWebsites(String segvards) {
        List<Map<String, String>> userWebsites = new ArrayList<>();
        List<Map<String, String>> allWebsites = loadWebsites();
        for (Map<String, String> website : allWebsites) {
            if (segvards.equals(website.get("Segvards"))) {
                userWebsites.add(website);
            }
        }
        return userWebsites;
    }
// Get favorite websites for a specific user
    public static List<String> getUserFavorites(String segvards) {
        List<String> favorites = new ArrayList<>();
        List<Map<String, String>> allFavorites = loadFavorites();
        for (Map<String, String> fav : allFavorites) {
            if (segvards.equals(fav.get("Segvards"))) {
                favorites.add(fav.get("VietnesNosaukums"));
            }
        }
        return favorites;
    }

    // Get combined data: users with their websites and favorites
    public static Map<String, Map<String, Object>> getAllData() {
        Map<String, Map<String, Object>> allData = new HashMap<>();

        List<Map<String, String>> users = loadUsers();
        List<Map<String, String>> websites = loadWebsites();
        List<Map<String, String>> favorites = loadFavorites();

        for (Map<String, String> user : users) {
            String segvards = user.get("Segvards");
            Map<String, Object> userData = new HashMap<>();
            userData.put("user", user);
            userData.put("websites", new ArrayList<Map<String, String>>());
            userData.put("favorites", new ArrayList<String>());
            allData.put(segvards, userData);
        }

        for (Map<String, String> website : websites) {
            String segvards = website.get("Segvards");
            if (allData.containsKey(segvards)) {
                @SuppressWarnings("unchecked")
                List<Map<String, String>> userWebsites = (List<Map<String, String>>) allData.get(segvards).get("websites");
                userWebsites.add(website);
            }
        }

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
