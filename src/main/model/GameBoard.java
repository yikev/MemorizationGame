package model;

import java.util.ArrayList;

public class GameBoard {
    private int totalCards;
    private static int CORRECT_GUESS = 40;
    private static int INITIAL_SCORE = 100;
    private static int WRONG_GUESS = CORRECT_GUESS / -2;
    private int score;
    private int[] gameCards;
    private int[][] hiddenCards;
    private int columns;
    private int rows = 2;
    private int guess1;
    private int guess2;
    private int totalGuesses;
    private UserAccount user;
    private UserDatabase userBase;

    /*
     * REQUIRES: totalCards must be an even number. There must be at least 2 cards.
     * EFFECTS: Constructs a MatchingGame with a given UserAccount and totalCards.
     */
    public GameBoard(String name, int totalCards) {
        userBase = new UserDatabase();
        this.totalCards = totalCards;
        gameCards = new int[totalCards];
        hiddenCards = new int[2][totalCards / 2];
        user = new UserAccount(name);
        userBase.addUser(user);
        totalGuesses = 0;
        columns = totalCards / 2;
        score = totalCards * INITIAL_SCORE;
    }

    public int[][] getHiddenCards() {
        return hiddenCards;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getGuess1() {
        return guess1;
    }

    public int getGuess2() {
        return guess2;
    }

    public int getTotalCards() {
        return totalCards;
    }

    public int getScore() {
        return score;
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }

    public int[] getGameCards() {
        return gameCards;
    }

    public UserAccount getUser() {
        return user;
    }

    public UserDatabase getUserBase() {
        return userBase;
    }

    /*
     * EFFECTS: Shows the leaderboards. Sorts the scores from
     *          highest to lowest.
     */
    public String getLeaderboards() {
        String leaderboard = "Leaderboards \n";
        ArrayList<UserAccount> tempList = new ArrayList<>(userBase.getList());

        for (int i = 0; i <= tempList.size() - 1; i++) {

            int tempHighest = tempList.get(i).getHighestScore();
            String tempUser = tempList.get(0).getName();

            for (int j = i + 1; j < tempList.size(); j++) {
                if (tempList.get(j).getHighestScore() > tempHighest) {
                    tempHighest = tempList.get(j).getHighestScore();
                    tempUser = tempList.get(j).getName();
                    tempList.remove(j);
                } else {
                    tempHighest = tempList.get(i).getHighestScore();
                    tempUser = tempList.get(i).getName();
                    tempList.remove(i);
                }
                i--;
            }
            leaderboard += tempUser + " " + tempHighest + "\n";
        }

        return leaderboard;
    }

    /*
     * REQUIRES: totalCards must be an even number.
     * MODIFIES: this.
     * EFFECTS:  Changes the total number of cards in the game board.
     */
    public void changeTotalCards(int totalCards) {

        this.totalCards = totalCards;
        score = totalCards * INITIAL_SCORE;
        gameCards = new int[totalCards];
        columns = totalCards / 2;
        hiddenCards = new int[2][totalCards / 2];
        totalGuesses = 0;
    }

    /*
     * MODIFIES: this.
     * EFFECTS: Updates the real time score of the game.
     */
    public void updateScore(int score) {
        this.score += score;
        totalGuesses++;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Updates the users stats after a game is finished and
     *           resets the game.
     */
    public void updateUser() {
        user.updateStats(getScore(),getTotalGuesses());
        resetGame();
    }

    /*
     * MODIFIES: This
     * EFFECTS:  Resets the game board.
     */
    public void resetGame() {
        totalCards = 0;
        score = getTotalCards() * INITIAL_SCORE;
        gameCards = null;
        hiddenCards = null;
        columns = getTotalCards() / 2;
        guess1 = 0;
        guess2 = 0;
        totalGuesses = 0;
    }

    /*
     * REQUIRES: numPairs must greater than 0.
     * MODIFIES: this
     * EFFECTS:  Sets the totalCards value and initalizesCards.
     */
    public void startGame(int numPairs) {
        changeTotalCards(numPairs * 2);
        initializeCards();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Sets the gameCards randomly to a value from
     *           1 to (totalCards / 2), each value being in pairs.
     *           Ex: If totalCards = 4
     *               There will be card values from 1 to 2 in pairs.
     *               4 cards in total: 1, 1, 2, 2 in random order.
     */
    public void initializeCards() {
        for (int i = 1; i <= getTotalCards() / 2; i++) {
            int rndNum = (int) (Math.random() * getTotalCards());
            while (gameCards[rndNum] != 0) {
                rndNum = (int) (Math.random() * getTotalCards());
            }
            gameCards[rndNum] = i;
            while (gameCards[rndNum] != 0) {
                rndNum = (int) (Math.random() * getTotalCards());
            }
            gameCards[rndNum] = i;
        }

    }

    /*
     * EFFECTS: Returns a string containing the x and y headings
     *          and the cards face down/found cards.
     */
    public String displayCards(int[][] cards) {
        String result = "Score: " + getScore() + "\n" + "  " + columnHeadings() + "\n";
        char yaxis = '1';

        for (int i = 0; i < 2; i++) {
            result += (char) (yaxis + i) + " ";
            for (int j = 0; j < getTotalCards() / 2; j++) {
                result += (cards[i][j]) + " ";
            }
            result += "\n";
        }
        return result;
    }

    /*
     * REQUIRES: guess must be in the format 'numberletter' (such as 1a).
     *           guess also must be in bounds of both axis. If it is the 2nd
     *           guess, guess must not be the same as the first guess.
     *           guessNum must be either 1 or 2.
     * MODIFIES: this
     * EFFECTS:  'Flips the face down card face up'.
     */
    public void revealCard(String guess, int guessNum) {
        int row = Integer.parseInt(guess.substring(0,1)) - 1;
        int col = (char) (guess.substring(1).toLowerCase().charAt(0) - 49) - '0';

        if (guessNum == 1) {
            guess1 = gameCards[row * columns + col];
        } else {
            guess2 = gameCards[row * columns + col];
        }

        hiddenCards[row][col] = gameCards[row * columns + col];
    }

    /*
     * REQUIRES: guess must be in the format 'numberletter' (such as 1a).
     *           guess also must be in bounds of both axis. If it is the 2nd
     *           guess, guess must not be the same as the first guess.
     * MODIFIES: this
     * EFFECTS:  'Flips the face up guess card back to face down'.
     */
    public void hideCard(String guess) {
        int row = Integer.parseInt(guess.substring(0,1)) - 1;
        int col = (char) (guess.substring(1).toLowerCase().charAt(0) - 49) - '0';

        hiddenCards[row][col] = 0;
    }

    /*
     * EFFECTS: Returns a string containing the 'x-axis' headings.
     */
    public String columnHeadings() {
        char letter = 65;                                                     //65 is ASCII capital A
        String colHeading = "";

        for (int i = 0; i < columns; i++) {
            colHeading += (char) (letter + i) + " ";
        }

        return colHeading;
    }

    /*
     * REQUIRES: guess must be in the format 'numberletter' (such as 1a).
     *           guess also must be in bounds of both axis. If it is the 2nd
     *           guess, guess must not be the same as the first guess.
     * MODIFIES: this
     * EFFECTS:  Checks if the guesses were correct. If incorrect, hides the
     *           guessed cards. Updates the score appropriately.
     */
    public void checkGuesses(String guess1, String guess2) {
        if (getGuess1() == getGuess2()) {
            updateScore(CORRECT_GUESS);
        } else {
            hideCard(guess1);
            hideCard(guess2);
            updateScore(WRONG_GUESS);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Checks if all pairs of cards have been found. If so,
     *           update the user's stats.
     */
    public boolean isGameOver() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                if (getHiddenCards()[i][j] == 0) {
                    return false;
                }
            }
        }
        updateUser();
        return true;
    }

    /*
     * REQUIRES: Name must not exist in the UserDataBase.
     * MODIFIES: this
     * EFFECTS:  Sets a new player to play the game.
     */
    public void changeUser(String name) {
        UserAccount newUser = new UserAccount(name);
        userBase.addUser(newUser);
        user = newUser;
    }
}
