package pers.xiaofeng.algorithm.dynamicProgramming;

/**
 * @author: 廿柒
 * @description: 多重背包:购买次数是有限制的，现用一个num数组规定限制
 * @date: Created in 2020/7/31 10:34
 */
public class MoreKnapsack {

    public static void main(String[] args) {

        int n = 5;                                  // 商品种类数
        int s = 12;                                 // 背包容量
        int[] w = {2, 3, 5, 7, 8};                  // 保存各个商品的体积
        int[] v = {3, 4, 6, 8, 10};                 // 保存各个商品的价值
        int[] num = {1, 2, 3, 4, 5};                // 购买商品的最多次数

        moreKnapsackProblem(w, v, n, s, num);

    }

    /**
     * @param w   商品体积
     * @param v   商品价值
     * @param n   商品数量
     * @param s   背包容量
     * @param num 商品限制购买次数
     */
    public static void moreKnapsackProblem(int[] w, int[] v, int n, int s, int[] num) {

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
                    // 多重背包需要知道当前商品最多购买的次数
                    // num[i - 1] 规定此商品最多拿多少
                    // j / w[i -1] 背包最多拿多少
                    int maxNumber = Math.min(num[i - 1], j / w[i - 1]);
                    for (int k = 0; k < maxNumber + 1; k++) {
                        if (dp[i - 1][j] < dp[i - 1][j - (k * (w[i - 1]))] + (k * (v[i - 1]))) {
                            dp[i][j] = dp[i - 1][j - (k * (w[i - 1]))] + (k * (v[i - 1]));
                            path[i][j] = 1;
                        } else
                            dp[i][j] = dp[i - 1][j];
                    }
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
        System.out.println("《=====================我是分割线======================》");

        // 打印路径矩形
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= s; j++) {
                System.out.print(path[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("《=====================我还是分割线======================》");

        // int i = path.length - 1;
        // int j = path[0].length - 1;
        // while (i > 0 && j > 0) {
        //     if (path[i][j] == 1) {
        //         // 放入背包一件商品，背包容量对应减少
        //         // 不可能说容量一直为10，如果不减少，那么路径就会重复
        //         System.out.println("物品" + i + "放入背包");
        //         if ((j - w[i - 1]) >= w[i - 1]) {
        //             System.out.println("物品" + i + "放入背包");
        //             j -= w[i - 1];
        //         }
        //         j -= w[i - 1];
        //     }
        //     if (i == 1) {
        //         i = 1;
        //     } else {
        //         i--;
        //     }
        // }
    }
}
