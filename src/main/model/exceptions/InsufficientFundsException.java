package model.exceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Not enough funds in wallet to purchase this!");
    }
}
