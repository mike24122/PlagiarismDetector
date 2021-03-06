import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    static void p(Object ...o) {System.out.println(Arrays.deepToString(o));};
    private static long solve(int before, int after, int len) {
        //		p(before, after, len);
        if (DP[before][after][len] != -1)
            return DP[before][after][len];
        long ans = 0;
        if (len == 1) {
            if(before == 0 && after == 0)
                ans = 2;
            else
                ans = 1;
        } else {
            if (before == 0) {
                ans += solve(1, after, len-1);
            }
            ans += solve(0, after, len-1);
        }
        DP[before][after][len] = ans;
        return ans;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String S = in.next();
        System.out.println(solution(S));
    }
    static String go(String S) {
        String ans = "";
        boolean wrap = false;
        if (S.charAt(S.length()-1) == 'A' && S.charAt(0) == 'B')
            wrap = true;
        for (int i = 0; i < S.length(); i++) {
            if (i == 0 && wrap) {
                ans += "A";
            } else if (i == S.length() -1 && wrap) {
                ans += "B";
            } else if (i + 1 < S.length() && S.charAt(i) == 'A' && S.charAt(i+1) == 'B') {
                ans += "BA";
                i++;
            } else {
                ans += S.charAt(i);
            }
        }
        return ans;
    }
    static long[][][] DP;
    static long solution(String S) {
        String A = make_swap(S);
        if (A.charAt(A.length()-1) == 'B' && A.charAt(0) == 'A')
            A = "S"+A.substring(1, A.length()-1);
        char[] C = A.toCharArray();
        int N = C.length;
        for (int i = 0; i < N ;i++) {
            if (C[i] == 'A' && C[(i+1)%N] == 'B')
                return 0;
        }
        DP = new long[2][2][N+1];
        for (int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                Arrays.fill(DP[i][j], -1);
        long ans = 1;
        for (int i = 0; i < N; i++) {
            if (C[(i+N-1)%N] != 'S' && C[i] == 'S') {
                int len = 1;
                while (len < N && C[(i+len)%N] == 'S') {
                    len++;
                }
                char before = C[(i+N-1)%N];
                char after = C[(i+len)%N];
                ans *= solve(before == 'A' ? 1 :0, after == 'B' ? 1 :0, len);
            }
        }
        boolean allS = true;
        for (char aC : C)
            if (aC != 'S')
                allS = false;
        if (allS)
            ans = solve(0, 0, N-1) + solve(1, 1, N-1);
        return ans;
    }
    static String make_swap(String S) {
        String A = "";
        for (int i = 0; i < S.length(); i++) {
            if (i +1 < S.length() && S.charAt(i) == 'B' && S.charAt(i+1) == 'A') {
                A += "S";
                i++;
            } else {
                A += S.charAt(i);
            }
        }
        return A;
    }
}
