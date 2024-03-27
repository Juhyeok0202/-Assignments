package assignment2.transposition_cipher.util;

import java.util.ArrayList;
import java.util.List;

public class Recoder {
    private static List<Long> execTimes = new ArrayList<>();

    public static void addTime(long timeElapsed) {
        execTimes.add(timeElapsed);
    }

    public static long getAvgExecTime() {
        long sum = 0;
        for (Long execTime : execTimes) {
            sum += execTime;
        }

        return sum/ execTimes.size();
    }
}
