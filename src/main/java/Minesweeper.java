import java.util.Random;

public class Minesweeper {
    private static final int ROWS = 8; // Number of rows in the grid
    private static final int COLS = 8; // Number of columns in the grid
    private static final int NUM_MINES = 10; // Number of mines in the grid

    private static char[][] grid = new char[ROWS][COLS];

    // Initialize the grid with empty cells
    private static void initializeGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = '.';
            }
        }
    }

    // Display the grid in the console
    private static void displayGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        initializeGrid();
        displayGrid();
    }
}


