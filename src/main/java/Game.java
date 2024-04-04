import java.util.Scanner;

public class Game {
    private Board board; // the game board
    private boolean isGameOver; //indicates whether game is over
    private int width;
    private int height;
    private int mines;


    // constructor to initialize game with width, height and no. of mines
    public Game(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines= mines;
        this.board = new Board(width, height, mines); // initialize the game board
        this.isGameOver = false; // game starts with isGameOver set to false
    }

    // Method to start the game
    public void start() {
        Scanner scanner = new Scanner(System.in); // Scanner to get user input
        board.printBoard(); // Print the initial state of the board
        // Implement game loop logic here (e.g., process user input, update game state)
        // Game loop continues until isGameOver becomes true
        while (!isGameOver) {
            System.out.println("Enter your move (r for reveal, f for flag) and coordinates (row col): e.g., 'r 1 2'");
            String input = scanner.nextLine(); // Read user input
            String[] parts = input.split(" "); // Split input into parts
            // System.out.println(parts[0]);
            if (parts.length < 3) {
                System.out.println("Invalid input, try again.");
                continue; // Continue to next iteration of loop if input is invalid
            }


            String command = parts[0]; // first part is command
            int row, col;
            try {
                row = Integer.parseInt(parts[1]); // convert second part of input to row index
                col = Integer.parseInt(parts[2]); // third part to column index
            } catch (NumberFormatException e) {
                System.out.println("Invalid coordinates, try again.");
                continue; // continue to next iteration of loop
            }

            // Check if entered coordinates are within the bounds of the board
            if (row < 0 || row>= height || col<0 || col>=width) {
                System.out.println("Coordinates out of bounds, try again.");
                continue; // Continue to top of loop if coordinates are out of bounds
            }

            // Execute command based on user input
            if ("r".equalsIgnoreCase(command)) {
                // reveal cell and check if game over
                if (!board.revealCell(row,col)) {
                    System.out.println("Game Over! You hit a mine.");
                    isGameOver = true; // game over as mine revealed
                }
            } else if ("f".equalsIgnoreCase(command)) {
                Cell cell = board.getCell(row,col);
                if (cell.isRevealed()) {
                    System.out.println("Cannot flag a revealed cell. Try again.");
                    continue; //skip rest of the loops iteration
                }
                board.flagCell(row, col);
            } else {
                System.out.println("Invalid command, try again. (Can only use 'r' or 'f')");
                continue;
            }

            board.printBoard();
            if (board.checkWin()) {
                System.out.println("YOU WON!!");
                isGameOver = true; // game-over if all non-mine cells cleared
            }
        }
        scanner.close();
        }
    public static void main(String[] args) {
        Game game = new Game(8, 8, 7); // Example: 4x4 board with 3 mines
        game.start();
    }
}
