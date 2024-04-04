public class Cell {
    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int adjacentMines;

    // Constructor to initialize the cell properties
    public Cell() {
        isMine = false;
        isRevealed = false;
        isFlagged = false;
        adjacentMines = 0;
    }

    // getter method for isMine()
    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine; // Sets the cell's mine status (true if it contains a mine).
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void reveal() {
        isRevealed = true;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void toggleFlag() {
        isFlagged = !isFlagged;
    }

    // getter method
    public int getAdjacentMines() {
        return adjacentMines; // returns the number of adjacent mines.
    }

    // setter method
    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines; // Sets the number of adjacent mines.
    }
}

