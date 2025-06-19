# Minesweeper

## Short Description

This is a **console-based version** of the classic **Minesweeper** game, implemented in **Java**.
The game generates a grid of hidden cells, some of which contain mines.
The player interacts with the board by marking suspected mines and revealing safe cells.

This project helps reinforce important **Java skills**, including:
- Two-dimensional arrays and data structures
- Object-oriented programming and encapsulation
- User input handling
- Game state management

It’s a fun way to build logical thinking and solidify understanding of **interactive program design** in Java.

---

## Game Flow

1. **Game Initialization**:
    - The player chooses how many mines to place.
    - The game initializes a grid and randomly places the mines.
2. **User Interaction**:
    - The player inputs coordinates and chooses an action:
        - `free`: reveal a cell
        - `mine`: mark/unmark a cell as a suspected mine
3. **Game Rules**:
    - The first move is always safe: no mine will be placed on the first chosen cell.
    - Revealing a mine ends the game.
    - Revealing an empty cell shows the number of adjacent mines.
    - Empty cells with no adjacent mines recursively reveal nearby cells.
4. **Winning the Game**:
    - The player wins by correctly marking all mines **or** revealing all non-mine cells.