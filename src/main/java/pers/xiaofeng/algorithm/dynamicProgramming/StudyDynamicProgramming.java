package pers.xiaofeng.algorithm.dynamicProgramming;

import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @className: pers.xiaofeng.algorithm.dynamicProgramming.StudyDynamicProgramming
 * @description: 初始动态规划问题
 * @author: xiaofeng
 * @create: 2021-04-20 10:24
 */
public class StudyDynamicProgramming {

    /**
     * 通过斐波那契数列问题明⽩什么是重叠⼦问题（斐波那契数列严格来说不是动态规划问题）使用传统递归。
     * 当N = 46时就已经不会立马出结果了，指数级别真不是吹的
     */
    @Test
    public void FibonacciSequence() {
        int N = 500;
        //int result = recursionOfFibonacciSequence(N);
        //long result = memorandumOfFibonacci(N);
        long result = DPArrayFibonacci(N);
        System.out.printf("The result of Fibonacci Sequence %d is %d.\n", N, result);
    }

    /**
     * 原始递归方法（低效）
     *
     * @param N
     * @return
     */
    private int recursionOfFibonacciSequence(int N) {
        if (N < 1) {
            System.out.println("WARN===>The length of Fibonacci sequence is wrong!!!");
            return -1;
        }

        if (N == 1 || N == 2)
            return 1;

        return recursionOfFibonacciSequence(N - 1) + recursionOfFibonacciSequence(N - 2);
    }

    /**
     * 备忘录解决斐波拉契数列
     *
     * @param N
     * @return
     */
    private long memorandumOfFibonacci(int N) {
        if (N < 1) {
            System.out.println("WARN===>The length of Fibonacci sequence is wrong!!!");
            return -1;
        }

        // 备忘录全初始化为0
        long[] memo = new long[N + 1];

        return helper(memo, N);
    }

    private long helper(long[] memo, int N) {
        // base case
        if (N == 1 || N == 2)
            return 1;

        // 已经计算过
        if (memo[N] != 0)
            return memo[N];

        // 还未计算则计算并记录
        memo[N] = (helper(memo, N - 1) + helper(memo, N - 2));
        return memo[N];
    }

    /**
     * dp 数组的迭代解法
     *
     * @param N
     * @return
     */
    private long DPArrayFibonacci(int N) {
        if (N < 1) {
            System.out.println("WARN===>The length of Fibonacci sequence is wrong!!!");
            return -1;
        }

        /*// 未优化代码
        long[] dp = new long[N + 1];
        // base case
        dp[1] = dp[2] = 1;

        for (int i = 3; i <= N; ++i) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[N];*/

        /*
        根据斐波那契数列 的状态转移⽅程，当前状态只和之前的两个状态有关，其实并不需要那么⻓ 的⼀个 DP table 来存储所有的状态，
        只要想办法存储之前的两个状态就⾏ 了。所以，可以进⼀步优化，把空间复杂度降为 O(1):（大一ACM我好像用的就是这种想法）
         */
        if (N == 1 || N == 2)
            return 1;

        int prev = 1;
        int curr = 1;

        for (int i = 3; i <= N; i++) {
            int sum = prev + curr;
            prev = curr;
            curr = sum;
        }

        return curr;
    }


    @Test
    public void coinChange() {
        int[] coins = {1, 2, 5, 10};
        int amounts = 39;
        int res = DPCoinChange(coins, amounts);
        System.out.println("WARN===>The amount is " + amounts + " and the result is " + res);
    }

    /**
     * 如何列出状态转移⽅程
     *
     * @param coins  可选硬币面值
     * @param amount 目标金额
     * @return 最少需要⼏枚硬币凑出这个⾦额，如果不可能凑出，算法返回 -1
     */
    private int DPCoinChange(int[] coins, int amount) {
        if (amount < 0) {
            System.out.println("The amount is smaller than zero, no result!");
            return -1;
        }

        // dp[i] = x 表⽰，当⽬标⾦额为 i 时，⾄少需要 x 枚硬币。
        int[] dp = new int[amount + 1];
        // 初始化dp表
        /*PS：为啥 dp 数组初始化为 amount + 1 呢，因为凑成 amount ⾦额的硬 币数最多只可能等于 amount （全⽤ 1 元⾯值的硬币），
        所以初始化为 amount + 1 就相当于初始化为正⽆穷，便于后续取最⼩值。*/
        Arrays.fill(dp, amount + 1);

        // base case
        dp[0] = 0;
        for (int i = 0; i < dp.length; ++i) {
            // 内层 for 在求所有⼦问题 + 1 的最⼩值
            for (int coin : coins) {
                // ⼦问题⽆解，跳过
                if (i - coin < 0)
                    continue;
                dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
            }
        }
        return (dp[amount] == amount + 1) ? 0 : dp[amount];
    }


}
