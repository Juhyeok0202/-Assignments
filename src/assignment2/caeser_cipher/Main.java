package assignment2.caeser_cipher;

import assignment2.caeser_cipher.util.FileInDictionary;
import assignment2.caeser_cipher.util.Generator;
import assignment2.caeser_cipher.util.TextBuilder;

import java.io.IOException;

public class Main {

    static final int privateKey = 3; //TODO 랜더으로
    static final String FILE_PATH = "C:/Project_files/computer_security/src/assignment2/words.txt";
    private static final int ALPHABET_COUNT = 26; // 알파벳 글자 수

    public static void main(String[] args) throws IOException {

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

    private static void showResult(StringBuilder plainText, String result) {
        System.out.println();
        if(plainText.toString().equals(result)) System.out.println("크래킹 성공!");
        else System.out.println("크래킹 실패...");

        System.out.println("평문: " + plainText.toString());
        System.out.println("복호화된암호: " + result);
    }
}
