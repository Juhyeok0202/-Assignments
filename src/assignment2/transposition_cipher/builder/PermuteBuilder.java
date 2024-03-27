package assignment2.transposition_cipher.builder;

import java.util.List;

public class PermuteBuilder {
    private List<String> result;
    private int[] output ;
    private int[] numbers;
    private boolean[] visited;
    private int depth;
    private int r;
    private int n;




    //빌더
    public PermuteBuilder result(List<String> result) {
        this.result = result;
        return this;
    }

    public PermuteBuilder output(int[] output) {
        this.output = output;
        return this;
    }

    public PermuteBuilder numbers(int[] numbers) {
        this.numbers = numbers;
        return this;
    }

    public PermuteBuilder visited(boolean[] visited) {
        this.visited = visited;
        return this;
    }

    public PermuteBuilder depth(int depth) {
        this.depth = depth;
        return this;
    }

    public PermuteBuilder r(int r) {
        this.r = r;
        return this;
    }

    public PermuteBuilder n(int n) {
        this.n = n;
        return this;
    }

    public PermuteRequest build() {
        return new PermuteRequest(result, output, numbers, visited, depth, r, n);
    }
}
