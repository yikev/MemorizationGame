package persistence;

import model.UserDatabase;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // Method was taken from JsonWriter class in:
    // https://github.com/stleary/JSON-java
    // EFFECTS:  Sets destination file name for JsonWriter.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Method was taken from JsonWriter class in:
    // https://github.com/stleary/JSON-java
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // Method was taken from JsonWriter class in:
    // https://github.com/stleary/JSON-java
    public void write(UserDatabase ub) {
        JSONObject json = ub.toJson();
        saveToFile(json.toString(TAB));
    }

    // Method was taken from JsonWriter class in:
    // https://github.com/stleary/JSON-java
    public void close() {
        writer.close();
    }

    // Method was taken from JsonWriter class in:
    // https://github.com/stleary/JSON-java
    // MODIFIES: this
    // EFFECTS:  Writes to file.
    private void saveToFile(String json) {
        writer.print(json);
    }
}
