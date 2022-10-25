package persistence;

import model.UserAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Class was taken from JsonTest in:
// https://github.com/stleary/JSON-java
public class JsonTest {
    protected void checkUser(String name, int highestScore, int totalGamesPlayed,
                              double averageGuesses, UserAccount user) {
        assertEquals(name, user.getName());
        assertEquals(highestScore, user.getHighestScore());
        assertEquals(totalGamesPlayed, user.getTotalGamesPlayed());
        assertEquals(averageGuesses, user.getAverageGuesses());
    }
}
