import java.io.IOException; 
import java.nio.file.*; 
import java.util.ArrayList; 
import java.util.List;
import java.util.function.Function;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User fromCsvRow(String row) {
        String[] parts = row.split(",");
        if (parts.length >= 2) {
            return new User(parts[0].trim(), parts[1].trim());
        }
        throw new IllegalArgumentException("Invalid CSV row: " + row);
    }

    
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return "User{username='" + username + "', password='" + password + "'}";
    }
}

public class FileHandler {

    private final Path filePath;
    public FileHandler(String fileName) {
        this.filePath = Paths.get(fileName);
    }

    public <Data> List<Data> load(Function<String, Data> parser) {
        List<Data> result = new ArrayList<>();
        if(!Files.exists(filePath)) {
            return result;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (!line.isEmpty()) {
                    result.add(parser.apply(line)); 
                }
            }

        } catch (IOException e) {
            System.out.println("Couldn't read the file: " + e.getMessage());
        }

        return result;
    }




    public static void main(String[] args) {
        FileHandler handler = new FileHandler("users.csv");

        List<User> users = handler.load(User::fromCsvRow);

        for (User u : users) {
            System.out.println(u);
        }

    }
}