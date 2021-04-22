package pers.xiaofeng.algorithm.dynamicProgramming;

import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @className: pers.xiaofeng.algorithm.dynamicProgramming.LongestIncreasingSubsequence
 * @description: 最长递增子序列
 * @author: xiaofeng
 * @create: 2021-04-22 10:01
 */
public class LongestIncreasingSubsequence {
     /*
     最⻓递增⼦序列（Longest Increasing Subsequence，简写 LIS）是⽐较经典的⼀个问题，⽐较容易想到的是动态规划解法，时间复杂度 O(N^2)，
     我们借这个问题来由浅⼊深讲解如何写动态规划。⽐较难想到的是利⽤⼆分查找， 时间复杂度是 O(NlogN)，我们通过⼀种简单的纸牌游戏来辅助理解这种巧妙的解法。
     注意：「⼦序列」和「⼦串」这两个名词的区别，⼦串⼀定是连续的，⽽⼦序列不⼀定是连续的。
     */

    // Q:给定一个无序的整数数组，找到其中最长上升子序列的长度

    // 动态规划的核心设计思想是：数学归纳法
    @Test
    private void testLIS() {
        int[] nums = {1, 4, 3, 4, 2, 5};
        //int res = lengthOfLISByDP(nums);
        int res = lengthOfLISByBinarySearch(nums);
        System.out.println("The Longest Increasing Subsequence`s length is " + res);
    }

    /**
     * 动态规划解决最长递增子序列问题
     * 时间复杂度:O(n^2)
     *
     * @param nums 所求的序列
     * @return
     */
    private int lengthOfLISByDP(int[] nums) {
        if (nums.length == 0) {
            System.out.println("The nums`s length is smaller than zero, no result!");
            return -1;
        }

        // dp[i] 表⽰以 nums[i] 这个数结尾的最⻓递增⼦序列的⻓度
        int[] dp = new int[nums.length];
        // dp 数组全都初始化为 1
        Arrays.fill(dp, 1);

        // base case
        dp[0] = 1;
        int res = 0;

        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    res = Math.max(res, dp[i]);
                }
            }
        }

        return res;
    }


    /*
    最⻓递增⼦序列和⼀种叫做 patience game 的纸牌游戏有关，甚⾄有⼀种排序⽅法就叫做 patience sorting（耐⼼排序）
    */

    /**
     * 动态规划解决最长递增子序列问题
     * 时间复杂度:O(NlogN)
     *
     * @param nums 所求的序列
     * @return
     */
    private int lengthOfLISByBinarySearch(int[] nums) {
        if (nums.length == 0) {
            System.out.println("The nums`s length is smaller than zero, no result!");
            return -1;
        }

        int[] top = new int[nums.length];
        // 牌堆数初始化为 0
        int piles = 0;

        for (int i = 0; i < nums.length; i++) {
            // 要处理的扑克牌
            int poker = nums[i];

            /*搜索左侧边界的二分查找*/
            int left = 0, right = piles;
            while (left < right) {
                int mid = (left + right) / 2;
                if (top[mid] > poker) {
                    right = mid;
                } else if (top[mid] < poker) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            // 没找到合适的牌堆，新建一个堆
            if (left == piles) ++piles;
            // 把这张牌放到牌堆顶
            top[left] = poker;
        }

        // 牌堆数就是 LIS 长度
        return piles;
    }
}
