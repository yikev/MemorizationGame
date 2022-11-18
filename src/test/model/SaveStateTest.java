package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaveStateTest {
    SaveState s1;
    int[] gameCards;
    int[] hiddenCards;

    @BeforeEach
    void runBefore() {
        s1 = new SaveState();
        gameCards = new int[10];
        hiddenCards = new int[10];
    }

    @Test
    void testUpdateSaveState() {
        s1.updateSaveState(40,40,40,gameCards,hiddenCards);

        assertEquals(40,s1.getScore());
        assertEquals(40, s1.correctGuesses);
        assertEquals(40, s1.getTotalGuesses());
        assertEquals(gameCards, s1.getGameCards());
        assertEquals(hiddenCards, s1.getHiddenCards());
    }

    @Test
    void testSetScore() {
        s1.setScore(100);

        assertEquals(100, s1.getScore());
    }

    @Test
    void testSetCorrectGuesses() {
        s1.setCorrectGuesses(100);

        assertEquals(100, s1.getScore());
    }

    @Test
    void testSetTotalGuesses() {
        s1.setTotalGuesses(100);

        assertEquals(100, s1.getScore());
    }

    @Test
    void testSetGameCards() {
        int[] newCards = new int[20];
        s1.setGameCards(newCards);
        assertEquals(newCards, s1.getGameCards());
    }

    @Test
    void testSetHiddenCards() {
        int[] newCards = new int[20];
        s1.setHiddenCards(newCards);
        assertEquals(newCards, s1.getHiddenCards());
    }
}
