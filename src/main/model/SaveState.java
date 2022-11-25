package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// An instance of the MemoryGame.
public class SaveState implements Writable {
    int score;
    int correctGuesses;
    int totalGuesses;
    int[] gameCards;
    int[] hiddenCards;

    public SaveState() {
        this.score = 0;
        this.correctGuesses = 0;
        this.totalGuesses = 0;
        this.gameCards = null;
        this.hiddenCards = null;
    }

    public SaveState(int score, int correctGuesses, int totalGuesses, int[] gameCards, int[] hiddenCards) {
        this.score = score;
        this.correctGuesses = correctGuesses;
        this.totalGuesses = totalGuesses;
        this.gameCards = gameCards;
        this.hiddenCards = hiddenCards;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Sets the score, correctGuesses, totalGuesses
     */
    public void updateSaveState(int score, int correctGuesses, int totalGuesses, int[] gameCards, int[] hiddenCards) {
        this.score = score;
        this.correctGuesses = correctGuesses;
        this.totalGuesses = totalGuesses;
        this.gameCards = gameCards;
        this.hiddenCards = hiddenCards;
    }

    public int getScore() {
        return score;
    }

    public int getCorrectGuesses() {
        return correctGuesses;
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }

    public int[] getGameCards() {
        return gameCards;
    }

    public int[] getHiddenCards() {
        return hiddenCards;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCorrectGuesses(int correctGuesses) {
        this.correctGuesses = correctGuesses;
    }

    public void setTotalGuesses(int totalGuesses) {
        this.totalGuesses = totalGuesses;
    }

    public void setGameCards(int[] gameCards) {
        this.gameCards = gameCards;
    }

    public void setHiddenCards(int[] hiddenCards) {
        this.hiddenCards = hiddenCards;
    }

    // Method was taken from Thingy class in:
    // https://github.com/stleary/JSON-java
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("score", score);
        json.put("guesses", correctGuesses);
        json.put("total", totalGuesses);
        json.put("cards", cardsToJson());
        json.put("hidden cards", hiddenCardsToJson());
        return json;
    }

    // Method was taken from WorkRoom class in:
    // https://github.com/stleary/JSON-java
    /*
     * EFFECTS:  UserAccounts in current UserDatabase stored in jsonArray.
     */
    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int card : gameCards) {
            jsonArray.put(card);
        }

        return jsonArray;
    }

    private JSONArray hiddenCardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int card : hiddenCards) {
            jsonArray.put(card);
        }

        return jsonArray;
    }

}
