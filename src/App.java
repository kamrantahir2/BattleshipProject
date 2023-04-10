import java.util.Scanner;

// ISSUES :
//-------

// - Placing ships. After placing the first ship we are again asked
//   to place the first ship. The program never moves on to the second ship.

// - The gameGrid is not printed at the start but is printed after typing the coordinates
//   for the first ship

public class App {
    static Scanner scanner = new Scanner(System.in);
    static Player player = new Player(scanner);

    public static void main(String[] args) throws Exception {

        player.printGameGrid();

        System.out.println("The game starts!");
        player.printGameGrid();

        boolean isGameOver = isGameOver();

        while (!isGameOver) {
            System.out.println("Take a shot!");
            String shotCoordinate = scanner.next();
            boolean validShotCoordinate = takeShot(shotCoordinate);
            if (!validShotCoordinate) {
                while (!validShotCoordinate) {
                    shotCoordinate = scanner.next();
                    validShotCoordinate = takeShot(shotCoordinate);
                }
            }
            isGameOver = isGameOver();
        }

        System.out.println("You sank the last ship. You won. Congratulations!");

        player.printGameGrid();
    }

    // MAIN METHOD ENDS HERE  !!!
//    ====================================================================================

    public static boolean isGameOver(){
        boolean isGameOver = true;

        for (int i = 0; i < player.getGameGrid().length; i++) {
            boolean endLoop = false;
            for (int j = 0; j < player.getGameGrid()[i].length; j++) {
                if (player.getGameGrid()[i][j].equalsIgnoreCase("O")) {
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

    public static int[][] createCoordinatesArray(String firstCoordinate, String secondCoordinate, int shipLength) {
        int[] strToCoordinate = Utility.coordinateToArray(firstCoordinate, player, scanner);
        int[][] coordinateArr = new int[shipLength][2];

        if (firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
            for(int i = 0; i < coordinateArr.length; i++) {
                int[] coordinate = {strToCoordinate[0], strToCoordinate[1] + i};
                coordinateArr[i] = coordinate;
            }
        } else if (firstCoordinate.charAt(1) == secondCoordinate.charAt(1)) {
            for(int i = 0; i < coordinateArr.length; i++) {
                int[] coordinate = {strToCoordinate[0] + i, strToCoordinate[1]};
                coordinateArr[i] = coordinate;
            }
        }
        return coordinateArr;
    }

    // Method taking a coordinate and firing a shot
    public static boolean takeShot(String str) {
        int[] coordinate = Utility.coordinateToArray(str, player, scanner);
        boolean validCoordinates = true;

        if (coordinate[0] < 1 || coordinate[0] > 10){
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            validCoordinates = false;
        }
        else if (coordinate[1] < 1 || coordinate[1] > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            validCoordinates = false;
        }
        else {
            if (player.gameGrid[coordinate[0]][coordinate[1]].equalsIgnoreCase("O")) {
                player.gameGrid[coordinate[0]][coordinate[1]] = "X";
                player.fogGrid[coordinate[0]][coordinate[1]] = "X";
                player.printGameGrid();

                // YOU ARE HERE !!!!!!
                for (int i = 0; i < player.shipsArray.length; i++) {
                    if (player.shipsArray[i].wasHit(coordinate)) {
                        if (player.shipsArray[i].checkLives(player) == 0) {
                            System.out.println("You sank a ship! Specify a new target:");
                        } else {
                            System.out.println("You hit a ship! Try again:");
                        }
                    }
                }

            }
            else if (player.gameGrid[coordinate[0]][coordinate[1]].equalsIgnoreCase("~"))
            {
                player.gameGrid[coordinate[0]][coordinate[1]] = "M";
                player.fogGrid[coordinate[0]][coordinate[1]] = "M";
                player.printFogGrid();
                System.out.println("You missed. Try again:");

            }
        }
        return validCoordinates;
    }

//    =====================================================================================
    // Print Methods


}