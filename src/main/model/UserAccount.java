package model;

public class UserAccount {
    private static int nextGameId = 1;  // I attribute this line from Account.java that was provided as an example
    private int id;
    private String name;
    private int highestScore = 0;
    private int totalGamesPlayed = 0;
    private double averageGuesses = 0;

    /*
     * REQUIRES: Name must not be already in the database.
     * EFFECTS: Constructs a UserAccount with a given name.
     */
    public UserAccount(String name) {
        nextGameId++;
        id = nextGameId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public double getAverageGuesses() {
        return averageGuesses;
    }

    /*
     * REQUIRES: score must be greater than 0.
     *           guesses must be greater than or equal to the
     *           total cards in the matching game.
     * MODIFIES: this.
     * EFFECTS:  Updates the UserAccount's stats.
     *           Checks if there is a new high score.
     *           Updates the totalGamesPlayed and averageGuesses.
     */
    public void updateStats(int score, int guesses) {
        if (score > highestScore) {
            highestScore = score;
        }

        averageGuesses = ((averageGuesses * totalGamesPlayed + guesses) / totalGamesPlayed + 1);
        totalGamesPlayed++;
    }

    /*
     *EFFECTS: Returns a string containing the user's stats.
     *         Their highest score, average guesses and
     *         games played.
     */
    public String retrieveStats() {
        String stats = "";
        stats += name + "'s Stats. \n" + "Highest Score: " + highestScore
                + "\n Average Guesses: " + averageGuesses + "\n"
                + "Games Played: " + totalGamesPlayed;

        return stats;
    }
}
