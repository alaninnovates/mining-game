package game;

public class RebirthCalculator {
    public static int calculateRebirthCost(int rebirths) {
        return (int) Math.pow(10, rebirths + 2);
    }

    // multiplier for how many blocks recieved after breaking 1 block
    public static int calculateRebirthBlockMutiplier(int rebirths) {
        return rebirths + 1;
    }

    // damage multiplier
    public static int calculateRebirthDamageMultiplier(int rebirths) {
        return (int) Math.pow(2, rebirths);
    }
}
