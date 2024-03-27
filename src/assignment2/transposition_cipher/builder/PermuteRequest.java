package assignment2.transposition_cipher.builder;

import java.util.List;

public class PermuteRequest {
    public List<String> result;
    public int[] output ;
    public int[] numbers;
    public boolean[] visited;
    public int depth;
    public int r;
    public int n;





    public PermuteRequest(List<String> result, int[] output, int[] numbers, boolean[] visited, int depth, int r, int n) {
        this.result = result;
        this.output = output;
        this.numbers = numbers;
        this.visited = visited;
        this.depth = depth;
        this.r = r;
        this.n = n;
    }
}
