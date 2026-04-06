// Custom Exception: Thrown when a negative or zero amount is entered
public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}
