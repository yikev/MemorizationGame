package model;

public class UserAccount {
    private String name;
    private int highestScore = 0;
    private int totalGamesPlayed = 0;
    private double averageGuesses = 0;

    /*
     * REQUIRES: Name must not exist in the database.
     * EFFECTS: Constructs a UserAccount with a given name.
     */
    public UserAccount(String name) {
        this.name = name;
    }

    public String getName() {
        return name.toUpperCase();
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
     *EFFECTS: Returns a string containing the user's stats.
     *         Their highest score, average guesses and
     *         games played.
     *
     */
    public String getUserStats() {
        return name + "'s Stats. \n" + "Highest Score: " + getHighestScore()
                + "\n Average Guesses: " + getAverageGuesses() + "\n"
                + "Games Played: " + getTotalGamesPlayed();
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
        if (score > getHighestScore()) {
            highestScore = score;
        }

        averageGuesses = ((getAverageGuesses() * getTotalGamesPlayed() + guesses) / (getTotalGamesPlayed() + 1));
        totalGamesPlayed++;
    }
}
