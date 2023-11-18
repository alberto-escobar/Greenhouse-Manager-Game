package model.exceptions;

public class DuplicatePlantException extends Exception {
    public DuplicatePlantException() {
        super("A plant with this name already exists!");
    }
}
