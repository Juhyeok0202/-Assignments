package assignment2.transposition_cipher;

    /*
    1. 2차원 배열에 넣고
    2. keyword : 43152 와 같이 주어 '열'을 재배치한다.
    3. ciphertext는 행-우선으로 읽어 만들어낸다.
     */

import assignment2.transposition_cipher.util.FileInDictionary;
import assignment2.transposition_cipher.util.Generator;
import assignment2.transposition_cipher.util.Recorder;
import assignment2.transposition_cipher.util.TextBuilder;
import assignment2.transposition_cipher.util.Timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final String FILE_PATH = "C:/Project_files/computer_security/src/assignment2/words.txt";
    static final int WORK_UNIT = 5;
    static int nonPaddedLen;
    static int paddedLen;
    public static String hint="";
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("한 번 실행하고 모든 결과를 확인하려면 : 1");
        System.out.println("100번 실행하고 각가의 실행 nano-sec와 평균 nano-sec값을 확인하려면 : 2\n");
        int input = Integer.parseInt(br.readLine());

        System.out.print("첫 단어 힌트를 드릴까요?(\"yes\" or \"no\") : ");
        boolean isHint;
        if(br.readLine().equals("yes")) isHint = true;
        else isHint = false;

        if (input == 1) {
            run(isHint);
        } else {
            runOneHundred(isHint);
        }

    }

    private static void runOneHundred(boolean isHint) throws IOException {
        FileInDictionary fileInDictionary = FileInDictionary.initFileInDictionary(FILE_PATH); //Text(사전)파일에서 모든 단어를 추출하여 1.평문으로 만들 단어 리스트 2.사전 내 모든 단어리스트 를 초기화 한다.

        int execCnt = 0;
        while (execCnt++ < 100) {

            //평문 생성
            StringBuilder plainText = TextBuilder.buildPlainText(fileInDictionary.getWords(), isHint);
            nonPaddedLen = plainText.length();

            //평문 Padding
            StringBuilder paddedPlainText = TextBuilder.padding(plainText, WORK_UNIT);
            paddedLen = paddedPlainText.length();

            // Transposition Cipher
            String cipherText = TextBuilder.buildTransCipher(paddedPlainText, Generator.generateRandomPrivateKey(), WORK_UNIT);

            // password cracking
            String foundText = Cracker.crack(cipherText, WORK_UNIT, paddedLen - nonPaddedLen);
            if (!foundText.equals(plainText.toString())) {
                System.out.println("크래킹 실패");
                System.exit(0);
            }

            //time pnt
            long time = Timer.calculateTotal();
            Recorder.addTime(time);
            System.out.println("["+execCnt+"번째]"+"경과시간(ms) : " + time);
        }

        System.out.println("평균 경과 시간(ms) : "+ Recorder.getAvgExecTime());
    }



    private static long run(boolean isHint) throws IOException {
        FileInDictionary fileInDictionary = FileInDictionary.initFileInDictionary(FILE_PATH); //Text(사전)파일에서 모든 단어를 추출하여 1.평문으로 만들 단어 리스트 2.사전 내 모든 단어리스트 를 초기화 한다.

        //평문 생성
        StringBuilder plainText = TextBuilder.buildPlainText(fileInDictionary.getWords(), isHint);
        nonPaddedLen = plainText.length();
//        System.out.println("Before PlainText : " + plainText + "\nsize is : " + nonPaddedLen);

        //평문 패딩처리
        StringBuilder paddedPlainText = TextBuilder.padding(plainText, WORK_UNIT);
        paddedLen = paddedPlainText.length();
//        System.out.println("After PlainText : " + paddedPlainText + "\nsize is : " + paddedLen);

        // Transposition Cipher 변환
        String privateKey = Generator.generateRandomPrivateKey(); // 자리바꿈 맵(5자리)
        String cipherText = TextBuilder.buildTransCipher(paddedPlainText, privateKey, WORK_UNIT);
        System.out.println("\nCipherText : " + cipherText);

        // Start to Cracking
        int paddedVal = paddedLen - nonPaddedLen;
        String result = Cracker.crack(cipherText, WORK_UNIT,paddedVal);
        System.out.println("DecodedText : " + result);

        //Validation
        if (plainText.toString().equals(result)) {
            System.out.println("크래킹 성공!");
        } else {
            System.out.println("크래킹 실패...");
            System.exit(0);
        }

        //Time pnt
        System.out.println("경과시간(ns) : " + Timer.calculateTotal());
        return Timer.calculateTotal();
    }

}
