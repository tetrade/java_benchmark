package test;

import org.openjdk.jmh.annotations.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyBenchmark {

    public static String randomString(int countWords) {
        Random random = new Random();
        int leftLimit = 65; // letter 'A'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = random.nextInt(10);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < countWords; i++) {
            sb.append(
                    random.ints(leftLimit, rightLimit + 1)
                            .limit(targetStringLength)
                            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            );
            sb.appendCodePoint(32);
        }
        return sb.toString();
    }


    public String lcs(String x, String y, int m, int n){
        if (m == 0 || n == 0) return "";
        if (x.charAt(m - 1) == y.charAt(n - 1)) return lcs(x, y, m - 1, n - 1) + x.charAt(m - 1);
        return Collections.max(List.of(lcs(x, y, m, n - 1), lcs(x, y, m - 1, n)), String.CASE_INSENSITIVE_ORDER);
    }


//  R = O(n * m), T = O(n * m)
    public static String lcs1(String S1, String S2, int m, int n) {
        int[][] LCS_table = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0)
                    LCS_table[i][j] = 0;
                else if (S1.charAt(i - 1) == S2.charAt(j - 1))
                    LCS_table[i][j] = LCS_table[i - 1][j - 1] + 1;
                else
                    LCS_table[i][j] = Math.max(LCS_table[i - 1][j], LCS_table[i][j - 1]);
            }
        }
        StringBuilder s = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
                s.append(S1.charAt(i - 1));
                i--;
                j--;
            }
            else if (LCS_table[i - 1][j] > LCS_table[i][j - 1])
                i--;
            else
                j--;
        }
        return s.reverse().toString();
    }

    @State(Scope.Benchmark)
    public static class Strings {

        @Setup(Level.Invocation)
        public void doSetup(){
            str1 = randomString(4);
            str2 = randomString(4);
        }

        public String str1;
        public String str2;
    }

    @Warmup(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    @Benchmark @Fork(1) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testMethod(Strings s) {
        String l = lcs(s.str1, s.str2, s.str1.length(), s.str2.length());
    }

    @Warmup(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    @Benchmark @Fork(1) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testMethod1(Strings s) {
        String l = lcs1(s.str1, s.str2, s.str1.length(), s.str2.length());
    }
}
