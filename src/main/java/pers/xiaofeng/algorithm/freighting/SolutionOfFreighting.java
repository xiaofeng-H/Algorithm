package pers.xiaofeng.algorithm.freighting;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @className: pers.xiaofeng.algorithm.freighting.SolutionOfFreighting
 * @description: 货物装配问题算法初试
 * @author: xiaofeng
 * @create: 2021-04-09 22:09
 */
public class SolutionOfFreighting {
    private static final Gson gson = new Gson();

    // 加权系数ɑ和ρ(ɑ，ρ>0)来构造零散货物配装问题的适应度函数
    private final double a = 0.5;
    private final double p = 0.5;

    // 集合N{1, 2, …, i, …, n}为到站相同的零散货物的集合
    private final int countsOfGoods;

    // 集合g{g1, g2, …, gi, …, gn}为零散货物的重量的集合
    private final double[] weightOfGoods;

    // 集合v{v1, v2, …, vi, …, vn}为零散货物的体积的集合
    private final double[] volumeOfGoods;

    // 零散货物密度/反密度的集合
    private double[] weightToVolume;

    // 集合M={1, 2, …, i, …, m}为载运工具的集合
    private final int countsOfLorry;

    // 集合G{G1, G2, …, Gi, …, Gm}载运工具标记载重的集合
    private final double[] weightOfLorry;

    // 集合{V1, V2, …, Vi, …, Vm}载运工具有效容积的集合
    private final double[] volumeOfLorry;

    // 集合Pi（Pi是N的子集）表示禁止与零散货物i(i∈N)混装的货物的集合
    private int[] P;

    // 集合Q（Q是N的子集）为指令性优先配装货物集合
    private int[] Q;

    // 集合f用来记录种群的适应度
    private double[] fitness;
    private double[] f1;
    private double[] f2;

    // 计算种群适应度所需的货物装载的集合
    private int[][] x;

    // S[i]=j 表示第i号货物装在了第j号货车
    private int[] solution;

    // 所需货车数量
    private int needLorryCounts;

    // 货车已装载的重量和容量
    private double[] sumOfVolume;
    private double[] sumOfWeight;

    public SolutionOfFreighting(double[] weightOfGoods, double[] volumeOfGoods, double[] weightOfLorry, double[] volumeOfLorry) {
        this.weightOfGoods = weightOfGoods;
        this.volumeOfGoods = volumeOfGoods;
        this.weightOfLorry = weightOfLorry;
        this.volumeOfLorry = volumeOfLorry;
        this.countsOfGoods = weightOfGoods.length;
        this.countsOfLorry = weightOfLorry.length;
        this.x = new int[this.countsOfGoods][this.countsOfLorry];
    }

    public static void main(String[] args) {
        double[] g = {1.221, 1.156, 0.7, 1.243, 1.600, 1.612, 2.300, 1.930, 1.850, 1.900};
        double[] v = {1.05, 1.98, 2.00, 3.14, 2.86, 2.17, 4.80, 5.20, 2.30, 3.80};
        double[] G = {5, 10, 15, 20};
        double[] V = {5, 10, 15, 20};
        SolutionOfFreighting solutionOfFreighting = new SolutionOfFreighting(g, v, G, V);
        solutionOfFreighting.calculate();
    }

    private void calculate() {
        initData();
        print();
    }

