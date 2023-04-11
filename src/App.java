import java.util.Scanner;

// CURRENT STAGE:
//--------------------
// - Both players can now place ships on their own grids.
// - Next step is to make them attack each other and check win condition accordingly.

public class App {
    static Scanner scanner = new Scanner(System.in);
    static Player player1;
    static Player player2;

    public static void main(String[] args) throws Exception {

        // PLAYER 1 SET UP:
        System.out.println("PLAYER 1");
        player1 = new Player(scanner);
        player1.createShips(scanner);
        player1.printGameGrid();

        // PLAYER 2 SET UP:
        System.out.println("PLAYER 2");
        player2 = new Player(scanner);
        player2.createShips(scanner);
        player2.printGameGrid();


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