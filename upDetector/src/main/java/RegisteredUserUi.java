package upDetector.src.main.java;

import java.io.*;
import java.util.*;

public class RegisteredUserUi {
    appFunkcijas app = new appFunkcijas();
    App sakums = new App();
    httpPing pingTests = new httpPing();
    public static int colors = 1;
    private String currentUserSegvards;
    private void updateUserField(int fieldIndex, String newValue) {
    if (currentUserSegvards == null || currentUserSegvards.isBlank()) {
        System.out.println("Segvards nav iestatits.");
        return;
    }

    File dataFolder = findDataFolder(new File(System.getProperty("user.dir")));
    if (dataFolder == null) {
        System.out.println("Data folder not found!");
        return;
    }

    File file = new File(dataFolder, "UserData.csv");
    if (!file.exists()) {
        System.out.println("UserData.csv not found!");
        return;
    }

    List<String> lines = new ArrayList<>();
    boolean updated = false;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        boolean firstLine = true;
        while ((line = br.readLine()) != null) {
            if (firstLine) {
                lines.add(line);
                firstLine = false;
                continue;
            }

            String[] row = line.split(",");
            if (row.length > 2 && row[2].trim().equals(currentUserSegvards.trim())) {
                if (fieldIndex >= 0 && fieldIndex < row.length) {
                    row[fieldIndex] = newValue;
                    updated = true;
                }
                lines.add(String.join(",", row));
            } else {
                lines.add(line);
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
        return;
    }

    if (!updated) {
        System.out.println("User not found!");
        return;
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
        for (String outputLine : lines) {
            bw.write(outputLine);
            bw.newLine();
        }
        System.out.println("[Vertiba atjaunota!]");
    } catch (IOException e) {
        System.out.println("Error writing file: " + e.getMessage());
    }
}
    public void showMenu() {
        try (Scanner answer = new Scanner(System.in)) {
            app.clear();
            System.out.println("        -Programma upDetector-      ");
            System.out.println();
            System.out.println("Izvelne");
            while (true) {
                System.out.println("Sveicinati, ko velaties sodien darit? (Spied ENTER lai beigtu darbības)");
                System.out.println("Vietnes parbaude (1) ");
                System.out.println("Vietnes meklesana (2) ");
                System.out.println("Konts (3) ");
                System.out.println("Izrakstities (4)");
                System.out.print("Atbilde: ");
                String userAnswer = answer.nextLine();

                if (userAnswer.equals("1")) {
                    app.clear();
                    System.out.println("Esat vietnes parbaudes sadala!");
                    System.out.println("Ierakstat vietni kuru gribat parbaudit");
                    System.out.print("Atbilde: ");
                    userAnswer = answer.nextLine();
                    pingTests.httpPinger(userAnswer);
                    System.out.println();
                    System.out.print("Lai izietu spied ENTER ");
                    userAnswer = answer.nextLine();
                    if (userAnswer.equals("")) {
                        app.clear();
                        app.exit();
                        break;
                    }
                } else if (userAnswer.equals("2")) {
                    app.clear();
                    System.out.println("Esat vietnes meklesana sadala!");
                    System.out.println("Ievadiet vietni, kuru gribat atrast");
                    System.out.print("Atbilde: ");
                    answer.nextLine();
                } else if (userAnswer.equals("3")) {
                    app.clear();
                    System.out.println("Esat sava konta sadala!");
                    System.out.println("Konta redigesana (1)");
                    System.out.println("Konta dzesana (2)");
                    System.out.print("Atbilde: ");
                    String accountChoice = answer.nextLine();

                    if (accountChoice.equals("1")) {
                        System.out.print("Ievadiet savu segvardu lai apstiprinatu: ");
                        currentUserSegvards = answer.nextLine();
                        System.out.println("Ko jus velaties rediget? (Vards (1), Uzvards (2), Segvards (3), E-pasts (4), Parole (5))");
                        String editChoice = answer.nextLine();

                        int fieldIndex = -1;
                        switch (editChoice) {
                            case "1" -> fieldIndex = 0;
                            case "2" -> fieldIndex = 1;
                            case "3" -> fieldIndex = 2;
                            case "4" -> fieldIndex = 3;
                            case "5" -> fieldIndex = 4;
                            default -> System.out.println("Nederiga atbilde!");
                        }

                        if (fieldIndex != -1) {
                            System.out.print("Ievadiet jauno vertibu: ");
                            String newValue = answer.nextLine();
                            updateUserField(fieldIndex, newValue);
                        }
                    } else if (accountChoice.equals("2")) {
                        app.clear();
                        System.out.println("Esat sava konta dzesanas sadala!");
                        System.out.println("Vai tiesam velaties dzest savu kontu?");
                        System.out.println("Ja (1)");
                        System.out.println("Ne (2)");
                        System.out.print("Atbilde: ");
                        String deleteChoice = answer.nextLine();
                        if (deleteChoice.equals("1")) {
                            System.out.println("Ievadiet savu Segvardu!    ");
                            String segvards = answer.nextLine();
                            System.out.println("Ievadiet savu paroli!    ");
                            String parole = answer.nextLine();
                            if (CsvFileHandler.checkUserLogin(segvards, parole)) {
                                File currentDir = new File(System.getProperty("user.dir"));
                                File dataFolder = findDataFolder(currentDir);
                                if (dataFolder != null) {
                                    String filePath = new File(dataFolder, "UserData.csv").getAbsolutePath();
                                    CsvFileHandler.removeFromCSV(filePath, segvards, 2);
                                }
                            } else {
                                if (colors == 1) {
                                    System.out.println("\u001B[31m[Nepareizs segvards vai parole!]\u001B[0m");
                                } else {
                                    System.out.println("[Nepareizs segvards vai parole!]");
                                }
                            }
                        }
                    }
                } else if (userAnswer.equals("4")) {
                    app.clear();
                    System.out.println("Esat sava konta izrakstisanas sadala!");
                    System.out.println("    Vai tiesam velaties izrakstities?    ");
                    System.out.println("Ja (1)");
                    System.out.println("Ne (2)");
                    System.out.print("Atbilde: ");
                    String logoutAnswer = answer.nextLine();
                    if (logoutAnswer.equals("1")) {
                        colors = 1;
                        System.out.println("\u001B[32m[Komanda izpildita]\u001B[0m");
                        app.clear();
                        sakums.App();
                    } else if (logoutAnswer.equals("2")) {
                        colors = 0;
                        System.out.println("[Komanda izpildita]");
                        app.clear();
                        continue;
                    }
                } else if (userAnswer.equals("")) {
                    app.exit();
                    break;
                }
            }
        }
    }

   

    public static void main(String[] args) {
        RegisteredUserUi ui = new RegisteredUserUi();
        ui.showMenu();
    }

    private static File findDataFolder(File startDir) {
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
}
