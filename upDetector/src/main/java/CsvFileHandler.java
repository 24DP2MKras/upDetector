import java.io.*;
import java.util.List;
import java.util.Scanner;

public class CsvFileHandler {

    // Save users list to CSV (compatible with appFunkcijas)
    public static void addToCSV(List<Lietotajs> users) {
        try {
            // Find the project root by looking for the data folder
            File currentDir = new File(System.getProperty("user.dir"));
            File dataFolder = findDataFolder(currentDir);
            
            if (dataFolder == null) {
                throw new IOException("Could not find 'data' folder. Search started from: " + System.getProperty("user.dir"));
            }
            
            String filePath = new File(dataFolder, "UserData.csv").getAbsolutePath();
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

    // Helper method to find the data folder by traversing up and down the directory tree
    private static File findDataFolder(File startDir) {
        // First, search UP the directory tree
        File current = startDir;
        for (int i = 0; i < 10; i++) {
            File data = new File(current, "data");
            if (data.exists() && data.isDirectory()) {
                return data;
            }
            if (current.getParentFile() != null) {
                current = current.getParentFile();
            } else {
                break;
            }
        }
        
        // Then, search DOWN the directory tree
        return findDataFolderDown(startDir);
    }
    
    private static File findDataFolderDown(File dir) {
        File data = new File(dir, "data");
        if (data.exists() && data.isDirectory()) {
            return data;
        }
        
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File found = findDataFolderDown(file);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return null;
    }    

    // Simple method to add a line to any CSV file
    public static void saveLine(String fileName, String data) {
        try {
            File dataFolder = findDataFolder(new File(System.getProperty("user.dir")));
            if (dataFolder == null) {
                System.out.println("Data folder not found");
                return;
            }
            
            File file = new File(dataFolder, fileName);
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(data);
                writer.newLine();
            }
        } catch(IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // Remove record from CSV file by identifier (any field index)
    public static void removeFromCSV(String filePath, String identifier, int fieldIndex) {
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
    }

    // Edit record in CSV file by identifier
    public static void editRecord(String filePath, String identifier, int fieldIndex, String[] fieldNames) {
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
    }

    // Read all records from CSV file
    public static void readCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
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

    // Check if a user exists by segvards
    public static boolean checkUserExists(String segvards) {
        try {
            File dataFolder = findDataFolder(new File(System.getProperty("user.dir")));
            if (dataFolder == null) {
                return false;
            }
            File file = new File(dataFolder, "UserData.csv");
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
    // Check if user login is valid (segvards and parole match)
    public static boolean checkUserLogin(String segvards, String parole) {
        try {
            File dataFolder = findDataFolder(new File(System.getProperty("user.dir")));
            if (dataFolder == null) {
                return false;
            }
            File file = new File(dataFolder, "UserData.csv");
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
}