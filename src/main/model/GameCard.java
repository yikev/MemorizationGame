package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;

public class GameCard extends JButton implements Writable {
    int coordinateX;
    int coordinateY;
    int height;
    int width;
    JButton card;
    boolean isFound; //hidden card

    public GameCard(int coordinateX, int coordinateY, int width, int height) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.width = width;
        this.height = height;
        card = new JButton();
        isFound = false;
    }

    public void setIsFound(boolean isFound) {
        this.isFound = isFound;
    }

    public boolean getIsFound() {
        return isFound;
    }

    // Method was taken from Thingy class in:
    // https://github.com/stleary/JSON-java
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("card", card);
        return json;
    }
}
