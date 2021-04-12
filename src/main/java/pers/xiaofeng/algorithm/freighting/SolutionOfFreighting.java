package pers.xiaofeng.algorithm.freighting;

import com.google.gson.Gson;
import pers.xiaofeng.algorithm.genetic.Chromosome;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @className: pers.xiaofeng.algorithm.freighting.SolutionOfFreighting
 * @description: 货物装配问题算法初试
 * @author: xiaofeng
 * @create: 2021-04-09 22:09
 */
public class SolutionOfFreighting {
    private static final Gson gson = new Gson();

    // 集合N{1, 2, …, i, …, n}为到站相同的零散货物的集合
    private int N;

    // 集合g{g1, g2, …, gi, …, gn}为零散货物的重量的集合
    private double[] g;

    // 集合v{v1, v2, …, vi, …, vn}为零散货物的体积的集合
    private double[] v;

    // 零散货物密度/反密度的集合
    private double[] s;

    // 集合M={1, 2, …, i, …, m}为载运工具的集合
    private int M;

    // 集合G{G1, G2, …, Gi, …, Gm}载运工具标记载重的集合
    private double[] G;

    // 集合{V1, V2, …, Vi, …, Vm}载运工具有效容积的集合
    private double[] V;

    // 集合Pi（Pi是N的子集）表示禁止与零散货物i(i∈N)混装的货物的集合
    private int[] P;

    // 集合Q（Q是N的子集）为指令性优先配装货物集合
    private int[] Q;

    // S[i]=j 表示第i号货物装在了第j号货车
    private int[] S;

    // 货车已装载的重量和容量
    private double[] sumOfVolume;
    private double[] sumOfWeight;

    public SolutionOfFreighting(double[] g, double[] v, double[] G, double[] V) {
        this.g = g;
        this.v = v;
        this.G = G;
        this.V = V;
        this.N = g.length;
        this.M = G.length;
    }

    public static void main(String[] args) {
        double[] g = {1.221, 1.156, 0.7, 1.243, 1.600, 1.612, 2.300, 1.930, 1.850, 1.900};
        double[] v = {1.05, 1.98, 2.00, 3.14, 2.86, 2.17, 4.80, 5.20, 2.30, 3.80};
        double[] G = {5, 10, 15, 20};
        double[] V = {5, 10, 15, 20};
        SolutionOfFreighting solutionOfFreighting = new SolutionOfFreighting(g, v, G, V);
        solutionOfFreighting.calculate();
    }

    public void calculate() {
        initData();
        print();
    }

    public void initData() {
        this.sumOfWeight = new double[M];
        this.sumOfVolume = new double[M];
        this.s = new double[N];
        this.S = new int[N];

        // 数据初始化
        initArray(s);
        initArray(S);

        // 若将所有货物都配装，则所需车辆为nm
        int nm = 0;
        int ng;
        int nv;

        // 零散货物重量总和
        double weightSumOfGoods = sumOfArrays(g);
        // 零散货物体积总和
        double volumeSumOfGoods = sumOfArrays(v);

        // 标记载重最小的运载工具的载重
        double minOfWeight = 0;
        // 有效容积最小的运载工具的容积
        double minOfVolume = 0;
        for (int i = 0; i < M; i++) {
            if (i == M - 1) {
                break;
            }
            if (g[i] < g[i + 1]) {
                minOfWeight = g[i];
            }

            if (V[i] < V[i + 1]) {
                minOfVolume = V[i];
            }
        }

        // 向上取整
        ng = (int) Math.ceil(weightSumOfGoods / minOfWeight);
        nv = (int) Math.ceil(volumeSumOfGoods / minOfVolume);
        //nm = Math.max(ng, nv);

        // 初始化零散货物的密度/反密度
        for (int i = 0; i < N; i++) {
            if (ng > nv) {
                s[i] = g[i] / v[i];
            } else {
                s[i] = v[i] / g[i];
            }
        }

        System.out.println("排序前的容重比：" + gson.toJson(s));
        // 对w进行递减排序得到排序后的货物对应的原始位置
        int[] b1 = bubbleSort(s, false);
        System.out.println("排序前的容重比：" + gson.toJson(s));

        System.out.println("排序后的货物位置为：" + gson.toJson(b1));

        // 临时记录b1的List，只因List操作更方便
        List<Integer> listTmp = new ArrayList<>();
        for (int k : b1) {
            listTmp.add(k);
        }


        // 假设现有的所有货车可以装下所有的货物
        for (int j = 0; j < M; j++) {   // j表示货车的次序
            // b表示当前还未装载的货物
            int[] b = new int[listTmp.size()];
            for (int i = 0; i < listTmp.size(); i++) {
                b[i] = listTmp.get(i);
            }
            System.out.println("待装货物为：" + gson.toJson(b));

            for (int i = 0; i < b.length; i++) {   // i表示是哪个货物
                if (i % 2 == 0) {
                    if ((sumOfWeight[j] + g[b[i / 2]]) <= G[j] && (sumOfVolume[j] + v[b[i / 2]]) <= V[j]) {
                        System.out.println("正在装" + b[i / 2] + "号货物");
                        // 将i号货物的重量和体积累加到j号货车
                        sumOfWeight[j] += g[b[i / 2]];
                        sumOfVolume[j] += v[b[i / 2]];
                        // 表示第i号货物装入到j货车
                        S[b[i / 2]] = j;
                        // 在当前还未装载的货物中移除第i号货物
                        listTmp.remove(0);
                    }
                } else {
                    if ((sumOfWeight[j] + g[b[b.length - 1 - (i / 2)]]) <= G[j] && (sumOfVolume[j] + v[b[b.length - 1 - (i / 2)]] <= V[j])) {
                        System.out.println("正在装" + b[b.length - 1 - (i / 2)] + "号货物");
                        // 将b.length - 1 - i号货物的重量和体积累加到j号货车
                        sumOfWeight[j] += g[b[b.length - 1 - (i / 2)]];
                        sumOfVolume[j] += v[b[b.length - 1 - (i / 2)]];
                        // 表示第b.length - 1 - (i / 2)号货物装入到j货车
                        S[b[b.length - 1 - (i / 2)]] = j;
                        // 在当前还未装载的货物中移除第N - 1 - i号货物
                        listTmp.remove(listTmp.size() - 1);
                    }
                }

                /*// 表示第j号车已经装满，开始装下一辆车
                if (sumOfWeight[j] > G[j] || sumOfVolume[j] > V[j]) {
                    break;
                }*/
            }
            // 所需车辆数+1
            nm += 1;
        }

        // 所有货车装载的货物重量总和
        double weightSumOfLorry = sumOfArrays(sumOfWeight);
        // 所有货车装载的货物体积总和
        double volumeSumOfLorry = sumOfArrays(sumOfVolume);

        System.out.println("货物累计重量为：" + weightSumOfGoods + " | 货车已装载的累计重量为：" + weightSumOfLorry);
        System.out.println("货物累计体积为：" + volumeSumOfGoods + " | 货车已装载的累计体积为：" + volumeSumOfLorry);


    }

