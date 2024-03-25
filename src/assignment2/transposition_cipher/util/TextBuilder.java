package assignment2.transposition_cipher.util;

import java.util.List;

/**
 * 평문생성
 * 전치암호생성
 */
public class TextBuilder {

    /**
     *      ※※※
     *      * 2차원 배열을 통한 자리바꿈과 열 순서로 읽어들이는 작업을 간단화 하기 위해서
     *      * 행과 열을 반전시켜 행렬을 구성하여 구현합니다.
     */
    static int COL;
    static int ROW;

    public static String buildTransCipher(StringBuilder plainText, String privateKey, int workUnit) {
        COL = workUnit;
        ROW = plainText.length() / workUnit;

        char[] plainChars = plainText.toString().toCharArray(); // String 형태의 평문
        char[][] plainMatrix = new char[COL][ROW]; // 행렬 형태의 평문


        initMatrix(plainChars, plainMatrix); // Column replace 이전의 행렬
        replaceMatrix(plainMatrix, privateKey); // Replaced Matrix by private Key

        return readCipherText(plainMatrix);
    }

    private static String readCipherText(char[][] plainMap) { //재배치된 행열을 열 순서로 읽는( 현재 프로그램에서는 행순서로 읽는 )
        StringBuilder sb = new StringBuilder();

        for (char[] chars : plainMap) {
            for (char aChar : chars) {
                sb.append(aChar);
            }
        }
        return sb.toString();
    }

    private static void replaceMatrix(char[][] plainMap, String switchSerial) {
        char[][] replacedMatrix = new char[COL][ROW]; // 재배치된 행렬 형태의 평문

        for (int i = 0; i <COL; i++) {
            replacedMatrix[i] = plainMap[switchSerial.charAt(i)-48-1];
        }
    }

    /**
     * 2차원 배열을 통한 자리바꿈과 열 순서로 읽어들이는 작업을 간단화 하기 위해서
     * 행과 열을 반전시켜 행렬을 구성합니다.
     *
     * @param plainChars
     * @param plainMap
     */
    private static void initMatrix(char[] plainChars, char[][] plainMap) {

        int index = 0;
        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COL; j++) {
                plainMap[j][i] = plainChars[index++];
            }
        }
    }


    public static StringBuilder buildPlainText(List<String> words) {
        StringBuilder plainText = new StringBuilder();
        for (String word : words) {
            plainText.append(word);
        }
        return plainText;
    }

    public static StringBuilder padding(StringBuilder plainText, int workUnit) {
        int plainTextLength = plainText.length();
        StringBuilder sb = new StringBuilder();
        sb.append(plainText);
        if (plainTextLength % workUnit != 0) { // work_unit으로 나누어 떨어지지 않을 경우 'z'로 padding
            //나누어 떨어지지 않음 => z로 padding TODO: invalid paddingSize -> bruteforce활용해서 리팩토링
            while (plainTextLength % workUnit != 0) {
                sb.append('z');
                plainTextLength = sb.length();
            }
        }

        return sb;
    }
}
