package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for UserDatabase class.
public class UserDatabaseTest {
    private UserDatabase users1;
    private UserAccount userA;
    private UserAccount userB;

    @BeforeEach
    void runBefore() {
        users1 = new UserDatabase();
        userA = new UserAccount("Bob");
        userB = new UserAccount(("Lee"));
        users1.addUser(userA);
    }

    @Test
    void testGetStats() {
        assertEquals("User does not exist.", users1.getStats("userB"));
        assertEquals(userA.getName(), users1.getList().get(0).getName());
    }

    @Test
    void testAddUser() {
        assertEquals(1, users1.getSize());
        users1.addUser(userB);
        String newUserName = userB.getName();
        assertEquals(2, users1.getSize());
        assertEquals(newUserName,users1.getList().get(1).getName());
    }
}
