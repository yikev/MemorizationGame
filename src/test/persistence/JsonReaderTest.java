package persistence;

import model.UserAccount;
import model.UserDatabase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    // Method was taken from JsonReaderTest class in:
    // https://github.com/stleary/JSON-java
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            UserDatabase ud = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //caught expected exception
        }
    }

    // Method was taken from JsonReaderTest class in:
    // https://github.com/stleary/JSON-java
    @Test
    void testReaderEmptyUserDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyUserDatabase.json");
        try {
            UserDatabase ud = reader.read();
            assertEquals(0, ud.getSize());
        } catch (IOException e) {
            fail("File reading failed.");
        }
    }

    // Method was taken from JsonReaderTest class in:
    // https://github.com/stleary/JSON-java
    @Test
    void testReaderGeneralUserDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUserDatabase.json");
        try {
            UserDatabase ud = reader.read();
            List<UserAccount> users = ud.getList();
            assertEquals(2, users.size());
            checkUser("POP", 460,1, 3, users.get(0));
            checkUser("YRO", 700,1, 4, users.get(1));
        } catch (IOException e) {
            fail("File reading failed.");
        }
    }
}