    private void initData() {
        this.sumOfWeight = new double[countsOfLorry];
        this.sumOfVolume = new double[countsOfLorry];
        this.weightToVolume = new double[countsOfGoods];
        this.solution = new int[countsOfGoods];
        this.needLorryCounts = 0;

        // 数据初始化
        initArray(weightToVolume);
        initArray(solution);
        initArray(x);

        int ng;
        int nv;

        // 零散货物重量总和
        double weightSumOfGoods = sumOfArrays(weightOfGoods);
        // 零散货物体积总和
        double volumeSumOfGoods = sumOfArrays(volumeOfGoods);

        // 标记载重最小的运载工具的载重
        double minOfWeight = 0;
        // 有效容积最小的运载工具的容积
        double minOfVolume = 0;
        for (int i = 0; i < countsOfLorry; i++) {
            if (i == countsOfLorry - 1) {
                break;
            }
            if (weightOfGoods[i] < weightOfGoods[i + 1]) {
                minOfWeight = weightOfGoods[i];
            }

            if (volumeOfLorry[i] < volumeOfLorry[i + 1]) {
                minOfVolume = volumeOfLorry[i];
            }
        }

        // 向上取整
        ng = (int) Math.ceil(weightSumOfGoods / minOfWeight);
        nv = (int) Math.ceil(volumeSumOfGoods / minOfVolume);
        //nm = Math.max(ng, nv);

        // 初始化零散货物的密度/反密度
        for (int i = 0; i < countsOfGoods; i++) {
            if (ng > nv) {
                weightToVolume[i] = weightOfGoods[i] / volumeOfGoods[i];
            } else {
                weightToVolume[i] = volumeOfGoods[i] / weightOfGoods[i];
            }
        }

        System.out.println("排序前的容重比：" + gson.toJson(weightToVolume));
        // 对w进行递减排序得到排序后的货物对应的原始位置
        int[] b1 = bubbleSort(weightToVolume, false);
        System.out.println("排序前的容重比：" + gson.toJson(weightToVolume));

        System.out.println("排序后的货物位置为：" + gson.toJson(b1));

        // 临时记录b1的List，只因List操作更方便
        List<Integer> listTmp = new ArrayList<>();
        for (int k : b1) {
            listTmp.add(k);
        }


        // 假设现有的所有货车可以装下所有的货物
        for (int j = 0; j < countsOfLorry; j++) {   // j表示货车的次序
            // b表示当前还未装载的货物
            int[] b = new int[listTmp.size()];
            for (int i = 0; i < listTmp.size(); i++) {
                b[i] = listTmp.get(i);
            }
            System.out.println("待装货物为：" + gson.toJson(b));

            // 如果货物已经装完，则直接退出循环
            if (b.length == 0) {
                break;
            }

            for (int i = 0; i < b.length; i++) {   // i表示是哪个货物
                if (i % 2 == 0) {
                    if ((sumOfWeight[j] + weightOfGoods[b[i / 2]]) <= weightOfLorry[j] && (sumOfVolume[j] + volumeOfGoods[b[i / 2]]) <= volumeOfLorry[j]) {
                        System.out.println("正在装" + b[i / 2] + "号货物");
                        // 将i号货物的重量和体积累加到j号货车
                        sumOfWeight[j] += weightOfGoods[b[i / 2]];
                        sumOfVolume[j] += volumeOfGoods[b[i / 2]];
                        // 表示第i号货物装入到j货车
                        solution[b[i / 2]] = j;
                        // 记录数组x x[i][j] = 1 表示i号货物装入j号车
                        x[b[i / 2]][j] = 1;
                        // 在当前还未装载的货物中移除第i号货物
                        listTmp.remove(0);
                    }
                } else {
                    if ((sumOfWeight[j] + weightOfGoods[b[b.length - 1 - (i / 2)]]) <= weightOfLorry[j] && (sumOfVolume[j] + volumeOfGoods[b[b.length - 1 - (i / 2)]] <= volumeOfLorry[j])) {
                        System.out.println("正在装" + b[b.length - 1 - (i / 2)] + "号货物");
                        // 将b.length - 1 - i号货物的重量和体积累加到j号货车
                        sumOfWeight[j] += weightOfGoods[b[b.length - 1 - (i / 2)]];
                        sumOfVolume[j] += volumeOfGoods[b[b.length - 1 - (i / 2)]];
                        // 表示第b.length - 1 - (i / 2)号货物装入到j货车
                        solution[b[b.length - 1 - (i / 2)]] = j;
                        // 记录数组x x[i][j] = 1 表示i号货物装入j号车
                        x[b[b.length - 1 - (i / 2)]][j] = 1;
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
            needLorryCounts += 1;
        }

        // 所有货车装载的货物重量总和
        double weightSumOfLorry = sumOfArrays(sumOfWeight);
        // 所有货车装载的货物体积总和
        double volumeSumOfLorry = sumOfArrays(sumOfVolume);

        System.out.println("货物累计重量为：" + weightSumOfGoods + " | 货车已装载的累计重量为：" + weightSumOfLorry);
        System.out.println("货物累计体积为：" + volumeSumOfGoods + " | 货车已装载的累计体积为：" + volumeSumOfLorry);


    }

    /**
     * 计算种群适应度
     */
    private void calculateFitness() {
        
    }

    /**
     * @Description: 输出结果
     */
    private void print() {
        System.out.println("====================Again and again====================");
        System.out.println("\n===>待装货物的重量和体积如下：");
        for (int i = 0; i < weightOfGoods.length; i++) {
            System.out.printf("货物编号：%d \t货物重量：%5.3f \t货物体积：%5.3f\n", i, weightOfGoods[i], volumeOfGoods[i]);
        }

        System.out.println("\n===>货车承载量和已装载货物信息如下：");
        for (int i = 0; i < weightOfLorry.length; i++) {
            System.out.printf("货车编号：%d \t货车载重量：%6.3f \t货车容量：%6.3f \t货车已装重量：%6.3f \t货车已装容量：%6.3f\n",
                    i, weightOfLorry[i], volumeOfLorry[i], sumOfWeight[i], sumOfVolume[i]);
        }

        System.out.println("\n===>总共需要 <" + needLorryCounts + "> 辆货车，配装结果如下：");
        System.out.println(gson.toJson(solution));
        System.out.println("使用矩阵表示为：");
        for (int i = 0; i < countsOfGoods; ++i) {
            for (int j = 0; j < countsOfLorry; ++j) {
                System.out.print(x[i][j] + "\t");
            }
            System.out.println();
        }

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

    private void initArray(int[][] array) {
        for (int i = 0; i < countsOfGoods; ++i) {
            for (int j = 0; j < countsOfLorry; ++j) {
                x[i][j] = 0;
            }
        }
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
