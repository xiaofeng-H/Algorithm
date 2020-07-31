package pers.xiaofeng.algorithm.dynamicProgramming;

/**
 * @author ：廿柒
 * @description：完全背包：没有限制时，一种商品可以无限买，直到背包容量装满
 * 为什么完全背包和01背包很像？
 * 因为01背包在当前商品可以购买时，实际是通过解决自己的i-1的子问题来解决01背包问题，dp[i-1][j - w[i - 1]]，
 * dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w[i - 1]] + v[i - 1])。
 * 完全背包是自己解决自己的i的子问题，dp[i][j - w[i - 1]]。
 * @date ：Created in 2020/7/31 9:40
 */
public class CompleteKnapsack {

    public static void main(String[] args) {

        int n = 5;                                  // 商品种类数
        int s = 10;                                 // 背包容量
        int[] w = {1, 2, 3, 4, 5};                  // 保存各个商品的体积
        int[] v = {3, 4, 6, 8, 10};                 // 保存各个商品的价值

        completeKnapsackProblem(n, s, w, v);
    }

    /**
     * 求解01背包问题
     *
     * @param n  商品种类数
     * @param s  背包容量
     * @param w  各个商品的体积
     * @param v  各个商品的价值
     */
    public static void completeKnapsackProblem(int n, int s, int[] w, int[] v) {
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
                    if (dp[i - 1][j] < dp[i][j - w[i - 1]] + v[i - 1]) {
                        dp[i][j] = dp[i][j - w[i - 1]] + v[i - 1];
                        path[i][j] = 1;
                    } else
                        dp[i][j] = dp[i - 1][j];
                }
                // 未设置打印路径时的赋值
                // dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w[i - 1]] + v[i - 1]);
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
        System.out.println("《=====================我是分割线======================》");

        // 打印路径矩形
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= s; j++) {
                System.out.print(path[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("《=====================我还是分割线======================》");

        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                // 放入背包一件商品，背包容量对应减少
                // 不可能说容量一直为10，如果不减少，那么路径就会重复
                System.out.println("物品" + i + "放入背包");
                if ((j - w[i-1])>=w[i-1]){
                    System.out.println("物品" + i + "放入背包");
                    j-=w[i-1];
                }
                j -= w[i - 1];
            }
            if (i==1){
                i = 1;
            }else {
                i--;
            }
        }
    }
}
