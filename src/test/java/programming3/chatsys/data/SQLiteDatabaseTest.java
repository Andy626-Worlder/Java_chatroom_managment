package programming3.chatsys.data;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteDatabaseTest extends InMemoryDatabaseTest {
    private final File DB_FILE = new File("test_db.sqlite");

    @Override
    protected void initDatabase() {
        DB_FILE.delete();
        db = new SQLiteDatabase("jdbc:sqlite:" + DB_FILE.getPath());
    }

    @Test
    @Override
    public void close() {
        assertEquals(0, this.db.getNumberUsers());
        this.register();
        assertEquals(2, this.db.getNumberUsers());
        super.close();
        Database freshDb = new SQLiteDatabase("jdbc:sqlite:" + DB_FILE.getPath());
        System.out.println(freshDb.getNumberUsers());
        assertEquals(2, freshDb.getNumberUsers());
        assertEquals(user1, freshDb.getUser("johndoe"));
        assertEquals(user2, freshDb.getUser("jane"));
        freshDb.close();
    }
}