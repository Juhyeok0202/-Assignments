package assignment2.caeser_cipher.util;

import java.util.List;

/**
 * 시져암호화
 * 평문생성
 *
 */
public class TextBuilder {

    public static StringBuilder buildCipherText(StringBuilder plainText, int privateKey) {
        StringBuilder cipherText = new StringBuilder();

        for (int i = 0; i < plainText.length(); i++) {
            char originalChar = plainText.charAt(i);
            int shiftedChar = Shifter.shiftCharWithinAlphabet(originalChar, privateKey); //알파벳 범위에서 순회하도록 Shift

            // 순회 완료된 character 로 암호문 재구성(building)
            cipherText.append((char)shiftedChar);
        }
        return cipherText;
    }

    public static StringBuilder buildPlainText(List<String> words) {
        StringBuilder plainText = new StringBuilder();
        for (String word : words) {
            plainText.append(word);
        }
        return plainText;
    }
}
