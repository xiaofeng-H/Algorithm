package pers.xiaofeng.algorithm.dynamicProgramming;

import org.testng.annotations.Test;

/**
 * @className: pers.xiaofeng.algorithm.dynamicProgramming.EditDistance
 * @description: 编辑距离问题就是给我们两个字符串 s1 和 s2 ，只能⽤三种操作，让我们把 s1 变成 s2 ，求最少的操作数。
 * @author: xiaofeng
 * @create: 2021-04-23 11:14
 */
public class EditDistance {

    @Test
    private void testEditDistance(){
        String s1 = "intention";
        String s2 = "execution";
        int res = minDistance(s1,s2);
        System.out.println(res);
    }

    private int minDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        // 存储 s1[0..i] 和 s2[0..j] 的最⼩编辑距离
        int[][] dp = new int[m + 1][n + 1];

        // base case
        for (int i = 1; i <= m; i++)
            dp[i][0] = i;
        for (int j = 1; j <= n; j++)
            dp[0][j] = j;

        // ⾃底向上求解
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else {
                    int min = Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                    dp[i][j] = Math.min(min, dp[i - 1][j - 1] + 1);
                }

        // 储存着整个 s1 和 s2 的最⼩编辑距离
        return dp[m][n];
    }
}
