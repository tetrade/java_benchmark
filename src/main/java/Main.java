
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    public static String randomString(int countWords) {
        Random random = new Random();
        int leftLimit = 97; // letter 'A'
        int rightLimit = 122; // letter 'z'
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < countWords; i++) {
            int targetStringLength = random.nextInt(10);
            sb.append(
                    random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            );
            sb.appendCodePoint(32);
        }
        return sb.toString();
    }


    public static String lcs(String x, String y, int m, int n){
        if (m == 0 || n == 0) return "";
        System.out.println(x.charAt(m - 1));
        if (x.charAt(m - 1) == y.charAt(n - 1)) return lcs(x, y, m - 1, n - 1) + x.charAt(m - 1);
        return Collections.max(List.of(lcs(x, y, m, n - 1), lcs(x, y, m - 1, n)), String.CASE_INSENSITIVE_ORDER);
    }

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



    public static void main(String[] args) {
        String x = randomString(2);
        String y = randomString(2);
        System.out.print(x);
        System.out.print(y);
        System.out.println(lcs1(x, y, x.length(), y.length()));
    }
}

