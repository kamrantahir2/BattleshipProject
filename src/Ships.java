public class Ships {
    // Contains fields and methods that all ships will use regardless of properties
    private int firstCoordinate;
    private int secondCoordinate;
    private int lives;

    public Ships(int firstCoordinate, int secondCoordinate, int lives) {
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;
        this.lives = lives;
    }
}