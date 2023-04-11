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

//        MAKE THE PLAYERS ATTACK EACH OTHER INSTEAD OF THEIR OWN GRIDS

        System.out.println("The game starts!");

        player1.printGameGrid();

        boolean isGameOver = player1.isGameOver(player1);

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
            isGameOver = player1.isGameOver(player1);
        }

        System.out.println("You sank the last ship. You won. Congratulations!");

        player1.printGameGrid();

    }

}