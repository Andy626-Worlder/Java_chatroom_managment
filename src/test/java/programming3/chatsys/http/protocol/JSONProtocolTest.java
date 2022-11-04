package programming3.chatsys.http.protocol;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import programming3.chatsys.data.User;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class JSONProtocolTest {
    private final JSONObject jsonJohn = new JSONObject("{username:johndoe, fullname:\"John Doe\", password:thepassword}");
    private final JSONObject jsonJane = new JSONObject("{username:jane, fullname:\"Jane Doe\", password:janespassword}");

    private final User john = new User("johndoe", "John Doe", "thepassword");
    private final User jane = new User("jane", "Jane Doe", "janespassword");

    @Test
    void readUser() throws IOException {
        JSONProtocol protocol = new JSONProtocol(new BufferedReader(new StringReader(jsonJohn.toString())));
        assertEquals(john, protocol.readUser());

        protocol = new JSONProtocol(new BufferedReader(new StringReader(jsonJane.toString())));
        assertEquals(jane, protocol.readUser());
    }

    @Test
    void writeUser() throws IOException {
        StringWriter string = new StringWriter();
        JSONProtocol protocol = new JSONProtocol(new BufferedWriter(string));
        protocol.writeUser(john);
        assertEquals(jsonJohn.toString(), string.toString());

        string = new StringWriter();
        protocol = new JSONProtocol(new BufferedWriter(string));
        protocol.writeUser(jane);
        assertEquals(jsonJane.toString(), string.toString());
    }

    @Test
    void readMessages() {
        // TODO
    }

    @Test
    void writeMessages() {
        // TODO
    }
}