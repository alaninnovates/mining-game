package exceptions;

public class PurchasedException extends Exception {
    public PurchasedException() {
        super("This item has already been purchased.");
    }
}
