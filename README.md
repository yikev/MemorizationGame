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

## Instructions for Playing

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

## Demonstration

### Full Demo including saving

Game initialization

![Game initialization](https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExNnhmaHQ2NnU1MGpsajAxaGc4bmdqMWoxYWloZmVrdmdnd2lia2YxNCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/s5wNceSb0tjwNoSTnE/giphy.gif)

Gameplay

![Gameplay](https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExd2d6MmEweDZwendqZHRpbDRycWp0ZzE5cm12bXBwanZwZzQ2MHEzNiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/hzmoUtoD8MpWxF3h35/giphy.gif)

Save, quit, and loading save

![Save, quit, and loading save](https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExZXYydHllNzhzdnR0ZDNvYzR0bmx3bTZrZjI0bXF4dHQ2YXhyaGQ2dyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/CrUxygT17V3Ma3yu0E/giphy.gif)

Changing Users

![](https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExc2Jta3FkNnQ0aHc4NGo5cWY0czBjdTlqMGFmaHFscTd5OTkyNmQ2aiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/XoaXrhrynLktLMtL05/giphy.gif)

Gameplay continued

![](https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExdG52Zm80bmMxMzNzNDJ6bnltdnJoN3cyeGFpc2pkaGVsdHRwZzc5NyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/QhwWTLQEBiK8KeFRPK/giphy.gif)

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

Possible Improvements:

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
