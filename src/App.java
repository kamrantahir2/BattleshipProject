import java.util.Scanner;

// CURRENT STAGE:
//--------------------
// - The Player class is complete but the game is still single player1. Add a new player1
//   and make them compete

public class App {
    static Scanner scanner = new Scanner(System.in);
    static Player player1;
    static Player player2;

    public static void main(String[] args) throws Exception {

        // PLAYER 1
        player1 = new Player(scanner);
        player1.createShips(scanner);
        player1.printGameGrid();

        System.out.println("The game starts!");

        player1.printGameGrid();

        boolean isGameOver = isGameOver();

        while (!isGameOver) {
            System.out.println("Take a shot!");
            String shotCoordinate = scanner.next();
            boolean validShotCoordinate = player1.takeShot(shotCoordinate, scanner, player1);
            if (!validShotCoordinate) {
                while (!validShotCoordinate) {
                    shotCoordinate = scanner.next();
                    validShotCoordinate = player1.takeShot(shotCoordinate, scanner, player1);
                }
            }
            isGameOver = isGameOver();
        }

        System.out.println("You sank the last ship. You won. Congratulations!");

        player1.printGameGrid();

    }

    // MAIN METHOD ENDS HERE  !!!
//    ====================================================================================

    public static boolean isGameOver(){
        boolean isGameOver = true;

        for (int i = 0; i < player1.gameGrid.length; i++) {
            boolean endLoop = false;
            for (int j = 0; j < player1.gameGrid[i].length; j++) {
                if (player1.gameGrid[i][j].equalsIgnoreCase("O")) {
                    isGameOver = false;
                    endLoop = true;
                    break;
                }
            }
            if (endLoop) {
                break;
            }
        }
        return isGameOver;
    }

}