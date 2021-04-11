package pers.xiaofeng.algorithm.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @className: pers.xiaofeng.algorithm.genetic.GeneticAlgorithm
 * @description: 遗传算法
 * @author: xiaofeng
 * @create: 2021-04-10 13:16
 */
public abstract class GeneticAlgorithm {
    // 种群列表，即个体列表
    private List<Chromosome> population = new ArrayList<>();

    // 种群规模。规模小，收敛快但降低了种群的多样性，N=20-200
    private int popSize = 100;              // 种群数量

    // 编码长度。编码长度取决于问题解的精度，精度越高，编码越长
    private int geneSize;                   // 基因最大长度

    // 终止进化代数。算法运行结束的条件之一，一般取100～1000
    private int maxIterNum = 100;           // 最大迭代次数

    // 变异概率。变异概率太小，则变异操作产生新个体的能力和抑制早熟现象的能力会较差；变异概率过高随机性过大，一般建议取值范围为0.005～0.01
    private double mutationRate = 0.01;     // 基因变异的概率

    // 一次变异最多可以变异的位置个数
    private int maxMutationNum = 3;         // 最大变异步长

    private int generation = 1;             // 当前遗传到第几代

    private double bestScore;               // 最好得分
    private double worstScore;              // 最坏得分
    private double totalScore;              // 总得分
    private double averageScore;            // 平均得分

    private double x;                       // 记录历史种群中最好的X值
    private double y;                       // 记录历史种群中最好的Y值
    private int geneI;                      // x y所在代数

    public GeneticAlgorithm(int geneSize) {
        this.geneSize = geneSize;
    }

    public void calculate() {
        // 初始化种群
        generation = 1;
        init();
        while (generation < maxIterNum) {
            // 种群遗传
            evolve();
            print();
            generation++;
        }
    }

    /**
     * @Description: 输出结果
     */
    private void print() {
        System.out.println("-----------------Again and again---------------");
        System.out.println("the generation is:" + generation);
        System.out.println("the best y is:" + bestScore);
        System.out.println("the worst fitness is:" + worstScore);
        System.out.println("the average fitness is:" + averageScore);
        System.out.println("the total fitness is:" + totalScore);
        System.out.println("geneI:" + geneI + "\tx:" + x + "\ty:" + y);
    }


    /**
     * @Description: 初始化种群
     */
    private void init() {
        population = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            // 随机生成一个个体
            Chromosome chrome = new Chromosome(geneSize);
            population.add(chrome);
        }
        calculateScore();
    }

    /**
     * @Description: 种群进行遗传
     */
    private void evolve() {
        // 新一代种群
        List<Chromosome> childPopulation = new ArrayList<>();

        // 生成下一代种群
        while (childPopulation.size() < popSize) {
            Chromosome p1 = getParentChromosome();
            Chromosome p2 = getParentChromosome();
            List<Chromosome> children = Chromosome.genetic(p1, p2);
            if (children != null) {
                childPopulation.addAll(children);
            }
        }

        // 新种群替换旧种群
        List<Chromosome> t = population;
        population = childPopulation;
        // 释放内存
        t.clear();

        // 新种群基因突变
        mutation();

        // 计算新种群的适应度
        calculateScore();
    }

    /**
     * @return
     * @Description: 轮盘赌法选择可以遗传下一代的染色体
     */
    private Chromosome getParentChromosome() {
        double slice = Math.random() * totalScore;
        double sum = 0;
        for (Chromosome chrome : population) {
            sum += chrome.getScore();
            if (sum > slice && chrome.getScore() >= averageScore) {
                return chrome;
            }
        }
        return null;
    }

    /**
     * @Description: 计算种群适应度
     */
    private void calculateScore() {
        setChromosomeScore(population.get(0));
        bestScore = population.get(0).getScore();
        worstScore = population.get(0).getScore();
        totalScore = 0;
        for (Chromosome chrome : population) {
            setChromosomeScore(chrome);
            if (chrome.getScore() > bestScore) { //设置最好基因值
                bestScore = chrome.getScore();
                if (y < bestScore) {
                    x = changeX(chrome);
                    y = bestScore;
                    geneI = generation;
                }
            }
            if (chrome.getScore() < worstScore) { //设置最坏基因值
                worstScore = chrome.getScore();
            }
            totalScore += chrome.getScore();
        }
        averageScore = totalScore / popSize;
        // 因为精度问题导致的平均值大于最好值，将平均值设置成最好值
        averageScore = Math.min(averageScore, bestScore);
    }

    /**
     * 基因突变
     */
    private void mutation() {
        for (Chromosome chrome : population) {
            if (Math.random() < mutationRate) { //发生基因突变
                // 随机取一个maxMutationNum以内的数作为本次基因突变的步长
                //int mutationNum = (int) (Math.random() * maxMutationNum);
                int mutationNum = new Random().nextInt(maxMutationNum) + 1;
                chrome.mutation(mutationNum);
            }
        }
    }

    /**
     * @param chrome
     * @Description: 设置染色体得分
     */
    private void setChromosomeScore(Chromosome chrome) {
        if (chrome == null) {
            return;
        }
        double x = changeX(chrome);
        double y = calculateY(x);
        chrome.setScore(y);

    }

    /**
     * @param chrome
     * @return
     * @Description: 将二进制转化为对应的X
     */
    public abstract double changeX(Chromosome chrome);


    /**
     * @param x
     * @return
     * @Description: 根据X计算Y值 Y=F(X)
     */
    public abstract double calculateY(double x);

    public void setPopulation(List<Chromosome> population) {
        this.population = population;
    }

    public void setPopSize(int popSize) {
        this.popSize = popSize;
    }

    public void setGeneSize(int geneSize) {
        this.geneSize = geneSize;
    }

    public void setMaxIterNum(int maxIterNum) {
        this.maxIterNum = maxIterNum;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public void setMaxMutationNum(int maxMutationNum) {
        this.maxMutationNum = maxMutationNum;
    }

    public double getBestScore() {
        return bestScore;
    }

    public double getWorstScore() {
        return worstScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
