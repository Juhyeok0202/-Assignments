package assignment2.caeser_cipher;

import assignment2.caeser_cipher.util.FileInDictionary;
import assignment2.caeser_cipher.util.Shifter;
import assignment2.caeser_cipher.util.TextBuilder;
import assignment2.caeser_cipher.util.Timer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cracker {

//    /**
//     * @param cipherText
//     * @return 후보군 리스트
//     */
//    public static List<String> crack(StringBuilder cipherText) {
//        String text = cipherText.toString();
//
//        List<String> candidate = new ArrayList<>();
//
//        for (int i = 0; i <25; i++) {
//            StringBuilder decodedText = new StringBuilder();
//
//            for (int j = 0; j <text.length(); j++) {
//                decodedText.append((char)(text.charAt(j)-i));
//            }
//            candidate.add(decodedText.toString());
//        }
//
//        return candidate;
//    }


    public String crack(StringBuilder cipherText, FileInDictionary fileInDictionary) throws IOException {

        Timer.setBeforeTime(System.currentTimeMillis());

        String text = cipherText.toString();
        List<String> candidate = new ArrayList<>();

        for (int i = 1; i <25; i++) { // random max value is 25
            StringBuilder decodedText = new StringBuilder(); //  Decryption password by private key that is received input integer value

            for (int j = 0; j <text.length(); j++) {
                //TODO: 디코딩도 특수문자 처리해야함.
                char originalChar = text.charAt(j);
//                int decodedChar = originalChar - i;
                int decodedChar = Shifter.decodeCharWithinAlphabet(originalChar, i); //알파벳 범위에서 순회하도록 Shift

                decodedText.append((char)decodedChar); // Decryption
            }
            candidate.add(decodedText.toString());
        }

        String result = validateInDictionary(candidate, fileInDictionary);

        return result;
    }

    private String validateInDictionary(List<String> candidates, FileInDictionary fileInDictionary) throws IOException {
        List<String> wordsInDictionary = fileInDictionary.getAllInDictionary(); // 사전 Text파일 내 모든 단어의 리스트
        List<Integer> frequencyList = new ArrayList<>(); //인덱스에 따른 빈도수 리스트

        // 사전 내 단어 빈도수를 통한 후보군 특정
        for (int i = 0; i < candidates.size(); i++) {
            int frequency = 0;

            for (int j = 0; j < wordsInDictionary.size(); j++) { //416296
                if (candidates.get(i).contains(wordsInDictionary.get(j))) {
                    frequency++;
                }
            }
                frequencyList.add(frequency);
        }
        Integer max = Collections.max(frequencyList); //최대 빈도수 (후보군 중에서 사전에 있는 단어를 가장 많이 포함한 '특정될 디코딩된 암호')
        int resultIndex = frequencyList.indexOf(max);

        Timer.setAfterTime(System.currentTimeMillis());
        return candidates.get(resultIndex);
    }
}
