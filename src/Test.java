import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        char[][] cs = new char[10][10];
        char c = 'a';
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cs[i][j] = c++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char[] chars : cs) {
            sb.append(chars);
        }
        list.add(sb.toString());
        System.out.println(list);
    }
}
