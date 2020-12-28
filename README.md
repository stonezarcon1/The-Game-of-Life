# The-Game-of-Life


This is a program that allows you to simulate the game of life.

The rules are :

Any living cell with 0, or 1 neighbor; die due to underpopulation.
Any living cell with 2, or 3 neighbors lives because the conditions are perfect.
Any living cell with 4 or more neighbors will die to to overpopulation.
Any dead cell with exactly 3 neighbors comes back to life.

This program allows you to import your own text files, or generate a random board.

*FOR CREATING A SET BOARD*
 
 When creating a text file to upload to the program be sure to use the correct formatting.
 
 The rules for formatting are : 
 
 1. Your game board must be a rectangle or square, no missing holes.
 2. You may only choose 1, or 0 for each element. If you choose otherwise the program will not work.
 
 Try out this example! 
 000000
 000000
 001110
 011100
 000000
 000000
 
*FOR CREATING A RANDOM BOARD*

1. Rows larger than 20 or so will go out of bounds and you won't be able to see the result, you can have more columns, but they will also eventually go out of bounds.
2. You must enter an integer greater than 1, with no spaces or other characters.
