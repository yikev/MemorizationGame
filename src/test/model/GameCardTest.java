package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameCardTest {
    GameCard g1;

    @BeforeEach
    void runBefore() {
        g1 = new GameCard(100, 100 ,20 ,30);
    }

    @Test
    void testIsFound() {
        assertFalse(g1.getIsFound());
        g1.setIsFound(true);
        assertTrue(g1.getIsFound());
    }
}
