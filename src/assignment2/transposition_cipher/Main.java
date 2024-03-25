package assignment2.transposition_cipher;

    /*
    1. 2차원 배열에 넣고
    2. keyword : 43152 와 같이 주어 '열'을 재배치한다.
    3. ciphertext는 행-우선으로 읽어 만들어낸다.
     */

import assignment2.caeser_cipher.util.FileInDictionary;
import assignment2.transposition_cipher.util.Generator;
import assignment2.transposition_cipher.util.TextBuilder;
import assignment2.transposition_cipher.util.Timer;

import java.io.IOException;

public class Main {
    static final String FILE_PATH = "C:/Project_files/computer_security/src/assignment2/words.txt";
    static final int WORK_UNIT = 5;
    static int nonPaddedLen;
    static int paddedLen;

    public static void main(String[] args) throws IOException {
        FileInDictionary fileInDictionary = FileInDictionary.initFileInDictionary(FILE_PATH); //Text(사전)파일에서 모든 단어를 추출하여 1.평문으로 만들 단어 리스트 2.사전 내 모든 단어리스트 를 초기화 한다.

        //평문 생성
        StringBuilder plainText = TextBuilder.buildPlainText(fileInDictionary.getWords());
        nonPaddedLen = plainText.length();
        System.out.println("Before PlainText : " + plainText + "\nsize is : " + nonPaddedLen);

        //평문 Padding
        StringBuilder paddedPlainText = TextBuilder.padding(plainText, WORK_UNIT);
        paddedLen = paddedPlainText.length();
        System.out.println("After PlainText : " + paddedPlainText + "\nsize is : " + paddedLen);

        // Transposition Cipher
        String cipherText = TextBuilder.buildTransCipher(paddedPlainText, Generator.generateRandomPrivateKey(), WORK_UNIT);
        System.out.println("\nCipherText : " + cipherText);

        // password cracking
        int paddedVal = paddedLen - nonPaddedLen;
        String result = Cracker.crack(cipherText, WORK_UNIT,paddedVal);
        System.out.println("DecodedText : " + result);

        //Validation
        if (plainText.toString().equals(result)) {
            System.out.println("크래킹 성공!");
        } else {
            System.out.println("크래킹 실패...");
        }

        //time pnt
        System.out.println("경과시간(ms) : " + Timer.calculateTotal());
    }

}
