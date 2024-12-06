package exceptions;

public class RequirementsException extends RuntimeException {
    public RequirementsException() {
        super("Requirements not fulfilled");
    }
}