    /**
     * @Description: 输出结果
     */
    private void print() {
        System.out.println("====================Again and again====================");
        System.out.println("\n===>待装货物的重量和体积如下：");
        for (int i = 0; i < g.length; i++) {
            System.out.println("货物编号：" + i + "\t货物重量：" + g[i] + "\t货物体积：" + v[i]);
        }

        System.out.println("\n===>货车承载量和已装载货物信息如下：");
        for (int i = 0; i < G.length; i++) {
            System.out.printf("货车编号：%d \t货车载重量：%6.3f \t货车容量：%6.3f \t货车已装重量：%6.3f \t货车已装容量：%6.3f\n",
                    i, G[i], V[i], sumOfWeight[i], sumOfVolume[i]);
        }

        System.out.println("\n===>配装结果如下：");
        System.out.println(gson.toJson(S));

    }

    /**
     * 计算一个int数组的和
     *
     * @param array
     * @return
     */
    private double sumOfArrays(double[] array) {
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    private void initArray(int[] array) {
        Arrays.fill(array, -1);
    }

    private void initArray(double[] array) {
        Arrays.fill(array, -1);
    }

    /**
     * 起泡排序
     *
     * @param r          待排序列
     * @param isIncrease 是否递增
     * @return 已排好的序列
     */
    private int[] bubbleSort(double[] r, boolean isIncrease) {
        // 用来记录按照容重比排序后该货物原先的位置
        int[] b1 = new int[r.length];
        // 先对其进行赋值操作，不然如果该位置的值不变的话会让其默认为0
        for (int i = 0; i < b1.length; i++) {
            b1[i] = i;
        }

        // 变量flag用来标记本趟排序是否发生了交换
        byte flag;
        for (int i = r.length - 1; i >= 1; --i) {
            flag = 0;
            for (int j = 1; j <= i; ++j) {
                // 临时变量
                double temp1 = 0;
                int temp2 = 0;
                // 递增排序
                if (isIncrease) {
                    if (r[j - 1] > r[j]) {
                        // 记录排序之后的值
                        temp1 = r[j];
                        r[j] = r[j - 1];
                        r[j - 1] = temp1;

                        // 记录原始位置
                        temp2 = b1[j];
                        b1[j] = b1[j - 1];
                        b1[j - 1] = temp2;

                        flag = 1;
                    }
                } else {
                    // 递减排序
                    if (r[j - 1] < r[j]) {
                        // 记录排序之后的值
                        temp1 = r[j];
                        r[j] = r[j - 1];
                        r[j - 1] = temp1;

                        // 记录原始位置
                        temp2 = b1[j];
                        b1[j] = b1[j - 1];
                        b1[j - 1] = temp2;

                        flag = 1;
                    }
                }
            }

            // 一趟排序过程中没有发生关键字交换，则证明序列有序，排序结束
            if (flag == 0)
                break;
        }

        // 返回排序结果
        return b1;
    }


}
