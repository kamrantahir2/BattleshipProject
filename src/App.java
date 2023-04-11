import java.util.Scanner;

// CURRENT STAGE:
//--------------------
// - Both players can now place ships on their own grids.
// - Next step is to make them attack each other and check win condition accordingly.

public class App {
    static Scanner scanner = new Scanner(System.in);
    static Player player1;
    static Player player2;
    static boolean player1Turn = true;
    static boolean player2Turn = false;

    public static void main(String[] args) throws Exception {

        // PLAYER 1 SET UP:
        System.out.println("Player 1, place your ships to the game field");
        player1 = new Player(scanner);
        player1.createShips(scanner);
        scanner.nextLine();

        // PLAYER 2 SET UP:
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        System.out.println("Player 2, place your ships to the game field");
        player2 = new Player(scanner);
        player2.createShips(scanner);

        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();

        boolean firstPlayerGameOver = player1.isGameOver();
        boolean secondPlayerGameOver = player2.isGameOver();

        while (!firstPlayerGameOver && !secondPlayerGameOver) {

            if (player1Turn && !player2Turn) {
                printGrids(player2, player1);
                System.out.println("Player 1, it's your turn:");
                attacked(player2);
                changePlayer();
            } else {
                printGrids(player1, player2);
                System.out.println("Player 2, it's your turn:");
                attacked(player1);
                changePlayer();
            }

            firstPlayerGameOver = player1.isGameOver();
            secondPlayerGameOver = player2.isGameOver();
        }

        if (firstPlayerGameOver) {
            System.out.println("Player 2 Won");
        } else if (secondPlayerGameOver) {
            System.out.println("Player 1 Won");
        }

        player1.printGameGrid();
    }

    public static void attacked(Player player) {
        String shotCoordinate = scanner.next();
        boolean validShotCoordinate = player.takeShot(shotCoordinate, scanner, player);
        if (!validShotCoordinate) {
            while (!validShotCoordinate) {
                shotCoordinate = scanner.next();
                validShotCoordinate = player.takeShot(shotCoordinate, scanner, player);
            }
        }
    }

    public static void changePlayer() {
        System.out.println("Press Enter and pass the move to another player");

        scanner.nextLine();
        if (player1Turn && !player2Turn) {
            player1Turn = false;
            player2Turn = true;
        } else {
            player1Turn = true;
            player2Turn = false;
        }

    }

    public static void printGrids(Player firstPlayer, Player secondPlayer) {
        secondPlayer.printFogGrid();
        System.out.println("---------------------");
        firstPlayer.printGameGrid();
    }


}