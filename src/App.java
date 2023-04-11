import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static Player player1;
    static Player player2;
    static boolean player1Turn = true;
    static boolean player2Turn = false;
    static boolean firstPlayerGameOver;
    static boolean secondPlayerGameOver;


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

        firstPlayerGameOver = player1.isGameOver();
        secondPlayerGameOver = player2.isGameOver();

        while (!firstPlayerGameOver && !secondPlayerGameOver) {

            if (player1Turn && !player2Turn) {
                scanner.nextLine();
                printGrids(player1, player2);
                System.out.println("Player 1, it's your turn:");
                attacked(player2);
            } else {
                scanner.nextLine();
                printGrids(player2, player1);
                System.out.println("Player 2, it's your turn:");
                attacked(player1);
            }

            firstPlayerGameOver = player1.isGameOver();
            secondPlayerGameOver = player2.isGameOver();
            if (firstPlayerGameOver || secondPlayerGameOver) {
                break;
            }
            changePlayer();
        }

        System.out.println("You sank the last ship. You won. Congratulations!");

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