package assignment2.transposition_cipher;

import assignment2.transposition_cipher.util.FileInDictionary;
import assignment2.transposition_cipher.util.Permutation;
import assignment2.transposition_cipher.util.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static assignment2.transposition_cipher.Main.hint;

public class Cracker {

    static int COL;
    static int ROW;
    public static String crack(String cipherText, int workUnit, int paddedVal) {
        Timer.setBeforeTime(System.currentTimeMillis());

        COL = workUnit;
        ROW = cipherText.length() / workUnit;

        // Cipher Text 2D 배열( trans :  Column By Colum )
        char[][] matrixFromCipherText = writeColByCol(cipherText.toCharArray());

        //WORK UNIT 길이의 자리바꿈 맵 생성
        List<String> allColumnPermutations = Permutation.generatePermutations(workUnit);

        //자리바꿈 맵들로 재배치되어 복호화된 암호 후보군 리스트
        List<String> candidates = new ArrayList<>();

        //최종 복호화된 Text
        String decodedText = "";

        //WORK UNIT 길이의 생성된 자리바꿈 맵들로 재배치 + 후보군리스트에 추가 (hint 있을 경우 바로 리턴)
        for (String perm : allColumnPermutations) {

            //재배치
            char[][] candidateMatrix = replaceColumn(matrixFromCipherText, perm);

            //재배치된 2차원배열 순차적으로 읽어 StringBuilder로 이어붙인다.
            StringBuilder sb = new StringBuilder();
            for (char[] matrix : candidateMatrix) {
                sb.append(matrix);
            }

            //힌트가 주어진 경우에는 후보군 리스트 addition을 하지 않고, hint로 시작하는 Text만 찾는다.
            if (!hint.isEmpty()) {
                //TODO: 이것들만 있는 후보군을 모아.
                if (sb.toString().startsWith(hint)) {
                    decodedText = sb.toString();
                    candidates.add(decodedText);
                }

            } else {

                //힌트가 없는 경우 후보군 리스트에 addition한다.
                candidates.add(sb.toString());
            }
        }

        //후보군 리스트업 완료 후, 주어진 사전 내 단어 빈도수를 체크한다.
        decodedText = isInDictionary(candidates);

        Timer.setAfterTime(System.currentTimeMillis());
        return decodedText.substring(0,decodedText.length()-paddedVal); //# 패딩 제거
    }

    private static String isInDictionary(List<String> candidates) {

        // 사전 내에 있는 모든 단어 리스트(특수문자를 포함한 경우는 제외한다.)
        List<String> allInDictionary = FileInDictionary.getAllInDictionary();

        // candidate리스트 내의 각 후보군의 사전 내 단어 빈도수 List
        List<Integer> frequencyList = new ArrayList<>();

        // START: 후보군 선택 loop
        for (int i = 0; i < candidates.size(); ++i) {
            int frequency = 0;

            // i번째 후보군에 대해서 사전 내 단어 모두를 체크loop
            for (int j = 0; j < allInDictionary.size(); j++) {

                // 후보군 Text가 j번째 사전 내 단어를 포하하면, 빈도수 증가
                if (candidates.get(i).contains(allInDictionary.get(j))) {
                    frequency++;
                }
            }

            // i번째 candidate 빈도수를 빈도수리스트에 추가
            frequencyList.add(frequency);
        } // END OF LOOP

        // 빈도수 리스트에서 최고로 높은 빈도수
        Integer max = Collections.max(frequencyList);

        // 가장 높은 빈도수의 인덱스(가장 높은 빈도를 가진 후보군의 인덱스)
        int resultIndex = frequencyList.indexOf(max);

        //found! (패딩 제거 X상태)
        String foundPlainText = candidates.get(resultIndex);

        assignment2.caeser_cipher.util.Timer.setAfterTime(System.currentTimeMillis());
        return foundPlainText;
    }


    private static char[][] replaceColumn(char[][] modifiedMatrix, String perm) {

        // 생성된 자리바꿈 맵으로 재배치된 2D 배열
        char[][] replacedMatrix = new char[ROW][COL];

        for (int i = 0; i < COL; i++) {
            for (int j = 0; j < ROW; j++) {
                replacedMatrix[j][i] = modifiedMatrix[j][perm.charAt(i)-48-1]; // TODO: 맞나 디버깅
            }
        }

        return replacedMatrix;
    }

    private static char[][] writeColByCol(char[] cipherChars) {

        //CipherText를 최초로 2D 배열로 변경한 배열
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
