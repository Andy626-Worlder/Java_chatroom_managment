package programming3.chatsys.data;

import java.io.*;

public class TextDatabase extends InMemoryDatabase {
    private File userDb;
    private File messageDb;

    public TextDatabase(File userDb, File messageDb) {
        this.userDb = userDb;
        this.messageDb = messageDb;

        this.loadUsers();
        this.loadMessages();
    }

    @Override
    public void close() {
        this.saveUsers();
        this.saveMessages();
    }

    private void saveUsers() {
        System.out.println("Saving users.");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(this.userDb))) {
            for (User u: this.users) {
                writer.write(u.format() + "\r\n");
            }
        } catch (IOException e) {
            throw new DatabaseAccessException("Cannot write user DB file" + this.userDb, e);
        }
    }

    private void loadUsers() {
        System.out.println("Loading users.");
        if (this.userDb.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(this.userDb))) {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    } else {
                        User u = new User(line);
                        this.users.add(u);
                    }
                }
            } catch (IOException e) {
                throw new DatabaseAccessException("Cannot access user DB file" + this.userDb, e);
            }
        }
    }

    private void saveMessages() {
        System.out.println("Saving chat messages.");
        // You need to implement this
    }

    private void loadMessages() {
        System.out.println("Loading chat messages.");
        // You need to implement this
    }
}
