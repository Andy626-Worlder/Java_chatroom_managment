package programming3.chatsys.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDatabaseTest {
    protected User user1;
    protected User user2;
    protected User user3;
    protected User user4;
    protected User user5;
    protected User user6;
    protected Database db;

    @BeforeEach
    void setUp() {
        user1 = new User("johndoe", "John Doe", "thepassword");
        user2 = new User("jane", "Jane Doe", "janespassword");
        user3 = new User("johndoe", "John Doe", "thepassword");
        user4 = new User("johndoe", "John Doe", "anotherpassword");
        user5 = new User("johndoe", "John Doe 2", "anotherpassword");
        user6 = user1;
        this.initDatabase();
    }

    protected void initDatabase() {
        db = new InMemoryDatabase();
    }

    @AfterEach
    void tearDown() {
        db.close();
    }

    @Test
    void register() {
        assertTrue(db.register(user1));
        assertTrue(db.register(user2));
        assertFalse(db.register(user3));
        assertFalse(db.register(user4));
        assertFalse(db.register(user5));
        assertFalse(db.register(user6));
    }

    @Test
    void getNumberUsers() {
        assertEquals(0, db.getNumberUsers());
        db.register(user1);
        assertEquals(1, db.getNumberUsers());
        db.register(user2);
        assertEquals(2, db.getNumberUsers());
        db.register(user3);
        assertEquals(2, db.getNumberUsers());
        db.register(user4);
        assertEquals(2, db.getNumberUsers());
        db.register(user5);
        assertEquals(2, db.getNumberUsers());
        db.register(user6);
        assertEquals(2, db.getNumberUsers());
    }

    @Test
    void getUser() {
        db.register(user1);
        db.register(user2);
        assertEquals(user1, db.getUser("johndoe"));
        assertEquals(user2, db.getUser("jane"));
        assertThrows(IllegalArgumentException.class, () -> {
            db.getUser("john");
        });
    }

    @Test
    void authenticate() {
        db.register(user1);
        assertTrue(db.authenticate("johndoe", "thepassword"));
        assertFalse(db.authenticate("johndoe", "wrongpassword"));
        assertFalse(db.authenticate("john", "thepassword"));
    }

    @Test
    void addMessage() {
    }

    @Test
    void getNumberMessages() {
    }

    @Test
    void getRecentMessages() {
    }

    @Test
    void getUnreadMessages() {
    }

    @Test
    void close() {
        this.db.close();
    }
}