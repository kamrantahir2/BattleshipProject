
public class Utility {

    // All methods in this class are declared static. This class will handle small
    // utility methods

    public static int[] coordinateToArray(String str) {
        // This method will return an int[] array representing coordinates
        // It takes a char[] array and turns the letter into an int and adds it to
        // the array along with the second part of the coordinate.

        // We start by calling the seperateCoordinate method to create a char[] array
        int numIndex = -1;

        // We then turn the first character, the letter, into an int index
        int letterIndex = -1;
        char character = 'a';

        if (str.length() == 2) {
            char[] characterArr = seperateCoordinate(str);
            character = characterArr[0];
            String numString = String.valueOf(characterArr[1]);
            numIndex = Integer.parseInt(numString);
        }
        else if (str.length() == 3) {
            character = str.charAt(0);
            String numString = str.substring(1);
            numIndex = Integer.parseInt(numString);
        }

        int startingCharUpper = 65;
        int startingCharLower = 97;

        for (int i = 1; i < App.gameGrid.length; i++) {
            if (character == (char) startingCharUpper || character == (char)
                    startingCharLower) {
                letterIndex = i;
                break;
            }
            startingCharUpper ++;
            startingCharLower ++;
        }

        return new int[] {letterIndex, numIndex};
    }


    private static char[] seperateCoordinate(String coordinates){
        boolean hasCharFirstLetter = false;
        boolean hasSecondCharNum = false;
        char[] coordinateAsArray = new char[2];

        if (coordinates.length() == 2){
            hasCharFirstLetter = Character.isLetter(coordinates.charAt(0));
            hasSecondCharNum =  Character.isDigit(coordinates.charAt(1));
        } else if (coordinates.length() == 3) {
            hasCharFirstLetter = Character.isLetter(coordinates.charAt(0));
            String doubleDigitNum = coordinates.substring(1);
            for (int i = 0; i < doubleDigitNum.length(); i++) {
                hasSecondCharNum =  Character.isDigit(doubleDigitNum.charAt(i));
                if (!hasSecondCharNum) {
                    break;
                }
            }
        }

        if (coordinates.length() == 2 && hasCharFirstLetter && hasSecondCharNum) {
            for (int i = 0; i < coordinates.length(); i++) {
                coordinateAsArray[i] = coordinates.charAt(i);
            }
        }
        return coordinateAsArray;
    }


}