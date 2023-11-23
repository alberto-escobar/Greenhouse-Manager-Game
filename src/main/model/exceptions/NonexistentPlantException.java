package model.exceptions;

public class NonexistentPlantException extends Exception {
    public NonexistentPlantException() {
        super("No plant with this name exists!");
    }
}
