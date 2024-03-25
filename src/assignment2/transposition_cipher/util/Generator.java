package assignment2.transposition_cipher.util;

import java.util.*;

public class Generator {

    public static String generateRandomPrivateKey() {
        //WORK_UNIT만큼의 열을 셔플해야함.
        List<String> numbers = Arrays.asList("1", "2", "3", "4", "5");
        Collections.shuffle(numbers);
        StringBuilder privateKey = new StringBuilder();

        for (String number : numbers) {
            privateKey.append(number);
        }

        return privateKey.toString();
    }

}
