package persistence;

import model.UserDatabase;
import model.UserAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public UserDatabase read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUserBase(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private UserDatabase parseUserBase(JSONObject jsonObject) {
        //String name = jsonObject.getString("name");
        UserDatabase ub = new UserDatabase();
        addUsers(ub, jsonObject);
        return ub;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addUsers(UserDatabase ub, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(ub, nextUser);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addUser(UserDatabase ub, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        UserAccount user = new UserAccount(name);
        user.setHighestScore(jsonObject.getInt("score"));
        user.setTotalGamesPlayed(jsonObject.getInt("games played"));
        user.setAverageGuesses(jsonObject.getDouble("average guesses"));
        ub.addUser(user);
    }
}
