import java.util.Scanner;

public class Player {
     String[][] gameGrid = createGrid();
     String[][] fogGrid = createGrid();
     private Ships aircraftCarrier;
     private Ships battleship;
     private Ships submarine;
     private Ships cruiser;
     private Ships destroyer;
     Ships[] shipsArray;

     public Player(Scanner scanner) {
         // CONSTRUCTOR CURRENTLY EMPTY WHILE METHOD ARE BEING ADDED
         printGameGrid();
     }

    // Method taking a coordinate and firing a shot
    public boolean takeShot(String str, Scanner scanner, Player player1) {
        int[] coordinate = Utility.coordinateToArray(str, player1, scanner);
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
            if (player1.gameGrid[coordinate[0]][coordinate[1]].equalsIgnoreCase("O")) {
                player1.gameGrid[coordinate[0]][coordinate[1]] = "X";
                player1.fogGrid[coordinate[0]][coordinate[1]] = "X";
                player1.printFogGrid();

                for (int i = 0; i < player1.shipsArray.length; i++) {
                    if (player1.shipsArray[i].wasHit(coordinate)) {
                        if (player1.shipsArray[i].checkLives(player1) == 0) {
                            System.out.println("You sank a ship! Specify a new target:");
                        } else {
                            System.out.println("You hit a ship! Try again:");
                        }
                    }
                }

            }
            else if (player1.gameGrid[coordinate[0]][coordinate[1]].equalsIgnoreCase("~"))
            {
                player1.gameGrid[coordinate[0]][coordinate[1]] = "M";
                player1.fogGrid[coordinate[0]][coordinate[1]] = "M";
                player1.printFogGrid();
                System.out.println("You missed. Try again:");

            }
        }
        return validCoordinates;
    }

     public void createShips(Scanner scanner){
         int aircraftCarrierLength = 5;
         int battleshipLength = 4;
         int submarineLength = 3;
         int cruiserLength = 3;
         int destroyerLength = 2;

         // Aircraft Carrier:
         this.aircraftCarrier = promptUserToPlaceShips(aircraftCarrierLength, "Aircraft Carrier", scanner);

         // Battleship:
         this.battleship =  promptUserToPlaceShips(battleshipLength, "Battleship", scanner);

         // Submarine:
         this.submarine = promptUserToPlaceShips(submarineLength, "Submarine", scanner);

         // Cruiser:
         this.cruiser = promptUserToPlaceShips(cruiserLength, "Cruiser", scanner);

         // Destroyer:
         this.destroyer = promptUserToPlaceShips(destroyerLength, "Destroyer", scanner);

         // shipsArray
         this.shipsArray = new Ships[] {aircraftCarrier, battleship, submarine, cruiser, destroyer};
     }

    public Ships promptUserToPlaceShips(int shipLength, String shipName, Scanner scanner){
        // We will call promptUserToPlaceShips() to create each type of ship needed

        System.out.println("Enter the coordinates of the " + shipName  + " (" + shipLength + " cells):");
        String firstCoordinate = scanner.next();
        String secondCoordinate = scanner.next();
        boolean isPlaced = placeShip(firstCoordinate, secondCoordinate, shipLength, shipName, scanner);

        while (!isPlaced) {
            System.out.println("Enter the coordinates of the " + shipName + " (" + shipLength + " cells):");
            firstCoordinate = scanner.next();
            secondCoordinate = scanner.next();
            isPlaced = placeShip(firstCoordinate, secondCoordinate, shipLength, shipName, scanner);
        }
        return new Ships(firstCoordinate, secondCoordinate, shipLength, this, scanner);
    }

    public boolean placeShip(String firstCoordinate, String secondCoordinate, int shipLength, String shipName, Scanner scanner) {

        boolean validPlacement = true;

        int[] firstArray = Utility.coordinateToArray(firstCoordinate, this,scanner);
        int[] secondArray = Utility.coordinateToArray(secondCoordinate, this, scanner);

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
                        this.gameGrid[smallestArr[0]][smallestArr[1] + i] = "O";
                    }
                    printGameGrid();
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
                        this.gameGrid[smallestArr[0] + i][highestArr[1]] = "O";
                    }
                    printGameGrid();
                }
            }
        }

        else {
            System.out.println("Error! Wrong ship location! Try again:");
            validPlacement = false;
        }

        return validPlacement;
    }

    public boolean checkSurroundingCells(int[] smallestArr, int[] highestArr, int shipLength) {
        boolean isFree = true;

        // Horizontally Placed:
        if (smallestArr[0] == highestArr[0]) {
            int above = smallestArr[0] -1;
            int below = smallestArr[0] +1;
            int secondIndex = smallestArr[1];

            try {
                if (this.gameGrid[smallestArr[0]][smallestArr[1]-1] == "X" || this.gameGrid[smallestArr[0]][smallestArr[1]-1] == "O"){
                    isFree = false;
                }
                if (this.gameGrid[highestArr[0]][highestArr[1]+1] == "X" || this.gameGrid[highestArr[0]][highestArr[1]+1] == "O") {
                    isFree = false;
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {

            }

            for (int i = 1; i <= shipLength; i++) {
                try {
                    if (this.gameGrid[above][secondIndex] == "X" || this.gameGrid[above][smallestArr[1]] == "O") {
                        isFree = false;
                        break;
                    } else if (this.gameGrid[below][secondIndex] == "X" || this.gameGrid[below][smallestArr[1]] == "O") {
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
                    if (this.gameGrid[smallestArr[0]-1][smallestArr[1]] == "X" || this.gameGrid[smallestArr[0]-1][smallestArr[1]] == "O") {
                        isFree = false;
                    }
                    if (this.gameGrid[highestArr[0]+1][highestArr[1]] == "X" || this.gameGrid[highestArr[0]+1][highestArr[1]] == "O") {
                        isFree = false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
                for (int i = 1; i <= shipLength; i++) {
                    try {
                        if (this.gameGrid[firstIndex][left] == "X" || this.gameGrid[smallestArr[0]][left]
                                == "O"){
                            isFree = false;
                            break;
                        } else if (this.gameGrid[firstIndex][right] == "X" || this.gameGrid[smallestArr[0]][right] == "O") {
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
                try {
                    if (this.gameGrid[smallestArr[0]-1][smallestArr[1]].equalsIgnoreCase("X") || this.gameGrid[smallestArr[0]-1][smallestArr[1]].equalsIgnoreCase("O")) {
                        isFree = false;
                    }
                    if (this.gameGrid[highestArr[0]+1][highestArr[1]].equalsIgnoreCase("X") || this.gameGrid[highestArr[0]+1][highestArr[1]].equalsIgnoreCase("O")) {
                        isFree = false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }

        return isFree;
    }

    public String[][] createGrid(){
         String[][] tempArr = new String[11][11];
        tempArr[0][0] = " ";
        // Numbers row:
        for(int i = 1; i < tempArr[0].length; i++) {
            tempArr[0][i] = String.valueOf(i);
        }

        // startingChar is used as the ASCII code for A and we increment it in the loop
        int startingChar = 65;
        for (int i = 1; i < tempArr.length; i++) {
            for (int j = 0; j < tempArr[i].length; j++) {
                if (j == 0) {
                    tempArr[i][j] = String.valueOf((char) startingChar);
                    startingChar ++;
                    continue;
                }
                if (tempArr[i][j] == null) {
                    tempArr[i][j] = "~";
                }
            }
        }
        return tempArr;
    }

//    ===================================================================================
//                     ***** PRINT METHODS *****

    public void printGameGrid() {
        for (int i = 0; i < gameGrid.length; i++) {
            String row = "";
            for (int j = 0; j < gameGrid.length; j++){
                row += gameGrid[i][j];
                row += " ";
            }
            System.out.println(row);
        }
    }

    public void printFogGrid() {
        for (int i = 0; i < fogGrid.length; i++) {
            String row = "";
            for (int j = 0; j < fogGrid.length; j++){
                row += fogGrid[i][j];
                row += " ";
            }
            System.out.println(row);
        }
    }

}
