package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    private GameBoard gb1;
    private GameBoard gb2;
    private GameBoard gb3;

    @BeforeEach
    void runBefore() {
        gb1 = new GameBoard("user1", 10);
        gb1.startGame(10);

        gb2 = new GameBoard("user2", 2);
        gb2.startGame(1);

        gb3 = new GameBoard("user3", 5);
        gb3.startGame(5);
    }

    @Test
    void testChangeTotalCards() {
        gb1.changeTotalCards(16);

        assertEquals(16,gb1.getTotalCards());
        assertEquals(8,gb1.getColumns());
    }

    @Test
    void testUpdateScore() {
        gb1.updateScore(20);

        assertEquals(2020, gb1.getScore());         //initial score is 2000. +20 after updating
        assertEquals(1, gb1.getTotalGuesses());     //updating the score implies that guesses were made

        gb1.updateScore(-20);

        assertEquals(2000, gb1.getScore());
        assertEquals(2, gb1.getTotalGuesses());
    }

    @Test
    void testResetGame() {
        gb1.updateScore(20);
        gb1.resetGame();

        assertEquals(0, gb1.getTotalCards());
        assertEquals(0,gb1.getScore());
        assertEquals(0, gb1.getColumns());
        assertEquals(0,gb1.getGuess1());
        assertEquals(0, gb1.getGuess2());
        assertEquals(0, gb1.getTotalGuesses());
    }

    @Test
    void testInitializeCards() {
        List<Integer> gameCardsList = new ArrayList<>();

        for (int i : gb1.getGameCards()) {
            gameCardsList.add(i);
        }

        assertEquals(20,gb1.getGameCards().length); // 20 cards means there are 10 pairs valued 1 - 10
        assertFalse(gameCardsList.contains(11));             // should not contain 11
        assertTrue(gameCardsList.contains(10));              // should contain values 1 - 10.
        assertTrue(gameCardsList.contains(9));
        assertTrue(gameCardsList.contains(8));
        assertTrue(gameCardsList.contains(7));
        assertTrue(gameCardsList.contains(6));
        assertTrue(gameCardsList.contains(5));
        assertTrue(gameCardsList.contains(4));
        assertTrue(gameCardsList.contains(3));
        assertTrue(gameCardsList.contains(2));
        assertTrue(gameCardsList.contains(1));
    }

//    @Test
//    void testDisplayCards() {
//
//    }

    @Test
    void testRevealCards() {
        gb2.revealCard("1a", 1);             //this game only contains 2 cards,
                                                              //so only one card value: 1
        assertEquals(1,gb2.getHiddenCards()[0][0]);  //guess 1a is [0][0] and it should be "face up" show its
        assertEquals(0,gb2.getHiddenCards()[1][0]);  //card value.
    }

    @Test
    void testHideCard() {
        gb2.revealCard("1a", 1);
        assertEquals(1,gb2.getHiddenCards()[0][0]);  //reveal card
        assertEquals(0,gb2.getHiddenCards()[1][0]);

        gb2.hideCard("1a");
        assertEquals(0,gb2.getHiddenCards()[0][0]);  //hide card
        assertEquals(0,gb2.getHiddenCards()[1][0]);
    }

    @Test
    void testColumnHeadings() {
        String heading = gb3.columnHeadings();                //5 columns means A B C D E

        assertTrue(heading.contains("A"));
        assertTrue(heading.contains("B"));
        assertTrue(heading.contains("C"));
        assertTrue(heading.contains("D"));
        assertTrue(heading.contains("E"));
        assertFalse(heading.contains("F"));
    }

    @Test
    void testCheckGuesses() {
        int initialScore = gb2.getScore();
        int gb1initialScore = gb1.getScore();
        int correctGuess = 40;
        int wrongGuess = correctGuess / -2;
        gb2.checkGuesses("1a","2a");
        assertEquals(initialScore + correctGuess, gb2.getScore());

        gb1.revealCard("1a", 1);
        gb1.revealCard("2a",2);
        gb1.checkGuesses("1a","2a");
        assertEquals(gb1initialScore + wrongGuess, gb1.getScore());
    }

    @Test
    void testIsGameOver() {
        gb2.revealCard("1a",1);
        gb2.revealCard("2a", 2);
        assertTrue(gb2.isGameOver());

        gb1.revealCard("1a", 1);
        gb1.revealCard("2a", 2);
        assertFalse(gb1.isGameOver());
    }

    @Test
    void testGetLeaderboards() {
        gb2.revealCard("1a",1);                //Play the game and finish it
        gb2.revealCard("2a", 2);
        gb2.checkGuesses("1a","2a");
        String userScore = Integer.toString(gb2.getScore());    //hold the user's final score before game resets
        assertEquals(240,gb2.getScore());
        assertTrue(gb2.isGameOver());                           //Game is finished

        String user = gb2.getUser().getName();
        String leaderboard = gb2.getLeaderboards();

        assertTrue(leaderboard.contains(user));                 //should contain the user
        assertTrue(leaderboard.contains(userScore));            //should contain the score of the user
    }

    @Test
    void testChangeUser() {
        assertEquals("USER2", gb2.getUser().getName());
        assertTrue(gb2.changeUser("bob"));
        assertEquals("BOB", gb2.getUser().getName());
        assertFalse(gb2.changeUser("user2"));
    }
}