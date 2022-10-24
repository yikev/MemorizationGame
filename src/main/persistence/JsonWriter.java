package persistence;

import model.UserDatabase;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.io.*;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //cite source
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    //cite source
    public void write(UserDatabase ub) {
        JSONObject json = ub.toJson();
        saveToFile(json.toString(TAB));
    }

    //cite source
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
