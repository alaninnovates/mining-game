package exceptions;

public class RequirementsException extends RuntimeException {
    public RequirementsException() {
        super("Cannot afford item!");
    }
}
