package library;

public class
NullException extends Exception { // custom exception for null text fields
    public NullException(String message) {
        super(message);
    }
}
