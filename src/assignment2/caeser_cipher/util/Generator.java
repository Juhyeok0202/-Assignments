package assignment2.caeser_cipher.util;

public class Generator {

    public static int generateRandomShiftNumber(int end) {
        return (int) (Math.random() * end+1);
    }

    //TODO: n의 범위를 start,end 설정해서 받을 수 있는 메소드로 리팩토링 for analysis
}

