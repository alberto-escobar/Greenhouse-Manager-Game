package model.exceptions;

public class InsufficientSpaceException extends Exception {
    public InsufficientSpaceException() {
        super("Not enough growing pots in greenhouse for new plant!");
    }
}
