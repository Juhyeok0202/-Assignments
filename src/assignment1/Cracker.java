package assignment1;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Cracker {
    static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String NUMBERS = "0123456789";
    static final String NUMCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String NUMCHARSPECIAL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~";

    static final String CUSTOM_HASH_HEADLINE = "---------Result: unsafety---------";
    static final String MD5_HASH_HEADLINE = "---------Result: MD5---------";

    static String PASSWORD_STRING_SET = "";

    static long afterTime;
    static long beforeTime;
    public static void crackingUsingBruteForce(String hashed, int type, boolean isSafety) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        //init
        afterTime = 0;
        beforeTime = 0;

        PASSWORD_STRING_SET = getTypeString(type);

        beforeTime = System.currentTimeMillis();

        for (int length = 1; length <= 9; length++) { // 가정: 패스워드 길이는 최대 6
            generate("", PASSWORD_STRING_SET, length, hashed, isSafety);
        }
    }

    private static String getTypeString(int type) {
        if (type == 1) {
            return NUMBERS;
        } else if (type == 2) {
            return CHARACTERS;
        } else if (type == 3) {
            return NUMCHAR;
        } else if (type == 4) {
            return NUMCHARSPECIAL;
        } else {
            System.out.println("Invalid type");
            return null;
        }
    }


    private static void generate(String prefix, String characters, int length, String hashed, boolean isSafety) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (length == 0) {

            if(isSafety==true) verify_safetyHash(prefix, hashed);
            else verify_unSafetyHash(prefix,hashed);
            return ;
        }

        for (int i = 0; i < characters.length(); i++) {
            String newPrefix = prefix + characters.charAt(i); // 새로운 조합 생성
            generate(newPrefix, characters, length - 1, hashed, isSafety);
        }
    }

    private static void verify_unSafetyHash(String prefix, String hashed) {
        if (hashed.equals(PasswordGenerator.generateUnsafetyHash(prefix))) {
            afterTime = System.currentTimeMillis();
            printResult(CUSTOM_HASH_HEADLINE, PASSWORD_STRING_SET, prefix);
        }
    }

    private static void printResult(String headLine, String passwordStringSet, String prefix) {
        System.out.println(headLine);
        System.out.println("StringSet : " + passwordStringSet);
        System.out.println("Found : "+prefix);
        System.out.println("경과 시간(ms) : "+(afterTime-beforeTime));
        System.out.println();
    }

    private static void verify_safetyHash(String prefix, String hashed) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (hashed.equals(PasswordGenerator.generateHash(prefix, "MD5"))) {
            afterTime = System.currentTimeMillis();
            printResult(MD5_HASH_HEADLINE, PASSWORD_STRING_SET, prefix);
        }
    }

}
