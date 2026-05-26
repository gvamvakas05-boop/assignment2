package mainpackage;

// Φτιαξαμε το δικο μας custom exception σε περιπτωση που καποιος παει να βαλει γραμματα αντι για αριθμο στο μητρωο
public class InvalidRegistrationException extends Exception {
    
    public InvalidRegistrationException(String message) {
        super(message);
    }
    
    public InvalidRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}