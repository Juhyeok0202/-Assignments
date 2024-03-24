package assignment2.caeser_cipher.util;

public class Generator {

    public static int generateRandomShiftNumber() {
        return (int) (Math.random() * 25+1);
    }
}

