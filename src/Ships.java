public class Ships {
    // Contains fields and methods that all ships will use regardless of properties
    private String firstCoordinate;
    private String secondCoordinate;
    private int lives;
    private int[][] coordinatesArray;
    private boolean messageShown = false;

    public Ships(String firstCoordinate, String secondCoordinate, int length) {
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;
        coordinatesArray =  App.createCoordinatesArray(firstCoordinate, secondCoordinate, length);
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


    public int checkLives() {
        try {
            int temp = 0;
            for (int i = 0; i < this.coordinatesArray.length; i++) {

                if (App.gameGrid[coordinatesArray[i][0]][coordinatesArray[i][1]].equalsIgnoreCase("O")){
                    temp ++;
                }
            }
            this.lives = temp;

        } catch (ArrayIndexOutOfBoundsException e) {
            lives = 3;
        }
        return this.lives;
    }


//    =====================================================================================
            //          GETTERS & SETTERS

    public String getFirstCoordinate() {
        return firstCoordinate;
    }

    public String getSecondCoordinate() {
        return secondCoordinate;
    }

    public int getLives() {
        return lives;
    }

    public int[][] getCoordinatesArray() {
        return coordinatesArray;
    }

    public boolean isMessageShown() {
        return messageShown;
    }

    public void setMessageShown(boolean messageShown) {
        this.messageShown = messageShown;
    }

    public void printCoordinatesArray() {
        for (int i = 0; i < coordinatesArray.length; i++) {
            String row = "";
            for (int j = 0; j < coordinatesArray[i].length; j++) {
                row += coordinatesArray[i][j];
            }
            System.out.println("Row = " + row);
        }
    }
}