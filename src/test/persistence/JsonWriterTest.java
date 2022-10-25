package persistence;

import model.UserAccount;
import model.UserDatabase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    // Method was taken from JsonWriterTest class in:
    // https://github.com/stleary/JSON-java
    @Test
    void testWriterInvalidFile() {
        try {
            UserDatabase ud = new UserDatabase();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //caught expected exception
        }
    }

    // Method was taken from JsonWriterTest class in:
    // https://github.com/stleary/JSON-java
    @Test
    void testWriterEmptyUserDatabase() {
        try {
            UserDatabase ud = new UserDatabase();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyUserDatabase.json");
            writer.open();
            writer.write(ud);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyUserDatabase.json");
            ud = reader.read();
            assertEquals(0, ud.getSize());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    // Method was taken from JsonWriterTest class in:
    // https://github.com/stleary/JSON-java
    @Test
    void testWriterGeneralUserDatabase() {
        try {
            UserDatabase ud = new UserDatabase();
            ud.addUser(new UserAccount("POP"));
            ud.addUser(new UserAccount("YRO"));
            ud.getList().get(0).setHighestScore(460);
            ud.getList().get(0).setAverageGuesses(3);
            ud.getList().get(0).setTotalGamesPlayed(1);
            ud.getList().get(1).setHighestScore(700);
            ud.getList().get(1).setAverageGuesses(4);
            ud.getList().get(1).setTotalGamesPlayed(1);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(ud);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            ud = reader.read();
            List<UserAccount> users = ud.getList();
            assertEquals(2, users.size());
            checkUser("POP", 460,1, 3, users.get(0));
            checkUser("YRO", 700,1, 4, users.get(1));

        } catch (IOException e) {
            fail("Exception not expected");
        }
    }
}
