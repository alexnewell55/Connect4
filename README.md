# Connect4
Creating a connect 4 game using Java, with a Swing GUI. Using different algorithms to program the AI to play the game.

For my dissertation project in my MSc Computer Science at Newcastle University I decided to create the popular family game Connect 4 using Java.
The main goal of the project was to create a game that would fit in on websites containing minigames aimed for children.
The secondary goal was to analyse the success of differen algorithms playing the game.

The Main class is used to run the game as normal, using the Menu class, the Game class and the respective Player classes.
The Player interface is implemented by six different classes, using different algorithms to decide what turns to take in the game.
The AIScoring class is the method that is used by the algorithms to value the baord state at the necessary time.
The AITesting class can be used to run several games of Connect 4 using two AI players and returning an analysis of the results.
