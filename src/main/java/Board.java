import java.util.Random;

public class Board {
    private Cell[][] cells; // 2D array
    private int width;
    private int height;
    private int mines;

    // Constructor for creating a new Board instance with specified dimensions and number of mines.
    public Board(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
        cells = new Cell[height][width]; // initializing the array with specified dimensions
        initializeBoard();
        placeMines(); // randomly place mines on the board
        calculateAdjacentMines();
    }

    private void initializeBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    // randomly place mines on the board
    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        // continue while loop until no. of minesPlaced is less than the mines required
        while (minesPlaced < mines) {

            // generate random row and col coordinates
            int row = random.nextInt(height);
            int col = random.nextInt(width);

            if (!cells[row][col].isMine()) { // if there is no mine at the location
                cells[row][col].setMine(true); // set (place) a mine at that location
                minesPlaced++;
            }
        }
    }

    // method for computing the number of mines adjacent to each mine
    private void calculateAdjacentMines() {
        // iterate over every row on the board
        for (int row = 0; row < height; row++) {
            // iterate over every column in the current row
            for (int col = 0; col < width; col++) {

                if (!cells[row][col].isMine()) { // if no mine
                    int count = 0;

                    // iterate over the 8 possible adjacent cells
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int newRow = row + i;
                            int newCol = col + j;
                            if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width
                                    && cells[newRow][newCol].isMine()) {
                                count++;
                            }
                        }
                    }
                    cells[row][col].setAdjacentMines(count);
                }
            }
        }
    }

    // Method to print the board (for debugging)
    public void printBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = cells[i][j]; // defining cell as cells[i][j] so we dont have to keep using it
                if (cell.isFlagged()) {
                    System.out.print("F "); // Flagged cell

                } else if (!cell.isRevealed()) {
                    System.out.print("? "); // Concealed cell
                } else if (cell.isMine()) {
                    System.out.print("M ");
                } else {
                    System.out.print(cell.getAdjacentMines() + " "); // no. of adjacent mines
                }
            }
            System.out.println();
        }
    }

    // Method to reveal cell
    public boolean revealCell(int row, int col) {

        // Checking bounds to ensure we dont go outside board
        // (so out-of-bounds exceptions dont take place)
        if (row <0 || row >= height || col<0 || col>= width) {
            return true;
        }

        Cell cell = cells[row][col];

        // If cell is already revealed or flagged, don't reveal it again to avoid infinite recursion
        if (cell.isRevealed() || cell.isFlagged()) {
            return true; // Not affecting the game over condition
        }

        cell.reveal(); // reveal this cell


        if (cell.isMine()) {
            return false; //Game over
        } else if (cell.getAdjacentMines() == 0) {
            // if no adjacent mines, recursively reveal all adjacent cells

            for (int i = -1; i<=1; i++) {
                for (int j = -1; j<=1;j++) {
                    // Prevent revealing the cell itself again
                    if (i==0 && j==0) continue;
                    int newRow = row+ i;
                    int newCol = col+ j;

                    // recursive call to reveal adjacent cells
                    revealCell(newRow,newCol);
                }
            }
        }

        return true;
    }

    // method for flagging cell
    public void flagCell(int row, int col) {
        cells[row][col].toggleFlag();
    }

    //method for checking if user has won
    public boolean checkWin() {
        // Check if all non-mine cells are revealed
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = cells[i][j];
                if (!cell.isMine() && !cell.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }
}

