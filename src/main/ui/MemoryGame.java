package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Scanner;

/*
 * MemoryGame GUI. Displays MemoryGame to the user with all its functionalities.
 */
public class MemoryGame extends JFrame {
    private static final String JSON_STORE = "./data/userdatabase.json";
    private static final String JSON_STORE_SAVE = "./data/savestate.json";
    private static int BASE_CARDS = 8;
    private static int MINIMUM_CARD_XCOORDINATE = 200;
    private static int MINIMUM_CARD_YCOORDINATE = 200;
    private static int MAXIMUM_CARD_XCOORDINATE = 800;
    private static int MAXIMUM_CARD_YCOORDINATE = 530;
    private static int CARD_WIDTH = 75;
    private static int CARD_HEIGHT = 110;
    private GameBoard memoryGame;
    //private Scanner input;
    private JsonWriter jsonWriterUserBase;
    private JsonWriter jsonWriterSaveState;
    private JsonReader jsonReaderUserBase;
    private JsonReader jsonReaderSaveState;
    int xcoord = MINIMUM_CARD_XCOORDINATE;
    int ycoord = MINIMUM_CARD_YCOORDINATE;
    int guessNumber = 0;
    int cardNumberOne;
    JButton playButton;
    JButton saveButton;
    JButton searchPlayerButton;
    JButton quitButton;
    JButton menuButton;
    JButton addPairButton;
    JButton removePairButton;
    JButton startButton;
    JButton changeUser;
    JFrame gameFrame;
    JPanel p1;
    JLabel leaderboards;
    JLabel playerStats;
    JLabel gameOver;
    JLabel scoreBoard;
    JLabel searchPlayerStats;
    JLabel gameEndImage;
    String userInput;
    ArrayList<GameCard> gameCards;
    Timer delayTimer;
    int count = 0;
    int delay = 1000;
    int choice;

    // Method was taken from WorkRoomApp class in:
    // https://github.com/stleary/JSON-java
    public MemoryGame() throws FileNotFoundException {
        jsonWriterUserBase = new JsonWriter(JSON_STORE);
        jsonWriterSaveState = new JsonWriter(JSON_STORE_SAVE);
        jsonReaderUserBase = new JsonReader(JSON_STORE);
        jsonReaderSaveState = new JsonReader(JSON_STORE_SAVE);
        choice = getUserChoice();
        initializeMainMenu();
        initializeMenuButtons();
        initializeMenuButtonActions();
        initializeLeaderboards();
        initializePlayerStats();
        initializeSearchPlayerStats();
        initializeGameOver();
        initializeScoreBoard();
        gameButtons();
        initializeGameButtonActions();
        initializeGame(choice);
        whenUserExitsButton();
        startButton.setVisible(false);
        removePairButton.setVisible(false);
    }

