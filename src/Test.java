import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static assignment2.transposition_cipher.Main.hint;

public class Test {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("PeneusreapplauseunsquelchedgumphionsnobologistgingivitisThroopnonexperiencegamphrel");
        String hint = "Peneus";
        boolean b = sb.toString().startsWith(hint);
        System.out.println(b);
    }
}
