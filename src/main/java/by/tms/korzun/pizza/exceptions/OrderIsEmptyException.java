package by.tms.korzun.pizza.exceptions;

public class OrderIsEmptyException extends Exception {
    public OrderIsEmptyException(final String message) {
        super(message);
    }
}
