import java.util.Scanner;

public class Ships {
    // Contains fields and methods that all ships will use regardless of properties
    private String firstCoordinate;
    private String secondCoordinate;
    private int lives;
    private int[][] coordinatesArray;
    private boolean messageShown = false;

    public Ships(String firstCoordinate, String secondCoordinate, int length, Player player1, Scanner scanner) {
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;
        coordinatesArray =  createCoordinatesArray(firstCoordinate, secondCoordinate, length, player1, scanner);
    }

    public int[][] createCoordinatesArray(String firstCoordinate, String secondCoordinate, int shipLength, Player player1, Scanner scanner) {
        int[] strToCoordinate = Utility.coordinateToArray(firstCoordinate, player1, scanner);
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

    // New method created check if the coordinate inputted is part of this ship's array
    public boolean wasHit(int[] coordinate){
        boolean hasCoordinate = false;
//      Created a String for each coordinate in coordinatesArray and checked it against a
//      String created for the shotCoordinate
        for (int i = 0; i < this.coordinatesArray.length; i++) {
            String currentCoordinateString = "";
            String shotCoordinateString = "";

            for (int j = 0; j < coordinatesArray[i].length; j++) {
                currentCoordinateString += coordinatesArray[i][j];
                shotCoordinateString += coordinate[j];
            }
            if (currentCoordinateString.equals(shotCoordinateString)) {
                hasCoordinate = true;
                break;
            }
        }
        return hasCoordinate;
    }

    public int checkLives(Player player) {
        try {
            int temp = 0;
            for (int i = 0; i < this.coordinatesArray.length; i++) {

                if (player.gameGrid[coordinatesArray[i][0]][coordinatesArray[i][1]].equalsIgnoreCase("O")){
                    temp ++;
                }
            }
            this.lives = temp;

        } catch (ArrayIndexOutOfBoundsException e) {
            lives = 3;
        }
        return this.lives;
    }
}