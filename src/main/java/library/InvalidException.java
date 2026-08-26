package library;

public class
InvalidException extends Exception { // custom exception for invalid inputs from text fields
    public InvalidException(String message) {
        super(message);
    }
}
