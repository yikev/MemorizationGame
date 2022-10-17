package ui;

import model.GameBoard;
import java.util.Scanner;

public class MemoryGame {
    private static int BASE_CARDS = 8;
    private GameBoard memoryGame;
    private Scanner input;

    public MemoryGame() {
        runGame();
    }

    public void runGame() {
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
                + "'c' to change players\n 'q' to quit\n";
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
                memoryGame.changeUser(input.next().toUpperCase()); // might to modify
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
