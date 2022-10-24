package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccountTest {
    private UserAccount user1;

    @BeforeEach
    void runBefore() {
        user1 = new UserAccount("user1");
    }

    @Test
    void testUpdateStats() {
        user1.updateStats(100, 5);
        assertEquals(1, user1.getTotalGamesPlayed());
        assertEquals(100,user1.getHighestScore());
        assertEquals(5,user1.getAverageGuesses());

        user1.updateStats(2,5);
        assertEquals(2, user1.getTotalGamesPlayed());
        assertEquals(100,user1.getHighestScore());
        assertEquals(5,user1.getAverageGuesses());
    }

    @Test
    void testMultipleUpdateStats() {                              //1st game with lower score
        user1.updateStats(100, 5);
        assertEquals(1, user1.getTotalGamesPlayed());
        assertEquals(100,user1.getHighestScore());
        assertEquals(5,user1.getAverageGuesses());

        user1.updateStats(300,5);                   //2nd game with higher score
        assertEquals(2, user1.getTotalGamesPlayed());
        assertEquals(300,user1.getHighestScore());
        assertEquals(5,user1.getAverageGuesses());
    }
}
