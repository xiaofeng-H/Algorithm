package pers.xiaofeng.algorithm.dynamicProgramming;

import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @author: 廿柒
 * @description: 01背包：每种商品只能买一个
 * @date: Created in 2020/7/30 15:02
 */
public class ZeroOneKnapsack {

    /*
    给你⼀个可装载容量为 W 的背包和 N 个物品，每个物品有重量和价值两个属性。其中第 i 个物品的容量为 w[i] ，价值为 v[i] ，
    现在让你⽤这个背包装物品，最多能装的价值是多少？
    */

    /*
    动态规划四步曲：
    第⼀步：要明确两点，「状态」和「选择」。
    第⼆步：要明确 dp 数组的定义。
    第三步：根据「选择」，思考状态转移的逻辑。
    最后⼀步：把伪码翻译成代码，处理⼀些边界情况。
    */

    public static void main(String[] args) {

        int n = 5;                                  // 商品种类数
        int s = 10;                                 // 背包容量
        int[] w = {1, 2, 3, 4, 5};                  // 保存各个商品的体积
        int[] v = {3, 4, 6, 8, 10};                 // 保存各个商品的价值

        zeroOneKnapsackProblem(n, s, w, v);
    }

    /**
     * 求解01背包问题
     *
     * @param n 商品种类数
     * @param s 背包容量
     * @param w 各个商品的体积
     * @param v 各个商品的价值
     */
    private static void zeroOneKnapsackProblem(int n, int s, int[] w, int[] v) {
        // 第一行为0表示当不存在商品可以装时，背包最大价值只能为0
        // 第一列为0表示当背包的容量为0时，背包最大价值只能为0
        int[][] dp = new int[n + 1][s + 1];         // 记录在可以装前n个商品且背包容量为s时，背包中所装商品的最大价值
        int[][] path = new int[n + 1][s + 1];       // 记录路径

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= s; j++) {
                // 如果背包的容量j小于当前商品的体积，则第i件商品不能装入背包
                if (w[i - 1] > j)
                    dp[i][j] = dp[i - 1][j];
                else {
                    // 装⼊或者不装⼊背包，择优:dp[i][w] = max(dp[i - 1][j - w[i-1]] + v[i-1], dp[i - 1][j]);
                    if (dp[i - 1][j] < dp[i - 1][j - w[i - 1]] + v[i - 1]) {
                        dp[i][j] = dp[i - 1][j - w[i - 1]] + v[i - 1];
                        path[i][j] = 1;
                    } else
                        dp[i][j] = dp[i - 1][j];

                    /*// 未设置打印路径时的赋值
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w[i - 1]] + v[i - 1]);*/
                }
            }
        }

        // 打印最优解
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= s; j++) {
                if (dp[i][j] < 10)
                    System.out.print(dp[i][j] + "  ");
                else
                    System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }

        // 分割线
        System.out.println("=====================我是分割线======================");

        // 打印路径矩形
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= s; j++) {
                System.out.print(path[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("=====================我还是分割线======================");

        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.println("物品" + i + "放入背包");
                // 放入背包一件商品，背包容量对应减少
                // 不可能说容量一直为10，如果不减少，那么路径就会重复
                j -= w[i - 1];
            }
            i--;
        }
    }

    @Test
    public void testPartition() {
        int[] nums = {1, 5, 11, 5};
        //int[] nums = {1, 2, 3, 5};
        boolean res = canPartition(nums);
        System.out.println("Can partition? " + res);
    }

    /**
     * ⼦集切割问题
     * 时间复杂度 O(n*sum)，空间复杂度 O(sum)
     *
     * @param nums
     * @return
     */
    private boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // 和为奇数时，不可能划分成两个和相等的集合
        if (sum % 2 != 0)
            return false;

        int n = nums.length;
        sum /= 2;

        // 未压缩版本-start
        boolean[][] dp = new boolean[n + 1][sum + 1];
        // base case
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        for (int i = 0; i <= sum; i++) {
            dp[0][i] = false;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (j - nums[i - 1] < 0) {
                    // 背包容量不足，不能装入第i个物品
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 装入或者不装入背包
                    dp[i][j] = dp[i - 1][j] | dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        /*// 打印结果
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= sum; ++j) {
                System.out.print(" " + dp[i][j]);
            }
            System.out.println();
        }*/

        return dp[n][sum];
        // 未压缩版本-end

        /*// 压缩版本-start
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;

        for (int i = 0; i < n; i++) {
            for (int j = sum; j >= 0; --j) {
                if (j - nums[i] >= 0) {
                    dp[j] = dp[j] | dp[j - nums[i]];
                }
            }
        }
        return dp[sum];
        // 压缩版本-end*/
    }

}
