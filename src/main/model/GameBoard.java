package model;

import java.util.ArrayList;

//The game board which holds a current player, a database of players and
//the game cards.
public class GameBoard {
    private static int MAXIMUM_COLUMNS = 8;
    private static int CORRECT_GUESS = 40;
    private static int INITIAL_SCORE = 100;
    private static int WRONG_GUESS = CORRECT_GUESS / -2;
    private static String BREAK_LINE = "<br/>";
    private int totalCards;
    private int score;
    private int columns;                      //saved for console use(p1)
    private int rows = 2;                     //saved for console use(p1)
    private int guess1;                       //saved for console use(p1)
    private int guess2;                       //saved for console use(p1)
    private int totalGuesses;
    private int correctGuesses;
    private int[] gameCards;
    private int[] hiddenCards;
    private int[][] hiddenCardsConsole;              //saved for console use(p1)
    private UserAccount user;
    private UserDatabase userBase;
    private boolean gameStarted;
    private SaveState save;

    /*
     * REQUIRES: totalCards must be an even number. There must be at least 2 cards.
     * EFFECTS: Constructs a MatchingGame with a given UserAccount and totalCards.
     */
    public GameBoard(String name, int totalCards) {
        this.totalCards = totalCards;
        userBase = new UserDatabase();
        gameCards = new int[totalCards];
        hiddenCardsConsole = new int[2][totalCards / 2];             //saved for console use(p2)
        user = new UserAccount(name);
        userBase.addUser(user);
        totalGuesses = 0;
        correctGuesses = 0;
        columns = totalCards / 2;                             //saved for console use(p2)
        score = totalCards * INITIAL_SCORE;
        gameStarted = false;
        save = new SaveState(score,correctGuesses,totalGuesses,gameCards,hiddenCards);
    }

