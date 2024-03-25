package assignment2.caeser_cipher;

import assignment2.caeser_cipher.util.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    static final String FILE_PATH = "C:/Project_files/computer_security/src/assignment2/words.txt";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("한 번 실행하고 모든 결과를 확인하려면 : 1");
        System.out.println("100번 실행하고 각가의 실행 micro-sec와 평균 micro-sec값을 확인하려면 : 2");

        int input = sc.nextInt();
        if (input == 1) {
            runOnce();
        } else {
            runOneHundred();
        }
    }

    private static void runOnce() throws IOException {
        FileInDictionary fileInDictionary = FileInDictionary.initFileInDictionary(FILE_PATH); //Text(사전)파일에서 모든 단어를 추출하여 1.평문으로 만들 단어 리스트 2.사전 내 모든 단어리스트 를 초기화 한다.

        //private Key
        StringBuilder plainText = TextBuilder.buildPlainText(fileInDictionary.getWords()); // 사전의 단어를 랜덤으로 조합하여 평문을 만든다.
        StringBuilder cipherText = TextBuilder.buildCipherText(plainText, Generator.generateRandomShiftNumber()); // 평문을 랜덤 쉬프트 값(1~25)을 이용하여 시저암호화 한다.

        System.out.println("plainText : " + plainText);
        System.out.println("cipherText: " + cipherText);

        /**
         * 암호 해독
         */
        try {
            Cracker crack = new Cracker();
            String result = crack.crack(cipherText, fileInDictionary);

            showResult(plainText, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runOneHundred() throws IOException {
        FileInDictionary fileInDictionary = FileInDictionary.initFileInDictionary(FILE_PATH); //Text(사전)파일에서 모든 단어를 추출하여 1.평문으로 만들 단어 리스트 2.사전 내 모든 단어리스트 를 초기화 한다.

        int execCnt = 0;

        while (execCnt++ < 100) {
            //private Key
            StringBuilder plainText = TextBuilder.buildPlainText(fileInDictionary.getWords()); // 사전의 단어를 랜덤으로 조합하여 평문을 만든다.
            StringBuilder cipherText = TextBuilder.buildCipherText(plainText, Generator.generateRandomShiftNumber()); // 평문을 랜덤 쉬프트 값(1~25)을 이용하여 시저암호화 한다.


            /**
             * 암호 해독
             */
            try {
                Cracker crack = new Cracker();
                crack.crack(cipherText, fileInDictionary);

                long time = Timer.calculateTotal();
                System.out.println("["+execCnt+"번째]"+"경과시간(ms) : " + time);

                Recoder.addTime(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("평균 경과 시간(ms) : "+Recoder.getAvgExecTime());
    }

    private static void showResult(StringBuilder plainText, String result) {
        System.out.println();
        if(plainText.toString().equals(result)) System.out.println("크래킹 성공!");
        else System.out.println("크래킹 실패...");

        System.out.println("평문: " + plainText.toString());
        System.out.println("복호화된암호: " + result);
        System.out.println("경과시간(ms) : " + Timer.calculateTotal());
    }
}
