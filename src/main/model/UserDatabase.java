package model;

import java.util.ArrayList;

public class UserDatabase {

    private int highestUserScore = 0;
    private ArrayList<UserAccount> users;

    public UserDatabase() {
        users = new ArrayList<>();
    }

    public int getHighestUserScore() {
        return highestUserScore;
    }

    /*
     * EFFECTS: Prints all the users in the database along with their score.
     */
    public String printUsers() {
        String result = "";

        result += "Users                    Scores";
        for (int i = 0; i < users.size(); i++) {
            result += users.get(i) + "                    " + users.get(i).getHighestScore() + "\n";
        }

        return result;
    }
}
