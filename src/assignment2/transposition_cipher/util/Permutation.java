package assignment2.transposition_cipher.util;

import assignment2.transposition_cipher.builder.PermuteBuilder;
import assignment2.transposition_cipher.builder.PermuteRequest;

import java.util.ArrayList;
import java.util.List;

public class Permutation {

    public static List<String> generatePermutations(int n) {
        List<String> results = new ArrayList<>();

        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = i+1;
        }

        PermuteRequest permRequest = new PermuteBuilder()
                .result(results)
                .output(new int[n])
                .numbers(numbers)
                .visited(new boolean[n])
                .depth(0)
                .r(n)
                .n(n)
                .build();

        permute(permRequest);

        return results;
    }

    public static void permute(PermuteRequest rq) {


        if (rq.depth == rq.r) { // 다 뽑았을 경우
            //TODO
            rq.result.add(addToResult(rq.output,rq.r));
            return;
        }

        for (int i = 0; i < rq.n; ++i) {
            if (rq.visited[i] != true) {
                rq.visited[i] = true;
                rq.output[rq.depth] = rq.numbers[i];
                permute(
                        new PermuteBuilder()
                                .result(rq.result)
                                .output(rq.output)
                                .numbers(rq.numbers)
                                .visited(rq.visited)
                                .depth(rq.depth+1)
                                .r(rq.r)
                                .n(rq.n)
                                .build()
                );
                rq.output[rq.depth] = 0;
                rq.visited[i] = false;

//                permute(result, output, numbers, visited, depth+1, n, r);
            }
        }
    }

    private static String addToResult(int[] output, int r) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < r; i++) {
            sb.append(String.valueOf(output[i]));
        }

        return sb.toString();
    }
}
