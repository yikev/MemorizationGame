package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class UserDatabase implements Writable {

    private ArrayList<UserAccount> users;

    /*
     * EFFECTS: Creates a UserDatabase object and creates the
     *          ArrayList of UserAccounts.
     */
    public UserDatabase() {
        users = new ArrayList<>();
    }

    public int getSize() {
        return users.size();
    }

    public ArrayList<UserAccount> getList() {
        return users;
    }

    /*
     * EFFECTS: Searches for a given user in the database if they exist.
     *          Returns a String of  the user's stats (highest score,
     *          average guesses, and games played) if they exist.
     */
    public String getStats(String user) {
        for (int i = 0; i < getSize(); i++) {
            if (user.toUpperCase().equals(users.get(i).getName())) {
                return users.get(i).getUserStats();
            }
        }

        return "User does not exist.";
    }

    /*
     * REQUIRES: Cannot add a user that has a name that already
     *           exists in the UserDataBase.
     * MODIFIES: this
     * EFFECTS:  Adds a new UserAccount to the UserDataBase.
     */
    public void addUser(UserAccount user) {
        users.add(user);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("users", usersToJson());
        return json;
    }

    /*
     * EFFECTS:  UserAccounts in current UserDatabase stored in jsonArray.
     */
    private JSONArray usersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (UserAccount u : users) {
            jsonArray.put(u.toJson());
        }

        return jsonArray;
    }
}
