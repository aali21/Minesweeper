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


    // method to get cell location (as was being repeated many times)
    public Cell getCell(int row, int col) {
        return cells[row][col];
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

            if (!getCell(row, col).isMine()) { // if there is no mine at the location
                getCell(row, col).setMine(true); // set (place) a mine at that location
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
        System.out.println("----Minesweeper Game----");

        // Print the x-axis labels
        System.out.print("    "); // Adjust for y-axis label space
        for (int i = 0; i < width; i++) {
            System.out.print(i + " "); // Print each x-axis label
        }
        System.out.println(); // New line after printing x-axis labels

        // Print a dashed line under the x-axis labels for separation
        System.out.print("   "); // Alignment for the starting point of the dashed line
        for (int i = 0; i < width; i++) {
            System.out.print("--"); // Dashed line under x-axis labels
        }
        System.out.println(); // New line after the dashed line

        for (int i = 0; i < height; i++) {
            // Print the y-axis label for the current row
            System.out.print(i + "   "); // Ensure this aligns with your grid's indentation
            for (int j = 0; j < width; j++) {
                Cell cell = cells[i][j]; // defining cell as cells[i][j] (with the method getCell
                // so we dont have to keep using it
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

    /* Method to reveal all mines on the board, this method is to be used in revealCell
    method when 1 mine is revealed (i.e game over) */
    private void revealAllMines() {
        // loop through each row
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) { // in each row loop through each of the columns
                Cell cell = getCell(r,c); // get the cell we're currently on
                if (cell.isMine()) { // check if its a mine and reveal if it is
                    cell.reveal();
                }
            }
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


        if (cell.isMine()) { // if the revealed cell is a mine
            revealAllMines(); //calling revealAllMines method defined above -> to reveal all the mines on the board
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
                Cell cell = getCell(i,j);

                if (!cell.isMine() && !cell.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }
}

