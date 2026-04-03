import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String failaVards = "sarakstsCSV/UserData.csv";

    public static void saveUsers (List<Lietotajs> users) {
        File file = new File(failaVards);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Vards,Uzvards,Segvards,Epasts,Parole");
            writer.newLine();
        for(Lietotajs u : users) {
            writer.write(u.getVards() + "," + u.getUzvards() + "," + u.getSegvards() + "," + u.getEpasts() + "," + u.getParole() + ",");
            writer.newLine();
            }
        if(App.colors == 1) {
            System.out.println("\u001B[32m[Lietotajs ir registrets]\u001B[0m");
            System.out.println(file.getAbsolutePath());
        } else {
            System.out.println("[Lietotajs ir registrets]");
            System.out.println(file.getAbsolutePath());
        }
        } catch(IOException e) {
            if(App.colors == 1) {
            System.out.println("\u001B[31m[Kluda saglabajot lietotaju]\u001B[0m");
            System.out.println(file.getAbsolutePath());
        } else {
            System.out.println("[Kluda saglabajot lietotaju]");
            System.out.println(file.getAbsolutePath());
        }
        }
    }
    public static List<Lietotajs> lietotaji() {
        List<Lietotajs> users = new ArrayList<>();
        File file = new File(failaVards);
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if(firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",", -1);
                if(data.length == 6) {
                    Lietotajs u = new Lietotajs(
                    data[0], data[1], data[2], data[3], data[4]
                    );
                    users.add(u);
                }
            }
            if(App.colors == 1) {
            System.out.println("\u001B[32m[Dati ieladeti no csv]\u001B[0m");
            System.out.println(file.getAbsolutePath());
            } else {
            System.out.println("[Dati ieladeti no csv]");
            System.out.println(file.getAbsolutePath());
        }
        } catch(IOException e) {
             if(App.colors == 1) {
            System.out.println("\u001B[31m[Kluda nolasot csv failu]\u001B[0m");
            System.out.println(file.getAbsolutePath());
        } else {
            System.out.println("[Kluda nolasot csv failu]");
            System.out.println(file.getAbsolutePath());
        }
        }
        return users;
    }
}
