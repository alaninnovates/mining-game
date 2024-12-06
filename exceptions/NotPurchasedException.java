package exceptions;

public class NotPurchasedException extends Exception {
    public NotPurchasedException() {
        super("This item has not been purchased.");
    }
}
