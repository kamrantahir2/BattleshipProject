import java.util.Scanner;

public class App {
    static String[][] gameGrid = new String[11][11];
    static String[][] fogGrid = new String[11][11];

    public static void main(String[] args) throws Exception {
        int aircraftCarrierLength = 5;
        int battleshipLength = 4;
        int submarineLength = 3;
        int cruiserLength = 3;
        int destroyerLength = 2;
        Scanner scanner = new Scanner(System.in);

        printGameGrid(gameGrid);

        // Aircraft Carrier:
        promptUserToPlaceShips(aircraftCarrierLength, "Aircraft Carrier", scanner);

        // Battleship:
        promptUserToPlaceShips(battleshipLength, "Battleship", scanner);

        // Submarine:
        promptUserToPlaceShips(submarineLength, "Submarine", scanner);

        // Cruiser:
        promptUserToPlaceShips(cruiserLength, "Cruiser", scanner);

        // Destroyer:
        promptUserToPlaceShips(destroyerLength, "Destroyer", scanner);

        System.out.println("The game starts!");
        printGameGrid(fogGrid);

        System.out.println("Take a shot!");
        String shotCoordinate = scanner.next();
        boolean validShotCoordinate = takeShot(shotCoordinate);

        if (!validShotCoordinate) {
            while (!validShotCoordinate) {
                shotCoordinate = scanner.next();
                validShotCoordinate = takeShot(shotCoordinate);
            }
        }
        printGameGrid(gameGrid);
    }

    // MAIN METHOD ENDS HERE
//    ====================================================================================

    public static void promptUserToPlaceShips(int shipLength, String shipName, Scanner scanner) throws Exception {
        System.out.println("Enter the coordinates of the " + shipName  + "(" + shipLength + "cells):");
        String firstCoordinate = scanner.next();
        String secondCoordinate = scanner.next();
        boolean isPlaced = placeShip(firstCoordinate, secondCoordinate, shipLength, shipName);

        while (!isPlaced) {
            System.out.println("Enter the coordinates of the " + shipName + "(" + shipLength + "cells):");
            firstCoordinate = scanner.next();
            secondCoordinate = scanner.next();
            isPlaced = placeShip(firstCoordinate, secondCoordinate, shipLength, shipName);
        }
    }