    public void getUserName() {
        userInput = JOptionPane.showInputDialog("Enter your name");
        memoryGame.changeUser(userInput);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Gets the users choice of loading from save or not.
     *           Checks if there is a save state. If there is ask the user if they
     *           want to load from save or not.
     */
    public int getUserChoice() {
        try {
            if (jsonReaderSaveState.readGameCards() == null) {
                return 1;
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

        return startUpDialog();
    }

    /*
     * EFFECTS:  Displays an option to the user on startup of when the program runs.
     *           The user decides whether to load from save or not. Returns 0 if
     *           user decides to load from save. Returns 1 if user declines.
     */
    public int startUpDialog() {
        Object[] options = {"Yes",
                "No"};
        choice = JOptionPane.showOptionDialog(gameFrame,
                "Would you like to load from your last save?",
                "Startup",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        return choice;
    }

    /*
     * MODIFIES: GameBoard, this.
     * EFFECTS:  If the user chose to load from save. The saved leaderboards
     *           gets loaded. The gameboard is created to replicate the previous
     *           save state.
     *
     *           If not, run the game as normal, asking the user for their name.
     */
    public void initializeGame(int choice) {
        if (choice == 0) {
            memoryGame = new GameBoard("null",BASE_CARDS);
            loadUserDataBase();
            memoryGame.changeUser(memoryGame.getUser().getName());
            leaderboards.setText(memoryGame.getLeaderboards());
            memoryGame.loadLastUser();
        } else {
            userInput = JOptionPane.showInputDialog("Enter your name");
            memoryGame = new GameBoard(userInput,BASE_CARDS);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Creates the JFrame and JPanel. Makes them visible to the user.
     */
    public void initializeMainMenu() {
        gameFrame = new JFrame("Memory Game");
        p1 = new JPanel();
        p1.setLayout(null);
        gameFrame.getContentPane().add(p1);
        gameFrame.setSize(1000,1000);
        p1.setVisible(true);
        gameFrame.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Creates the menu buttons: play, save, change user, search player
     *           and quit buttons.
     */
    public void initializeMenuButtons() {
        playButton = new JButton("Play");
        saveButton = new JButton("Save");
        changeUser = new JButton("Change User");
        searchPlayerButton = new JButton("Search Player");
        quitButton = new JButton("Quit");

        playButton.setBounds(15,110,100,50);
        saveButton.setBounds(15,160,100,50);
        searchPlayerButton.setBounds(15,260,100,50);
        quitButton.setBounds(15,310,100,50);
        changeUser.setBounds(15,360,100,50);
        p1.add(playButton);
        p1.add(saveButton);
        p1.add(searchPlayerButton);
        p1.add(quitButton);
        p1.add(changeUser);
        saveButton.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Creates the game buttons: menu, add pair, remove pair,
     *           and start buttons. Creates the game cards.
     */
    public void gameButtons() {
        menuButton = new JButton("Menu");
        addPairButton = new JButton("Add Pair");
        removePairButton = new JButton("Remove Pair");
        startButton = new JButton("Start Game");
        gameCards = new ArrayList<>();
        initializeGameCards();

        startButton.setBounds(15,60,100,50);
        menuButton.setBounds(15,110,100,50);
        addPairButton.setBounds(15,160,100,50);
        removePairButton.setBounds(15,210,100,50);

        p1.add(menuButton);
        p1.add(addPairButton);
        p1.add(removePairButton);
        p1.add(startButton);
        menuButton.setVisible(false);
        addPairButton.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  This method keeps the chosen cards "value" displayed for
     *           'countPassed' amount of times. If the guesses were correct
     *           update the score and check if the game is over.
     */
    // Method was taken/inspired from:
    //https://www.youtube.com/watch?v=XHd2s9hV8Tc&ab_channel=plzHelpM3
    public void startTimer(int countPassed, GameCard firstCard, GameCard secondCard, int one, int two) {
        ActionListener action = e -> {
            scoreBoard.setVisible(true);
            if (count == 0) {
                delayTimer.stop();
                if (memoryGame.checkGuesses(one,two)) {
                    updateScoreAndCards(firstCard,secondCard);
                    if (memoryGame.isGameOver()) {
                        memoryGame.updateUser();
                        celebrateWin();
                    }
                } else {
                    scoreBoard.setText("Score" + memoryGame.getScore());
                    turnBackChosenCards(firstCard,secondCard);
                }
            } else {
                count--;
            }
        };
        delayTimer = new Timer(delay,action);
        delayTimer.start();
        count = countPassed;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Display the game over celebration. Displays the fireworks and
     *           gameOver label.
     */
    public void celebrateWin() {
        gameOver.setText("You won! With " + memoryGame.getTotalGuesses() + " guesses.");
        gameEndImage.setVisible(true);
        gameOver.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Updates the scoreboard, and sets the correct GameCards to found.
     */
    public void updateScoreAndCards(GameCard firstCard, GameCard secondCard) {
        scoreBoard.setText("Score" + memoryGame.getScore());
        showChosenCards(firstCard,secondCard);
        firstCard.setIsFound(true);
        secondCard.setIsFound(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Leave correct cards displayed with their respective 'values'.
     */
    public void showChosenCards(GameCard firstCard, GameCard secondCard) {
        for (GameCard gc : gameCards) {
            if (!gc.getIsFound()) {
                gc.setEnabled(true);
            }
        }
        firstCard.setEnabled(false);
        secondCard.setEnabled(false);
        firstCard.setText("" + memoryGame.getGameCards()[gameCards.indexOf(firstCard)]);
        secondCard.setText("" + memoryGame.getGameCards()[gameCards.indexOf(secondCard)]);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Hides the value of the chosen cards, 'flipping' them over.
     */
    public void turnBackChosenCards(JButton firstCard, JButton secondCard) {
        for (GameCard gc : gameCards) {
            if (!gc.getIsFound()) {
                gc.setEnabled(true);
            }
        }
        firstCard.setText("");
        secondCard.setText("");
    }

//    public void initializeGameCards() {
//        for (int i = 0; i < 8; i++) {
//            this.xcoord += cardWidth;
//            gameCards.add(new JButton());
//            gameCards.get(i).setBounds(this.xcoord,ycoord,cardWidth,cardHeight);
//            p1.add(gameCards.get(i));
//            gameCards.get(i).setVisible(false);
//            addActionToGameCard(gameCards.get(i));
//        }
//    }

    /*
     * MODIFIES: this
     * EFFECTS:  Creates the game cards and displays them on the GameFrame.
     *           Adds actions to the game cards.
     */
    public void initializeGameCards() {
        for (int i = 0; i < 8; i++) {
            this.xcoord += CARD_WIDTH;
            gameCards.add(new GameCard(xcoord,ycoord,CARD_WIDTH,CARD_HEIGHT));
            gameCards.get(i).setBounds(this.xcoord,ycoord,CARD_WIDTH,CARD_HEIGHT);
            p1.add(gameCards.get(i));
            gameCards.get(i).setVisible(false);
            addActionToGameCard(gameCards.get(i));
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Adds mouse click listener to the game cards. When clicked,
     *           display the 'value' to the user. If it is the second card
     *           clicked disable all cards and the start the timer.
     */
    public void addActionToGameCard(GameCard gameCard) {
        gameCard.addActionListener(e -> {
            gameCard.setEnabled(false);
            gameCard.setText("" + memoryGame.getGameCards()[gameCards.indexOf(e.getSource())]);
            guessNumber++;
            if (guessNumber == 2) {
                for (GameCard gc : gameCards) {
                    gc.setEnabled(false);
                }
                startTimer(1, gameCard, gameCards.get(cardNumberOne),
                        cardNumberOne,gameCards.indexOf(e.getSource()));
                guessNumber = 0;
            } else {
                cardNumberOne = gameCards.indexOf(e.getSource());
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Adds an action event to when the "X" button is clicked.
     *           If the "X" button is clicked during a live game, prompt user
     *           if they wish to save or not. If the "X" button is clicked any other
     *           time, simply close the program.
     */
    public void whenUserExitsButton() {
        gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowE) {
                if (saveButton.isVisible()) {
                    if (JOptionPane.showConfirmDialog(gameFrame,
                            "Do you want to save?", "Exit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        saveUserDataBase();
                        memoryGame.printLog(EventLog.getInstance());
                        System.exit(0);
                    }
                } else {
                    memoryGame.printLog(EventLog.getInstance());
                    System.exit(0);
                }
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Adds action listeners to menu buttons.
     */
    private void initializeMenuButtonActions() {
        playButton.addActionListener(e -> {
            hideMenu();
            menuButton.setVisible(true);
            revealCards();
        });

        searchPlayerButton.addActionListener(e -> searchPlayer());

        changeUser.addActionListener(e -> {
            getUserName();
            startButton.setVisible(false);
        });

        quitButton.addActionListener(e -> {
            memoryGame.printLog(EventLog.getInstance());
            System.exit(0);
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Prompts the user to enter a name for a user to search for.
     */
    public void searchPlayer() {
        startButton.setVisible(false);
        userInput = JOptionPane.showInputDialog("Enter a name to search:");
        searchPlayerStats.setText(memoryGame.getUserBase().getStats(userInput));
        searchPlayerStats.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Displays the game cards to the user. Game cards are disabled.
     */
    public void revealCards() {
        for (JButton button : gameCards) {
            button.setVisible(true);
            button.setEnabled(false);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Displays the game cards to the user. Game cards are enabled.
     */
    public void revealCardsAndEnable() {
        for (JButton button : gameCards) {
            button.setVisible(true);
            button.setEnabled(true);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Hides the game cards.
     */
    public void hideGameCards() {
        for (JButton button : gameCards) {
            button.setVisible(false);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Resets the game. Updates player stats and leaderboards.
     */
    public void resetGame() {
        memoryGame.resetGame();
        for (GameCard gc : gameCards) {
            gc.setEnabled(true);
            gc.setIsFound(false);
            gc.setText("");
        }
        leaderboards.setText(memoryGame.getLeaderboards());
        playerStats.setText(memoryGame.getUser().getUserStats());
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Adds action listeners to game buttons.
     */
    public void initializeGameButtonActions() {
        menuButton.addActionListener(e -> menuAction());

        addPairButton.addActionListener(e -> {
            addPair();
            gameFrame.repaint();
        });

        removePairButton.addActionListener(e -> {
            removePair();
            gameFrame.repaint();
        });

        startButton.addActionListener(e -> startAction());

        saveButton.addActionListener(e -> saveUserDataBase());
    }

    /*
     * MODIFIES: this
     * EFFECTS:  When the menu button is clicked: shows the menu, hides the
     *           game cards, hides the game end labels and updates the user.
     *           resets the game.
     */
    public void menuAction() {
        showMenu();
        hideGameCards();
        gameOver.setVisible(false);
        gameEndImage.setVisible(false);
        scoreBoard.setVisible(false);
        resetGame();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Adds a new pair of game cards to the game.
     */
    public void addPair() {
        if (!(xcoord == MAXIMUM_CARD_XCOORDINATE && ycoord == MAXIMUM_CARD_YCOORDINATE)) {
            for (int i = 0; i < 2; i++) {
                xcoord += CARD_WIDTH;

                if (xcoord > MAXIMUM_CARD_XCOORDINATE) {
                    ycoord += CARD_HEIGHT;
                    xcoord = MINIMUM_CARD_XCOORDINATE + CARD_WIDTH;
                }
                gameCards.add(new GameCard(xcoord,ycoord,CARD_WIDTH,CARD_HEIGHT));
                gameCards.get(gameCards.size() - 1).setBounds(xcoord,ycoord,CARD_WIDTH,CARD_HEIGHT);
                addActionToGameCard(gameCards.get(gameCards.size() - 1));
                p1.add(gameCards.get(gameCards.size() - 1));
                gameCards.get(gameCards.size() - 1).setEnabled(false);
            }
            memoryGame.changeTotalCards(gameCards.size());
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Removes a pair of game cards from the game board.
     */
    public void removePair() {
        if (xcoord == MINIMUM_CARD_XCOORDINATE) {
            if (ycoord == MINIMUM_CARD_YCOORDINATE) {
                return;
            }
            xcoord = MAXIMUM_CARD_XCOORDINATE;
            ycoord -= CARD_HEIGHT;
        }
        p1.remove(gameCards.get(gameCards.size() - 1));
        gameCards.remove(gameCards.size() - 1);
        p1.remove(gameCards.get(gameCards.size() - 1));
        gameCards.remove(gameCards.size() - 1);
        xcoord -= 2 * CARD_WIDTH;
        memoryGame.changeTotalCards(gameCards.size());
    }

    /*
     * MODIFIES: this
     * EFFECTS:  When the start button is clicked: hide the start button,
     *           show the save button. Start the game.
     */
    public void startAction() {
        if (gameCards.size() != 0) {
            startButton.setVisible(false);
            saveButton.setVisible(true);
            startGame();
            gameFrame.repaint();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Hides the add/remove pair buttons. Enables the game cards.
     *           The game is started.
     */
    public void startGame() {
        addPairButton.setVisible(false);
        removePairButton.setVisible(false);
        for (GameCard gc : gameCards) {
            gc.setEnabled(true);
        }
        memoryGame.startGame(gameCards.size());
    }

//    public void initializeHiddenCards(int[] gameCards) {
//        int x = 200;
//        int y = 200;
//        for (int i : gameCards) {
//            System.out.println(x);
//            x += cardWidth;
//
//            if (x > 800) {
//                y += cardHeight;
//                x = 275;
//            }
//
//            hiddenCards.add(new JLabel("HI"));
//            hiddenCards.get(hiddenCards.size() - 1).setBounds(x,y,cardWidth,cardHeight);
//            p1.add(hiddenCards.get(hiddenCards.size() - 1));
//            hiddenCards.get(hiddenCards.size() - 1).setVisible(true);
//            p1.repaint();
//            p1.revalidate();
//            gameFrame.repaint();
//            gameFrame.revalidate();
//        }
//    }

    /*
     * MODIFIES: this
     * EFFECTS:  Creates the leaderboards label.
     */
    public void initializeLeaderboards() {
        leaderboards = new JLabel();
        leaderboards.setBounds(200,210,200,600);
        p1.add(leaderboards);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Creates the player stats label.
     */
    public void initializePlayerStats() {
        playerStats = new JLabel();
        playerStats.setBounds(200,0,300,300);
        p1.add(playerStats);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Creates the search player label.
     */
    public void initializeSearchPlayerStats() {
        searchPlayerStats = new JLabel();
        searchPlayerStats.setBounds(550,0,300,300);
        p1.add(searchPlayerStats);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Creates the game ending labels. Game end image(fireworks),
     *           game over label.
     */
    public void initializeGameOver() {
        gameOver = new JLabel("You won!");
        ImageIcon imageIcon = new ImageIcon("./data/fireworks.jpg");
        gameEndImage = new JLabel();
        gameEndImage.setIcon(imageIcon);
        gameEndImage.setBounds(200,200,800,800);
        gameOver.setBounds(300,0,300,100);
        p1.add(gameOver);
        p1.add(gameEndImage);
        gameOver.setVisible(false);
        gameEndImage.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Creates the scoreboard label.
     */
    public void initializeScoreBoard() {
        scoreBoard = new JLabel("SCORE");
        scoreBoard.setBounds(700,0,100,100);
        p1.add(scoreBoard);
        scoreBoard.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Display all the menu buttons and hides all other buttons.
     */
    public void showMenu() {
        playButton.setVisible(true);
        saveButton.setVisible(false);
        searchPlayerButton.setVisible(true);
        quitButton.setVisible(true);
        changeUser.setVisible(true);
        leaderboards.setVisible(true);
        playerStats.setVisible(true);
        searchPlayerStats.setVisible(true);
        addPairButton.setVisible(false);
        removePairButton.setVisible(false);
        startButton.setVisible(false);
        menuButton.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Hides all the menu buttons.
     */
    public void hideMenu() {
        playButton.setVisible(false);
        saveButton.setVisible(false);
        changeUser.setVisible(false);
        searchPlayerButton.setVisible(false);
        quitButton.setVisible(false);
        leaderboards.setVisible(false);
        searchPlayerStats.setVisible(false);
        playerStats.setVisible(false);
        addPairButton.setVisible(true);
        removePairButton.setVisible(true);
        startButton.setVisible(true);
        menuButton.setVisible(true);
    }


//    public void runGame() {
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//        input = new Scanner(System.in);
//        System.out.println("Enter your name:");
//        String name = input.next().toUpperCase();
//        memoryGame = new GameBoard(name,BASE_CARDS);
//
//        JButton button = new JButton();
//        button.setBounds(200,100,100,50);
//
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLayout(null);
//        this.setSize(500,500);
//        this.setVisible(true);
//        this.add(button);
//
//        while (true) {
//            if (runMenu()) {
//                break;
//            }
//        }
//    }

    /*
     * EFFECTS: Introduces the game to the user.
     */
//    public String introduction() {
//        return "Memory Game!\n Match all the pairs to win. " + "Base game starts with 8 cards."
//                + " Do you want to add more cards?\n"
//                + "Enter the number of pairs to add (0 if none)\n";
//    }

    /*
     * EFFECTS: Lets the player choose an option to start a new game, check stats,
     *          check other user stats, see the leaderboards, or exit the game.
     */
//    public String displayMenu() {
//        return "Type 'p' to play\n 's' to check your stats\n 'l' to check leaderboards\n"
//                + "'f' to find a user and check their stats\n"
//                + "'c' to change players\n 'save' to save user stats \n"
//                + "'load' to load userbase \n 'q' to quit\n";
//    }

    //switch cases making many lines
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
//    public boolean runMenu() {
//        System.out.println(displayMenu());
//        String userInput = input.next();
//
//        switch (userInput) {
//            case "p":
//                playGame();
//                break;
//            case "s":
//                System.out.println(memoryGame.getUser().getUserStats());
//                break;
//            case "l":
//                System.out.println(memoryGame.getLeaderboards());
//                break;
//            case "f":
//                System.out.println("Enter the user to search:\n");
//                userInput = input.next().toUpperCase();
//                System.out.println(memoryGame.getUserBase().getStats(userInput));  // might to modify
//                break;
//            case "c":
//                System.out.println("Enter your name:");
//                if (!memoryGame.changeUser(input.next().toUpperCase())) {
//                    System.out.println("That user already exists.");
//                }
//                break;
//
//            case "save":
//                saveUserDataBase();
//                break;
//            case "load":
//                loadUserDataBase();
//                memoryGame.changeUser(memoryGame.getUser().getName());
//                break;
//            case "q":
//                return true;
//        }
//        return false;
//    }

//    public void playGame() {
//        String userInput;
//        String guess1;
//        String guess2;
//
//        System.out.println(introduction());
//        userInput = input.next();
//
//        memoryGame.startGame(Integer.parseInt(userInput));
//
//        while (!memoryGame.isGameOver()) {
//            guess1 = getGuess().toLowerCase();
//            if (guess1.equals("q")) {
//                break;
//            }
//            memoryGame.revealCard(guess1, 1);
//            guess2 = getGuess();
//            if (guess2.equals("q")) {
//                break;
//            }
//            memoryGame.revealCard(guess2, 2);
//            System.out.println(memoryGame.displayCards(memoryGame.getHiddenCards()));
//
//            waitAndHideCards();
//
//            //memoryGame.checkGuesses(guess1, guess2);
//        }
//
//        System.out.println("**********************************");
//        System.out.println("You found all the the pairs!");
//    }

//    private String getGuess() {
//        System.out.println(memoryGame.displayCards(memoryGame.getHiddenCards()));
//        System.out.println("Enter a guess row column such as 1a OR 'q' to quit:");
//        return input.next();
//    }

    /*
     * EFFECTS:  Saves current UserDatabase to json.
     */
    private void saveUserDataBase() {
        try {
            memoryGame.saveGame();
            jsonWriterUserBase.open();
            jsonWriterSaveState.open();
            jsonWriterUserBase.write(memoryGame.getUserBase());
            jsonWriterSaveState.writeCards(memoryGame.getSave());
            jsonWriterUserBase.close();
            jsonWriterSaveState.closeSaveState();
            System.out.println("Saved " + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

//    private void saveGameCards() {
//        try {
//            memoryGame.saveGame();
//            jsonWriter.open();
//            jsonWriter.writeCards(memoryGame.getSave());
//            jsonWriter.close();
//            System.out.println("Saved " + " to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }

    /*
     * MODIFIES: this
     * EFFECTS:  Loads a previously saved UserDatabase.
     */
    private void loadUserDataBase() {
        try {
            loadSavedGame(jsonReaderSaveState.readGameCards());
            memoryGame.setSaveState(jsonReaderSaveState.readGameCards());
            memoryGame.setUserBase(jsonReaderUserBase.read());
            System.out.println("Loaded " + " save from " + JSON_STORE);
            revealCardsAndEnable();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Loads a previously saved SaveState.
     */
    public void loadSavedGame(SaveState s) {
        hideMenu();
        menuButton.setVisible(true);
        getSavedCards(s);

        startButton.setVisible(false);
        saveButton.setVisible(true);
        addPairButton.setVisible(false);
        removePairButton.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Retrieves the saved cards from save state.
     *           Replicates the save state cards by creating the
     *           required amount of GameCards with their respective 'values'.
     */
    public void getSavedCards(SaveState s) {
        int howManyCards = s.getGameCards().length;
        int index = 0;

        System.out.println(howManyCards);

        if (8 - howManyCards > 0) {
            for (int i = 0; i < (8 - howManyCards) / 2; i++) {
                removePair();
            }
        } else {
            for (int i = 0; i < (howManyCards - 8) / 2; i++) {
                addPair();
            }
        }

        for (GameCard gc : gameCards) {
            if (s.getHiddenCards()[index] == 0) {
                gc.setEnabled(true);
            } else {
                gc.setIsFound(true);
                gc.setText("" + s.getGameCards()[index]);
            }
            index++;
        }
    }

//    private void loadGameCards() {
//        try {
//            memoryGame.setSaveState(jsonReader.readGameCards());
//            System.out.println("Loaded " + " save from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }

    /*
     * Can use sleep thread in the future
     */
//    public void waitAndHideCards() {
//        int timer = 0;
//        for (int i = 0; i < 999999999; i++) {
//            timer *= i;
//        }
//
//        for (int i = 0; i < 999999999; i++) {
//            timer *= i;
//        }
//
//        for (int i = 0; i < 8; i++) {
//            System.out.println("###################################");
//        }
//
//        System.out.println("Looking up is cheating!");
//    }
}
