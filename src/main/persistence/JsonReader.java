package persistence;

import model.SaveState;
import model.UserDatabase;
import model.UserAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Data persistence, reads data.
public class JsonReader {
    private String source;

    // Method was taken from JsonReader class in:
    // https://github.com/stleary/JSON-java
    // EFFECTS:  constructs JsonReader to read from file source.
    public JsonReader(String source) {
        this.source = source;
    }

    // Method was taken from JsonReader class in:
    // https://github.com/stleary/JSON-java
    // EFFECTS:  Reads UserDatabase from source file and returns it.
    //           IOException thrown if error during readFile(source).
    public UserDatabase read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUserBase(jsonObject);
    }

    // Method was taken from JsonReader class in:
    // https://github.com/stleary/JSON-java
    // EFFECTS:  Reads SaveState from source file and returns it.
    //           IOException thrown if error during readFile(source).
    public SaveState readGameCards() throws IOException {
        String jsonData = readFile(source);
        if (jsonData.isEmpty()) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameCards(jsonObject);
    }

    // Method was taken from JsonReader class in:
    // https://github.com/stleary/JSON-java
    // EFFECTS:  Reads source file as string and returns it.
    //           IOException thrown if error during readFile(source).
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // Method was taken from JsonReader class in:
    // https://github.com/stleary/JSON-java
    // EFFECTS: Parses UserDatabase from JSON object and returns it.
    private UserDatabase parseUserBase(JSONObject jsonObject) {
        UserDatabase udb = new UserDatabase();
        addUsers(udb, jsonObject);
        return udb;
    }

    // Method was taken from JsonReader class in:
    // https://github.com/stleary/JSON-java
    // EFFECTS: Parses SaveState from JSON object and returns it.
    private SaveState parseGameCards(JSONObject jsonObject) {
        SaveState save = new SaveState();
        addCards(save, jsonObject);
        return save;
    }

    // Method was taken from JsonReader class in:
    // https://github.com/stleary/JSON-java
    // MODIFIES: udb, UserDatabase
    // EFFECTS:  Parses UserAccounts from JSON object and adds them to UserDataBase.
    private void addUsers(UserDatabase udb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(udb, nextUser);
        }
    }

    // Method was taken from JsonReader class in:
    // https://github.com/stleary/JSON-java
    // MODIFIES: save, SaveState
    // EFFECTS:  Parses GameCards from JSON object and adds them to SaveState.
    private void addCards(SaveState save, JSONObject jsonObject) {
        int[] gameCards;
        int[] hiddenCards;

        int i = 0;

        save.setScore(jsonObject.getInt("score"));
        save.setCorrectGuesses(jsonObject.getInt("guesses"));
        save.setTotalGuesses(jsonObject.getInt("total"));

        JSONArray jsonArrayGameCards = jsonObject.getJSONArray("cards");
        JSONArray jsonArrayHiddenCards = jsonObject.getJSONArray("hidden cards");
        gameCards = new int[jsonArrayGameCards.length()];
        hiddenCards = new int[jsonArrayGameCards.length()];
        for (Object json : jsonArrayGameCards) {
            gameCards[i] = (int) json;
            i++;
        }

        i = 0;

        for (Object json : jsonArrayHiddenCards) {
            hiddenCards[i] = (int) json;
            i++;
        }

        save.setGameCards(gameCards);
        save.setHiddenCards(hiddenCards);

    }

    // Method was taken from JsonReader class in:
    // https://github.com/stleary/JSON-java
    // MODIFIES: udb, UserDatabase
    // EFFECTS:  Parses UserAccount from JSON object and adds it to UserDatabase.
    private void addUser(UserDatabase udb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        UserAccount user = new UserAccount(name);
        user.setHighestScore(jsonObject.getInt("score"));
        user.setTotalGamesPlayed(jsonObject.getInt("games played"));
        user.setAverageGuesses(jsonObject.getDouble("average guesses"));
        udb.addUser(user);
    }
}