    public static boolean takeShot(String str) {
        int[] coordinate = Utility.coordinateToArray(str);
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

            if (gameGrid[coordinate[0]][coordinate[1]].equalsIgnoreCase("O")) {
                gameGrid[coordinate[0]][coordinate[1]] = "X";
                fogGrid[coordinate[0]][coordinate[1]] = "X";
                printGameGrid(fogGrid);
                System.out.println("You hit a ship!");
                printGameGrid(fogGrid);
            }
            else if (gameGrid[coordinate[0]][coordinate[1]].equalsIgnoreCase("~"))
            {
                gameGrid[coordinate[0]][coordinate[1]] = "M";
                fogGrid[coordinate[0]][coordinate[1]] = "M";
                printGameGrid(fogGrid);
                System.out.println("You missed!");
                printGameGrid(fogGrid);

            }
        }
        return validCoordinates;
    }

    public static boolean placeShip(String firstCoordinate, String secondCoordinate, int shipLength, String shipName) throws Exception {

        boolean validPlacement = true;

        int[] firstArray = Utility.coordinateToArray(firstCoordinate);
        int[] secondArray = Utility.coordinateToArray(secondCoordinate);

        int[] smallestArr = new int[firstArray.length];
        int[] highestArr = new int[firstArray.length];


        if (firstArray[0] == secondArray[0]){
            // Check which number at index 0 is lower
            smallestArr = firstArray[1] > secondArray[1] ? secondArray : firstArray;
            highestArr = firstArray[1] > secondArray[1] ? firstArray : secondArray;
        } else {
            smallestArr = firstArray[0] > secondArray[0] ? secondArray : firstArray;
            highestArr = firstArray[0]  > secondArray[0] ? firstArray : secondArray;
        }

        // Horizontally Placed:
        if (firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
            if (Math.abs(smallestArr[1] - highestArr[1]) != shipLength -1) {
                validPlacement = false;
                System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
            }
            else if (!checkSurroundingCells(smallestArr, highestArr, shipLength)) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                validPlacement = false;
            }
            else {
                if (validPlacement) {
                    for (int i = 0; i < shipLength; i++) {
                        App.gameGrid[smallestArr[0]][smallestArr[1] + i] = "O";
                    }
                    printGameGrid(gameGrid);
                }
            }
        }

        // Vertically Placed:
        else if (firstCoordinate.charAt(1) == secondCoordinate.charAt(1)){
            if (Math.abs(smallestArr[0] - highestArr[0]) != shipLength -1){
                System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
                validPlacement = false;
            }
            else if (!checkSurroundingCells(smallestArr, highestArr, shipLength)) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                validPlacement = false;
            }
            else {
                if (validPlacement){
                    for (int i = 0; i < shipLength; i++) {
                        App.gameGrid[smallestArr[0] + i][highestArr[1]] = "O";
                    }
                    printGameGrid(gameGrid);
                }

            }
        }

        else {
            System.out.println("Error! Wrong ship location! Try again:");
            validPlacement = false;
        }

        return validPlacement;
    }


    public static boolean checkSurroundingCells(int[] smallestArr, int[] highestArr, int shipLength) {
        boolean isFree = true;

        // Horizontally Placed:
        if (smallestArr[0] == highestArr[0]) {
            int above = smallestArr[0] -1;
            int below = smallestArr[0] +1;
            int secondIndex = smallestArr[1];

            try {
                if (gameGrid[smallestArr[0]][smallestArr[1]-1] == "X" || gameGrid[smallestArr[0]][smallestArr[1]-1] == "O"){
                    isFree = false;
                }
                if (gameGrid[highestArr[0]][highestArr[1]+1] == "X" || gameGrid[highestArr[0]][highestArr[1]+1] == "O") {
                    isFree = false;
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {

            }

            for (int i = 1; i <= shipLength; i++) {
                try {
                    if (gameGrid[above][secondIndex] == "X" || App.gameGrid[above][smallestArr[1]] == "O") {
                        isFree = false;
                        break;
                    } else if (gameGrid[below][secondIndex] == "X" || gameGrid[below][smallestArr[1]] == "O") {
                        isFree = false;
                        break;
                    }
                    secondIndex ++;
                }
                catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
        // Vertically Placed:
        else if (smallestArr[1] == highestArr[1]) {
            int left = smallestArr[1] -1;
            int right = smallestArr[1] +1;
            int firstIndex = smallestArr[0];

            if (shipLength > 2) {
                try {
                    if (gameGrid[smallestArr[0]-1][smallestArr[1]] == "X" || gameGrid[smallestArr[0]-1][smallestArr[1]] == "O") {
                        isFree = false;
                    }
                    if (gameGrid[highestArr[0]+1][highestArr[1]] == "X" || gameGrid[highestArr[0]+1][highestArr[1]] == "O") {
                        isFree = false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
                for (int i = 1; i <= shipLength; i++) {
                    try {
                        if (gameGrid[firstIndex][left] == "X" || gameGrid[smallestArr[0]][left]
                                == "O"){
                            isFree = false;
                            break;
                        } else if (gameGrid[firstIndex][right] == "X" || gameGrid[smallestArr[0]][right] == "O") {
                            isFree = false;
                            break;
                        }
                        firstIndex ++;
                    }
                    catch (ArrayIndexOutOfBoundsException e) {

                    }
                }

            }
            else {
                // THIS IS WHERE THE PROBLEM IS !!!!!!
                try {
                    if (gameGrid[smallestArr[0]-1][smallestArr[1]].equalsIgnoreCase("X") || gameGrid[smallestArr[0]-1][smallestArr[1]].equalsIgnoreCase("O")) {
                        isFree = false;
                    }
                    if (gameGrid[highestArr[0]+1][highestArr[1]].equalsIgnoreCase("X") || gameGrid[highestArr[0]+1][highestArr[1]].equalsIgnoreCase("O")) {
                        isFree = false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }


        return isFree;
    }


//    =====================================================================================
    // Print Methods

    public static void printGameGrid(String[][] gameGrid) {
        gameGrid[0][0] = " ";
        // Numbers row:
        for(int i = 1; i < gameGrid[0].length; i++) {
            gameGrid[0][i] = String.valueOf(i);
        }

        // startingChar is used as the ASCII code for A and we increment it in the loop
        int startingChar = 65;
        for (int i = 1; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[i].length; j++) {
                if (j == 0) {
                    gameGrid[i][j] = String.valueOf((char) startingChar);
                    startingChar ++;
                    continue;
                }
                if (gameGrid[i][j] == null) {
                    gameGrid[i][j] = "~";
                }
            }
        }

        for (int i = 0; i < gameGrid.length; i++) {
            String row = "";
            for (int j = 0; j < gameGrid.length; j++){
                row += gameGrid[i][j];
                row += " ";
            }
            System.out.println(row);
        }
    }

}