package assignment2.transposition_cipher.util;

public class Timer {

    private static long beforeTime;
    private static long afterTime;

    public static long calculateTotal() {
        return (afterTime - beforeTime);
    }

    public static long getBeforeTime() {
        return beforeTime;
    }

    public static void setBeforeTime(long beforeTime) {
        Timer.beforeTime = beforeTime;
    }

    public static long getAfterTime() {
        return afterTime;
    }

    public static void setAfterTime(long afterTime) {
        Timer.afterTime = afterTime;
    }
}
