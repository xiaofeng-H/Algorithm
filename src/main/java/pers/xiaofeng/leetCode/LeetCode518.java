package pers.xiaofeng.leetCode;

import org.testng.annotations.Test;

/**
 * @className: pers.xiaofeng.leetCode.LeetCode518
 * @description: 给定不同面额的硬币和一个总金额。写出函数来计算可以凑成总金额的硬币组合数。假设每一种面额的硬币都有无限个。
 * @author: xiaofeng
 * @create: 2021-04-22 17:28
 */
public class LeetCode518 {

    @Test
    private void test() {
        int amount = 5;
        int[] coins = {1, 2, 5};
        int res = change(amount, coins);
        System.out.println("总共有组合数：" + res);
    }

    /**
     * 完全背包问题
     *
     * @param amount 总金额
     * @param coins  硬币面值
     * @return
     */
    private int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        // base case
        for (int i = 0; i <= n; i++)
            dp[i][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= amount; j++)
                if (j - coins[i - 1] >= 0)
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                else
                    dp[i][j] = dp[i - 1][j];
        }
        return dp[n][amount];
    }

    /**
     * 完全背包问题（优化版：压缩空间）
     * 时间复杂度 O(N*amount)，空间复杂度 O(amount)
     *
     * @param amount 总金额
     * @param coins  硬币面值
     * @return
     */
    private int changeByPress(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        // base case
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= amount; j++)
                if (j - coins[i - 1] >= 0)
                    dp[j] = dp[j] + dp[j - coins[i - 1]];
        }
        return dp[amount];
    }
}
