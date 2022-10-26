package ui;

import model.GameBoard;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MemoryGame {
    private static final String JSON_STORE = "./data/workroom.json";
    private static int BASE_CARDS = 8;
    private GameBoard memoryGame;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Method was taken from WorkRoomApp class in:
    // https://github.com/stleary/JSON-java
    public MemoryGame() throws FileNotFoundException {
        runGame();
    }

    public void runGame() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        System.out.println("Enter your name:");
        String name = input.next().toUpperCase();
        memoryGame = new GameBoard(name,BASE_CARDS);

        while (true) {
            if (runMenu()) {
                break;
            }
        }
    }

    /*
     * EFFECTS: Introduces the game to the user.
     */
    public String introduction() {
        return "Memory Game!\n Match all the pairs to win. " + "Base game starts with 8 cards."
                + " Do you want to add more cards?\n"
                + "Enter the number of pairs to add (0 if none)\n";
    }

    /*
     * EFFECTS: Lets the player choose an option to start a new game, check stats,
     *          check other user stats, see the leaderboards, or exit the game.
     */
    public String displayMenu() {
        return "Type 'p' to play\n 's' to check your stats\n 'l' to check leaderboards\n"
                + "'f' to find a user and check their stats\n"
                + "'c' to change players\n 'save' to save game \n"
                + "'load' to load userbase \n 'q' to quit\n";
    }

    //switch cases making many lines
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public boolean runMenu() {
        System.out.println(displayMenu());
        String userInput = input.next();

        switch (userInput) {
            case "p":
                playGame();
                break;
            case "s":
                System.out.println(memoryGame.getUser().getUserStats());
                break;
            case "l":
                System.out.println(memoryGame.getLeaderboards());
                break;
            case "f":
                System.out.println("Enter the user to search:\n");
                userInput = input.next().toUpperCase();
                System.out.println(memoryGame.getUserBase().getStats(userInput));  // might to modify
                break;
            case "c":
                System.out.println("Enter your name:");
                if (!memoryGame.changeUser(input.next().toUpperCase())) {
                    System.out.println("That user already exists.");
                }
                break;

            case "save":
                saveUserDataBase();
                break;
            case "load":
                loadUserDataBase();
                memoryGame.changeUser(memoryGame.getUser().getName());
                break;
            case "q":
                return true;
        }
        return false;
    }

    public void playGame() {
        String userInput;
        String guess1;
        String guess2;

        System.out.println(introduction());
        userInput = input.next();

        memoryGame.startGame(Integer.parseInt(userInput));

        while (!memoryGame.isGameOver()) {
            guess1 = getGuess().toLowerCase();
            if (guess1.equals("q")) {
                break;
            }
            memoryGame.revealCard(guess1, 1);
            guess2 = getGuess();
            if (guess2.equals("q")) {
                break;
            }
            memoryGame.revealCard(guess2, 2);
            System.out.println(memoryGame.displayCards(memoryGame.getHiddenCards()));

            waitAndHideCards();

            memoryGame.checkGuesses(guess1, guess2);
        }

        System.out.println("**********************************");
        System.out.println("You found all the the pairs!");
    }

    private String getGuess() {
        System.out.println(memoryGame.displayCards(memoryGame.getHiddenCards()));
        System.out.println("Enter a guess row column such as 1a OR 'q' to quit:");
        return input.next();
    }

    /*
     * EFFECTS:  Saves current UserDatabase to json.
     */
    private void saveUserDataBase() {
        try {
            jsonWriter.open();
            jsonWriter.write(memoryGame.getUserBase());
            jsonWriter.close();
            System.out.println("Saved " + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Loads a previously saved UserDatabase.
     */
    private void loadUserDataBase() {
        try {
            memoryGame.setUserBase(jsonReader.read());
            System.out.println("Loaded " + " save from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    /*
     * Can use sleep thread in the future
     */
    public void waitAndHideCards() {
        int timer = 0;
        for (int i = 0; i < 999999999; i++) {
            timer *= i;
        }

        for (int i = 0; i < 999999999; i++) {
            timer *= i;
        }

        for (int i = 0; i < 8; i++) {
            System.out.println("###################################");
        }

        System.out.println("Looking up is cheating!");
    }



}
