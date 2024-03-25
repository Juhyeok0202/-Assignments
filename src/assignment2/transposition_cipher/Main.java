package assignment2.transposition_cipher;

    /*
    1. 2차원 배열에 넣고
    2. keyword : 43152 와 같이 주어 '열'을 재배치한다.
    3. ciphertext는 행-우선으로 읽어 만들어낸다.
     */

import assignment2.caeser_cipher.util.FileInDictionary;
import assignment2.transposition_cipher.util.Generator;
import assignment2.transposition_cipher.util.Recoder;
import assignment2.transposition_cipher.util.TextBuilder;
import assignment2.transposition_cipher.util.Timer;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static final String FILE_PATH = "C:/Project_files/computer_security/src/assignment2/words.txt";
    static final int WORK_UNIT = 5;
    static int nonPaddedLen;
    static int paddedLen;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("한 번 실행하고 모든 결과를 확인하려면 : 1");
        System.out.println("100번 실행하고 각가의 실행 nano-sec와 평균 nano-sec값을 확인하려면 : 2");

        int input = sc.nextInt();
        if (input == 1) {
            run();
        } else {
            runOneHundred();
        }

    }

    private static void runOneHundred() throws IOException {
        FileInDictionary fileInDictionary = FileInDictionary.initFileInDictionary(FILE_PATH); //Text(사전)파일에서 모든 단어를 추출하여 1.평문으로 만들 단어 리스트 2.사전 내 모든 단어리스트 를 초기화 한다.

        int execCnt = 0;
        while (execCnt++ < 100) {

            //평문 생성
            StringBuilder plainText = TextBuilder.buildPlainText(fileInDictionary.getWords());
            nonPaddedLen = plainText.length();

            //평문 Padding
            StringBuilder paddedPlainText = TextBuilder.padding(plainText, WORK_UNIT);
            paddedLen = paddedPlainText.length();

            // Transposition Cipher
            String cipherText = TextBuilder.buildTransCipher(paddedPlainText, Generator.generateRandomPrivateKey(), WORK_UNIT);

            // password cracking
            int paddedVal = paddedLen - nonPaddedLen;
            String result = Cracker.crack(cipherText, WORK_UNIT, paddedVal);

            //time pnt
            long time = Timer.calculateTotal();
            Recoder.addTime(time);
            System.out.println("["+execCnt+"번째]"+"경과시간(ns) : " + time);
        }

        System.out.println("평균 경과 시간(ns) : "+Recoder.getAvgExecTime());
    }



    private static long run() throws IOException {
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
        System.out.println("경과시간(ns) : " + Timer.calculateTotal());
        return Timer.calculateTotal();
    }

}
