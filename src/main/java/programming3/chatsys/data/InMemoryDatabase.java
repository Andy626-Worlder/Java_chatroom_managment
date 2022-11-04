package programming3.chatsys.data;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public class InMemoryDatabase implements Database {
    protected List<User> users;
    protected List<ChatMessage> messages;
    private int lastId = 0;

    /**
     * Create a new empty InMemoryDatabase.
     */
    public InMemoryDatabase() {
        users = new LinkedList<>();
        messages = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "InMemoryDatabase{" +
                "users=" + users +
                '}';
    }

    @Override
    public boolean register(User user) {
        if (this.contains(user)) {
            return false;
        } else {
            this.users.add(user);
            return true;
        }
    }


    /**
     * Checks whether a User with the same userName is already present in the Database.
     * @param user the User to check for inclusion.
     * @return true if there is already a User with the same userName in the Database.
     */
    private boolean contains(User user) {
        return this.contains(user.getUserName());
    }

    /**
     * Checks whether a User with the same userName is already present in the Database.
     * @param userName the userName to check for inclusion.
     * @return true if there is already a User with the given userName in the Database.
     */
    private boolean contains(String userName) {
        try {
            return this.getUser(userName) != null;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public int getNumberUsers() {
        return this.users.size();
    }

    @Override
    public User getUser(String userName) {
        for (User u: this.users) {
            if (u.getUserName().equals(userName)) {
                return u;
            }
        }
        throw new IllegalArgumentException(userName + " is not a registered user");
    }

    @Override
    public boolean authenticate(String userName, String password) {
        try {
            return this.getUser(userName).getPassword() == password;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public ChatMessage addMessage(String userName, String message) {
        if (this.contains(userName)) {
            this.lastId++;
            ChatMessage chatMessage = new ChatMessage(this.lastId, userName, message);
            this.messages.add(chatMessage);
            return chatMessage;
        } else {
            throw new IllegalArgumentException("User " + userName + " is not registered.");
        }
    }

    @Override
    public int getNumberMessages() {
        return this.messages.size();
    }

    @Override
    public List<ChatMessage> getRecentMessages(int n) {
        if (n > 0 && n < this.messages.size()) {
            return messages.subList(messages.size() - n, messages.size());
        } else {
            return new LinkedList<>(messages);
        }
    }

    @Override
    public List<ChatMessage> getUnreadMessages(String userName) {
        User user = this.getUser(userName);
        final int lastReadId = user.getLastReadId();
        if (lastReadId == this.lastId) {
            return new LinkedList<>();
        } else {
            int firstUnread = 0;
            for (ChatMessage m : this.messages) {
                firstUnread = m.getId();
                if (firstUnread > lastReadId) {
                    break;
                }
            }
            this.getUser(userName).setLastReadId(this.lastId);
            return this.messages.subList(firstUnread - 1, this.messages.size());
        }
    }

    @Override
    public void close() {

    }
}
