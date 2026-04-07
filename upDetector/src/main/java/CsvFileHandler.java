import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class CsvFileHandler {

    // Save users list to CSV (compatible with appFunkcijas)
    public static void addUsersToCSV(List<Lietotajs> users, String fileName) {
        try {
            // Find the project root by looking for the data folder
            String filePath = new File("../data", fileName).getAbsolutePath();
            File file = new File(filePath);
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                if (!file.exists()) {
                    writer.write("Vards,Uzvards,Segvards,Epasts,Parole");
                    writer.newLine();
                }
                for(Lietotajs u : users) {
                    writer.write(u.getVards() + "," + u.getUzvards() + "," + u.getSegvards() + "," + u.getEpasts() + "," + u.getParole());
                    writer.newLine();
                }
                if(App.colors == 1) {
                    System.out.println("\u001B[32m[Lietotajs ir registrets]\u001B[0m");
                    System.out.println(file.getAbsolutePath());
                } else {
                    System.out.println("[Lietotajs ir registrets]");
                    System.out.println(file.getAbsolutePath());
                }
            }
        } catch(IOException e) {
            if(App.colors == 1) {
                System.out.println("\u001B[31m[Kluda saglabajot lietotaju: " + e.getMessage() + "]\u001B[0m");
            } else {
                System.out.println("[Kluda saglabajot lietotaju: " + e.getMessage() + "]");
            }
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
    public static void saveLine(String fileName, String data) {
        try {
            String filePath = new File("../data", fileName).getAbsolutePath();
            File file = new File(filePath);
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(data);
                writer.newLine();
            }
        } catch(IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // Remove record from CSV file by identifier (any field index)
    public static void removeFromCSV(String fileName, String identifier, int fieldIndex) {
        try {
            String filePath = new File("../data", fileName).getAbsolutePath();
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
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Kluda lasot CSV failu]\u001B[0m");
                } else {
                    System.out.println("[Kluda lasot CSV failu]");
                }
                e.printStackTrace();
                return;
            }

            // Replace original file with temp file
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
                if (App.colors == 1) {
                    System.out.println("\u001B[32m[Ieraksts nonemts! CSV atjaunots.]\u001B[0m");
                } else {
                    System.out.println("[Ieraksts nonemts! CSV atjaunots.]");
                }
            } catch (IOException e) {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Kluda aizvietojot failu: " + e.getMessage() + "]\u001B[0m");
                } else {
                    System.out.println("[Kluda aizvietojot failu: " + e.getMessage() + "]");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Edit record in CSV file by identifier
    public static void editRecord(String fileName, String identifier, int fieldIndex, String[] fieldNames) {
        try {
            String filePath = new File("../data", fileName).getAbsolutePath();
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

                        if (App.colors == 1) {
                            System.out.println("\u001B[33mIeraksts atrasts!\u001B[0m");
                        } else {
                            System.out.println("Ieraksts atrasts!");
                        }

                        // Display field options
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

                            if (App.colors == 1) {
                                System.out.println("\u001B[32m[Vertiba atjaunota!]\u001B[0m");
                            } else {
                                System.out.println("[Vertiba atjaunota!]");
                            }
                        } else {
                            if (App.colors == 1) {
                                System.out.println("\u001B[31m[Nepareiza izvele!]\u001B[0m");
                            } else {
                                System.out.println("[Nepareiza izvele!]");
                            }
                        }

                        // Write updated row
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
                    if (App.colors == 1) {
                        System.out.println("\u001B[31m[Ieraksts nav atrasts!]\u001B[0m");
                    } else {
                        System.out.println("[Ieraksts nav atrasts!]");
                    }
                    new File(tempFile).delete();
                    return;
                }

            } catch (Exception e) {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Kluda apstradejot CSV failu]\u001B[0m");
                } else {
                    System.out.println("[Kluda apstradejot CSV failu]");
                }
                e.printStackTrace();
                return;
            }

            // Replace original file with temp file
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
                if (App.colors == 1) {
                    System.out.println("\u001B[32m[Redigesana pabeigta!]\u001B[0m");
                } else {
                    System.out.println("[Redigesana pabeigta!]");
                }
            } catch (IOException e) {
                if (App.colors == 1) {
                    System.out.println("\u001B[31m[Kluda aizvietojot failu: " + e.getMessage() + "]\u001B[0m");
                } else {
                    System.out.println("[Kluda aizvietojot failu: " + e.getMessage() + "]");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read all records from CSV file
    public static void readCSV(String fileName) {
        try {
            String filePath = new File("../data", fileName).getAbsolutePath();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            if (App.colors == 1) {
                System.out.println("\u001B[31m[Kluda lasot CSV failu]\u001B[0m");
            } else {
                System.out.println("[Kluda lasot CSV failu]");
            }
            e.printStackTrace();
        }
    }

    // Write a full Vietnes.csv file from a list of lines (overwrites existing file)
    public static void writeVietnes(List<String> lines) {
        String fileName = "Vietnes.csv";
        try {
            String filePath = new File("../data", fileName).getAbsolutePath();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("Wrote " + lines.size() + " line(s) to " + fileName);
            }
        } catch (IOException e) {
            System.out.println("Error writing " + fileName + ": " + e.getMessage());
        }
    }

    // Append a single Vietnes row
    public static void appendVietne(String line) {
        saveLine("Vietnes.csv", line);
    }

    // Read Vietnes.csv
    public static void readVietnes() {
        readCSV("Vietnes.csv");
    }

    // Delete rows from Vietnes.csv by matching one field
    public static void deleteVietne(String identifier, int fieldIndex) {
        removeFromCSV("Vietnes.csv", identifier, fieldIndex);
    }

    // Edit a row in Vietnes.csv by matching one field, using interactive field selection
    public static void editVietne(String identifier, int fieldIndex, String[] fieldNames) {
        editRecord("Vietnes.csv", identifier, fieldIndex, fieldNames);
    }

    // Check if a user exists by segvards
    public static boolean checkUserExists(String segvards, String fileName) {
        try {
            File file = new File("../data", fileName);
            if (!file.exists()) {
                return false;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false; // Skip header
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
            File dataFolder = new File("../data");
            if (!dataFolder.exists() || !dataFolder.isDirectory()) {
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
            File file = new File("../data", fileName);
            if (!file.exists()) {
                return false;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false; // Skip header
                        continue;
                    }
                    String[] parts = line.split(",");
                    if (parts.length >= 5 && parts[2].trim().equals(segvards.trim()) && parts[4].trim().equals(parole.trim())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking login: " + e.getMessage());
        }
        return false;
    }

    // Load all users from UserData.csv
    public static List<Map<String, String>> loadUsers() {
        List<Map<String, String>> users = new ArrayList<>();
        try {
            File file = new File("../data", "UserData.csv");
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
            File file = new File("../data", "Vietnes.csv");
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
            System.out.println("Error loading websites: " + e.getMessage());
        }
        return websites;
    }

    // Load all favorites from MilakasVietnes.csv
    public static List<Map<String, String>> loadFavorites() {
        List<Map<String, String>> favorites = new ArrayList<>();
        try {
            File file = new File("../data", "MilakasVietnes.csv");
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
            System.out.println("Error loading favorites: " + e.getMessage());
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
        
        // Add users
        for (Map<String, String> user : users) {
            String segvards = user.get("Segvards");
            Map<String, Object> userData = new HashMap<>();
            userData.put("user", user);
            userData.put("websites", new ArrayList<Map<String, String>>());
            userData.put("favorites", new ArrayList<String>());
            allData.put(segvards, userData);
        }
        
        // Add websites to users
        for (Map<String, String> website : websites) {
            String segvards = website.get("Segvards");
            if (allData.containsKey(segvards)) {
                @SuppressWarnings("unchecked")
                List<Map<String, String>> userWebsites = (List<Map<String, String>>) allData.get(segvards).get("websites");
                userWebsites.add(website);
            }
        }
        
        // Add favorites to users
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