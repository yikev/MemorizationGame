package model;

public class GameBoard {
    private int totalCards;
    private UserAccount user;
    private int score;

    /*
     * REQUIRES: totalCards must be an even number. There must be at least 2 cards.
     * EFFECTS: Constructs a MatchingGame with a given UserAccount and totalCards.
     */
    public GameBoard(UserAccount user, int totalCards) {
        this.user = user;
        this.totalCards = totalCards;
    }

    /*
     * REQUIRES: totalCards must be an even number.
     * MODIFIES: this.
     * EFFECTS: Changes the total number of cards in the game board.
     */
    public void changeTotalCards(int totalCards) {
        this.totalCards = totalCards;
    }

    /*
     * MODIFIES: this.
     * EFFECTS: Updates the real time score of the game.
     */
    public void updateScore(int score) {
        this.score += score;
    }

}
