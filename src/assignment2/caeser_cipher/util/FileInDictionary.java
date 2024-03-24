package assignment2.caeser_cipher.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FileInDictionary {

    private final List<String> words; //selectRandomWords(wordList, 7, 10);
    private final List<String> allInDictionary; //사전 내 전체 단어 리스트

    protected FileInDictionary(List<String> words, List<String> allInDictionary) {
        // protected 를 통한 기본생성자 제한
        this.words = words;
        this.allInDictionary = allInDictionary;
}

    public static FileInDictionary initFileInDictionary (String filePath) throws IOException {
        return new FileInDictionary(filePath);
    }

    public FileInDictionary(String filePath) throws IOException {
        this.words = initWords(filePath);
        this.allInDictionary = getNonRandomWords(filePath);
    }
    public List<String> getAllInDictionary() {
        return this.allInDictionary;
    }

    public List<String> getWords() {
        return this.words;
    }


    public List<String> initWords(String filePath) {
        String path = filePath;
        try {
            List<String> wordList = getAllInDictionaryFile(path);
            List<String> selectedWords = selectRandomWords(wordList, 7, 10);

            return selectedWords;
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("정상적으로 사전 내 단어를 불러오지 못하였습니다.");
    }

    public static List<String> getAllInDictionaryFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    private List<String> selectRandomWords(List<String> wordList, int min, int max) {
        Random random = new Random();
        List<String> selectedWords = new ArrayList<>();
        int numberOfWords = random.nextInt(max - min + 1) + min;

        while (selectedWords.size() < numberOfWords) {
            Collections.shuffle(wordList);
            String potentialWord = wordList.get(0); // 셔플된 리스트의 첫 번째 단어를 선택

            if (isValidWord(potentialWord) && !selectedWords.contains(potentialWord)) { // 특수문자미포함 and 선택되지 않은 단어
                selectedWords.add(potentialWord);
            }
        }

        return selectedWords;
    }

    public List<String> getNonRandomWords(String filePath) throws IOException {
        List<String> wordList = getAllInDictionaryFile(filePath);

        ArrayList<String> filteredWordList = new ArrayList<>();

        for (String word : wordList) {
            if (isValidWord(word)) {
                // filter
                filteredWordList.add(word);
            }
        }

        return filteredWordList;
    }

    public static boolean isValidWord(String word) {
        // 단어가 특수 문자를 포함하고 있는지 검사
        return word.matches("^[a-zA-Z]+$"); // 영문자만 포함된 단어는 true 반환
    }
}
