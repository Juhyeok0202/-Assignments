package assignment2.caeser_cipher.util;

public class Shifter {

    public static int shiftCharWithinAlphabet(char originalChar, int privateKey) { // For encoding
        char base = Character.isUpperCase(originalChar) ? 'A' : 'a';
        int alphabetPosition = (originalChar - base + privateKey) % 26 + base; // 알파벳 내 위치 계산

        return alphabetPosition;
    }

    public static int decodeCharWithinAlphabet(char originalChar, int privateKey) { // For decoding
        char base = Character.isUpperCase(originalChar) ? 'A' : 'a';
        int alphabetPosition = (originalChar - base - privateKey) % 26; // 알파벳 내 위치 계산

        if (alphabetPosition < 0) {
            alphabetPosition += 26;
        }

        return alphabetPosition +  base;
    }
}
