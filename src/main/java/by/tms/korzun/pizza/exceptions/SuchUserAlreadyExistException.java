package by.tms.korzun.pizza.exceptions;

public class SuchUserAlreadyExistException extends Exception {
    public SuchUserAlreadyExistException(final String message) {
        super(message);
    }
}
