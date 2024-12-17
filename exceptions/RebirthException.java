package exceptions;

public class RebirthException extends RuntimeException {
    public RebirthException(int coinsMissing) {
        super("Cannot rebirth, you need " + coinsMissing + " more coins!");
    }
}