    public int[][] getHiddenCardsConsole() {
        return hiddenCardsConsole;
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

    public SaveState getSave() {
        return save;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    /*
     * EFFECTS: Shows the leaderboards. Sorts the scores from
     *          highest to lowest.
     */
    public String getLeaderboards() {
        String leaderboard = "<html>Leaderboards" + BREAK_LINE;
        ArrayList<UserAccount> tempList = new ArrayList<>(userBase.getList());

        while (tempList.size() != 0) {
            int tempHighest = tempList.get(0).getHighestScore();
            String tempUser = tempList.get(0).getName();
            int index = 0;

            for (int j = 1; j < tempList.size(); j++) {
                if (tempList.get(j).getHighestScore() > tempHighest) {
                    tempHighest = tempList.get(j).getHighestScore();
                    tempUser = tempList.get(j).getName();
                    index = j;
                }
            }
            tempList.remove(index);
            leaderboard += tempUser + " " + tempHighest + BREAK_LINE;
        }

        leaderboard += "</html>";

        return leaderboard;
    }

    public void setUserBase(UserDatabase userBase) {
        this.userBase = userBase;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Loads the SaveState save from data/savestate.json
     */
    public void setSaveState(SaveState save) {
        EventLog.getInstance().logEvent(new Event("Loading game..."));
        //changeTotalCards(save.getGameCards().length);
        setTotalCards(save.getGameCards().length);
        gameCards = save.getGameCards();
        hiddenCards = save.getHiddenCards();
        score = save.getScore();
        correctGuesses = save.getCorrectGuesses();
        totalGuesses = save.getTotalGuesses();
        gameStarted = true;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Sets the total cards to a given value.
     */
    public void setTotalCards(int totalCards) {
        this.totalCards = totalCards;
        score = totalCards * INITIAL_SCORE;
        gameCards = new int[totalCards];
        hiddenCards = new int[totalCards];
    }

    /*
     * MODIFIES: this.
     * EFFECTS:  Removes the most recently added UserAccount. Sets the UserAccount
     *           to the last UserAccount in the UserBase
     */
    public void loadLastUser() {
        EventLog.getInstance().logEvent(new Event("Loading previous user..."));
        this.user = null;
        userBase.removeLastUser();
        this.user = userBase.getList().get(userBase.getSize() - 1);
    }

    /*
     * REQUIRES: totalCards must be an even number.
     * MODIFIES: this.
     * EFFECTS:  Changes the total number of cards in the game board.
     */
    public void changeTotalCards(int totalCards) {
        if ((totalCards - this.totalCards) < 0) {
            EventLog.getInstance().logEvent(new Event("Removed a pair of cards."));
        } else if ((totalCards - this.totalCards) > 0) {
            EventLog.getInstance().logEvent(new Event("Added a pair of cards."));
        }
        this.totalCards = totalCards;
        score = totalCards * INITIAL_SCORE;
        gameCards = new int[totalCards];
        hiddenCards = new int[totalCards];
        columns = totalCards / 2;
        hiddenCardsConsole = new int[2][totalCards / 2];          //saved for console use
        totalGuesses = 0;
        correctGuesses = 0;
        gameStarted = false;
    }

    /*
     * MODIFIES: this.
     * EFFECTS:  Updates the real time score of the game.
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
        correctGuesses = 0;
        gameStarted = false;
        EventLog.getInstance().logEvent(new Event("Game is reset."));
    }

    /*
     * REQUIRES: numPairs must greater than 0.
     * MODIFIES: this
     * EFFECTS:  Sets the totalCards value and initalizesCards.
     */
    public void startGame(int numPairs) {
        setTotalCards(numPairs);
        EventLog.getInstance().logEvent(new Event("Game started with " + totalCards + " cards."));
        //changeTotalCards(numPairs);
        initializeCards();
        gameStarted = true;
    }

    /*
     * REQUIRES: numPairs must greater than 0.
     * MODIFIES: this
     * EFFECTS:  Sets the totalCards value and initalizesCards.
     */
    public void startGameConsole(int numPairs) {
        changeTotalCards(numPairs * 2);
        initializeCards();
        gameStarted = true;
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

        hiddenCardsConsole[row][col] = gameCards[row * columns + col];
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

        hiddenCardsConsole[row][col] = 0;
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
    public boolean checkGuesses(int guess1, int guess2) {
        if (gameCards[guess1] == gameCards[guess2]) {
            hiddenCards[guess1] = 1;
            hiddenCards[guess2] = 1;
            correctGuesses++;
            updateScore(CORRECT_GUESS);
            EventLog.getInstance().logEvent(new Event("Correct guess for: " + guessToString(guess1)
                    + " and " + guessToString(guess2)));
            return true;
        } else {
            EventLog.getInstance().logEvent(new Event("Wrong guess for: " + guessToString(guess1)
                    + " and " + guessToString(guess2)));
            updateScore(WRONG_GUESS);
            return false;
        }
    }

    /*
     * REQUIRES: guess must be in the format 'numberletter' (such as 1a).
     *           guess also must be in bounds of both axis. If it is the 2nd
     *           guess, guess must not be the same as the first guess.
     * MODIFIES: this
     * EFFECTS:  Checks if the guesses were correct. If incorrect, hides the
     *           guessed cards. Updates the score appropriately.
     */
    public void checkGuessesConsole(String guess1, String guess2) {
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
    public boolean isGameOverConsole() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                if (getHiddenCardsConsole()[i][j] == 0) {
                    return false;
                }
            }
        }
        updateUser();
        return true;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Checks if all pairs of cards have been found. If so,
     *           update the user's stats.
     */
    public boolean isGameOver() {
        if (correctGuesses * 2 >= totalCards) {
            EventLog.getInstance().logEvent(new Event("Game Finished"));
            return true;
        }
        return false;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Updates the save state. Updates the score, correct guesses,
     *           total guesses, game cards, and hidden cards
     */
    public void saveGame() {
        save.updateSaveState(score,correctGuesses,totalGuesses,gameCards,hiddenCards);
        EventLog.getInstance().logEvent(new Event("Game saved."));
    }

    /*
     * REQUIRES: Name must not exist in the UserDataBase.
     * MODIFIES: this
     * EFFECTS:  Sets a new player to play the game.
     */
    public boolean changeUser(String name) {

        for (UserAccount ua : userBase.getList()) {
            if (ua.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        UserAccount newUser = new UserAccount(name);
        userBase.addUser(newUser);
        EventLog.getInstance().logEvent(new Event("Changed user from: "
                + user.getName() + " to " + newUser.getName()));
        user = newUser;

        return true;
    }

    // Method was taken from LogPrinter class in:
    // https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Converts a user guess to a string. The row and column numbers.
     */
    public String guessToString(int guess) {
        int temp = guess;
        int row = 1;

        while (temp / MAXIMUM_COLUMNS != 0) {
            row++;

            temp /= MAXIMUM_COLUMNS;
        }

        return "Row:" + row + " Column:" + ((temp % MAXIMUM_COLUMNS) + 1);
    }

}
