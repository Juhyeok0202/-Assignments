package assignment1;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class PasswordGenerator {
    private String password;


    public static String generateUnsafetyHash(String plainText) {
        // ASCII code 값에 변화를 주는 안전하지 않은 해쉬함수
        String hashed = "";

        /**
         * 평문 비밀번호 문자열의 각각의 인덱스 문자열을
         * charAt을 통해 ascii code로 변환 후 +10을 해준다.
         */
        for (int index = 0; index < plainText.length(); ++index) {
            hashed = hashed + (plainText.charAt(index)+10);
        }
        return hashed;
    }

    public static String generateHash(String plainText, String Algorithms) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(Algorithms);
        md.update(plainText.getBytes("utf-8"));
        return Base64.getEncoder().encodeToString(md.digest());
    }
    public static String generateRandomPw(int length, int type) {

        StringBuffer strPwd = new StringBuffer();
        char[] str = new char[1];

        if (type == 1) { //숫자
            int[] strs = new int[1];
            for (int i = 0; i < length; ++i) {
                strs[0] = (int) (Math.random() * 9);
                strPwd.append(strs[0]);
            }
        } else if (type == 2) { // 알파벳
//            for (int i = 0; i < length; ++i) {
//                str[0] = (char) ((Math.random() * 26) + 65);
//                strPwd.append(str);

            for (int i = 0; i < length; ++i) {
                int rnd = (int) (Math.random() * 52); // 대문자 + 소문자 = 52
                if (rnd < 26) {
                    str[0] = (char) ('A' + rnd); // A-Z
                } else {
                    str[0] = (char) ('a' + (rnd - 26)); // a-z
                }
                strPwd.append(str);
            }

        } else if (type == 3) { // 알파벳 + 숫자
            Random random = new Random();
            for (int i = 0; i < length; ++i) {
                if (random.nextBoolean()) { // boolean type의 난수를 리턴 (A-Z)
                    int rnd = (int) (Math.random() * 52); // 대문자 + 소문자 = 52
                    if (rnd < 26) {
                        str[0] = (char) ('A' + rnd); // A-Z
                    } else {
                        str[0] = (char) ('a' + (rnd - 26)); // a-z
                    }
                    strPwd.append(str);
                } else {
                    strPwd.append((random.nextInt(10)));
                }
            }
        } else { // 특수문자 + 알파벳 + 숫자
            for (int i = 0; i < length; i++) {
                str[0] = (char) ((Math.random() * 94) + 33);
                strPwd.append(str);
            }
        }
        return strPwd.toString();
    }
}
