# Memory Game

## Proposal

This memory game will be a simple card memorization game.
The user will be given a set number of cards in which they will
be hidden until the user clicks a card. When a card is 
clicked it will reveal a picture for a brief moment. 
The user must find and match pairs of the same picture. 
The game is over when all pairs of pictures are revealed.

The game will contain:

* Database of players
* Database of player high scores
* Game difficulty (adding more cards)

I'm choosing to make this memory game because I remember playing 
it when I was a kid and it was fun. Also, it seems like it could be challenging 
to make the user interface. 

## User Stories

* As a user, I would like to be able to add more pairs of cards to the game board.
* As a user, I want to see a list of high scores.
* As a user, I want to see my *stats*, how many games I've played. How many guesses on average per game etc.
* As a user, I would like to see other user's stats.
* As a user, I would like the option to quit the current game and go back to the main menu.
* As a user, I would like the option to save all stats of all players.
* As a user, I want to be able to re-open the application and have the same leaderboards/stats from my last session.

## Instructions for Grader

-You can generate the first required event related to adding Cards to the Gameboard by:

1. Run the program. 
2. Decline to load from save. Enter a name.
3. Click "Play" button.
4. Add pairs of cards with the "Add Pair" button.

-You can generate the second required event related to adding Cards to a Gameboard by:

1. Run the program.
2. Decline to load from save. Enter a name.
3. Click "Play" button.
4. Remove pairs of cards with the "Remove Pair" button.

Visual component:

1. Run the program.
2. Load game from save or decline and enter a name.
3. If loaded from save state, simply finish the game.
    1. If not click the "Play" button. Optionally, "Add Pair" or "Remove Pair" then click "Start" button.
    2. Finish the game.
4. Once the game is finished, there should be an image of fireworks.

Saving: 

1. Run the program.
2. Decline to load from save. Enter a name.
3. Click "Play" button. Optionally add/remove pairs of cards.
4. Click "Start" button.
5. Click "Save" button whenever during the game. Alternatively, 
   click the "x" button to exit the game. The game will prompt to save or not.

Loading:

1. Run the program.
2. An option will be given to load from save.

##Phase 4: Task 2

Event log sample:

Wed Nov 23 20:17:16 PST 2022
Removed a pair of cards.


Wed Nov 23 20:17:16 PST 2022
Removed a pair of cards.


Wed Nov 23 20:17:18 PST 2022
Removed a pair of cards.


Wed Nov 23 20:17:19 PST 2022
Added a pair of cards.


Wed Nov 23 20:17:20 PST 2022
Game started with 4 cards.


Wed Nov 23 20:17:25 PST 2022
Wrong guess for: Row:1 Column:2 and Row:1 Column:3


Wed Nov 23 20:17:28 PST 2022
Correct guess for: Row:1 Column:4 and Row:1 Column:3


Wed Nov 23 20:17:31 PST 2022
Correct guess for: Row:1 Column:2 and Row:1 Column:1


Wed Nov 23 20:17:31 PST 2022
Game Finished


Wed Nov 23 20:17:32 PST 2022
Game is reset.

##Phase 4: Task 3

If I had more time these would be the changes I would make to improve my design:

* Implement GameCard into GameBoard. In phase 3 I realized I needed to have GameCard objects to check if they were 
"pressed" and "correct guesses". In console (phase 2), I simply just needed two arrays for this.
* There is a lot of duplication amongst my methods in MemoryGame and GameBoard classes. Possibly refactor these
duplications into another class or better designed methods.
* My implementation of JSon was very messy. I created two JsonReaders/JsonWriters because I was pretty confused on how
to implement it. I also should probably implement my persistence in GameBoard.

## References

JsonSerializationDemo, https://github.com/stleary/JSON-java

TellerApp, https://github.students.cs.ubc.ca/CPSC210/TellerApp

fireworks.jpg, https://www.vectorstock.com/royalty-free-vector/firework-icon-vector-28921354

https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html

Swing Timer Tutorial, https://www.youtube.com/watch?v=XHd2s9hV8Tc&ab_channel=plzHelpM3