package assignment2.transposition_cipher;

public class Cracker {

    // TODO: 최종적으로 padding된 z를 빼주어야함.
    static int COL;
    static int ROW;
    public static String crack(String cipherText, int workUnit, int paddedVal) {
        COL = workUnit;
        ROW = cipherText.length() / workUnit;

        String decodedText = readDecodedText(restoreMatrix(cipherText.toCharArray()), paddedVal);


        return decodedText;
    }

    private static String readDecodedText(char[][] backedMatrix, int paddedVal) {

        StringBuilder sb = new StringBuilder();

        for (char[] matrix : backedMatrix) {
            for (char c : matrix) {
                sb.append(c);
            }
        }

        //패딩된 'z' 제거
        if (paddedVal > 0) {
            sb.delete(sb.length() - paddedVal, sb.length());
        }


        return sb.toString();
    }

    private static char[][] restoreMatrix(char[] cipherChars) {
        char[][] originMatrix = new char[ROW][COL];

        int index = 0;

        for (int i = 0; i < COL; ++i) {
            for (int j = 0; j < ROW; ++j) {
                originMatrix[j][i] = cipherChars[index++];
            }
        }

        return originMatrix;


    }
}
