package pers.xiaofeng.algorithm.genetic;

/**
 * @className: pers.xiaofeng.algorithm.genetic.GeneticAlgorithmTest
 * @description: 遗传算法测试类
 * @author: xiaofeng
 * @create: 2021-04-10 13:17
 */
public class GeneticAlgorithmTest extends GeneticAlgorithm {

    // 该种群的基因长度
    private int geneSize;

    // 该基因长度对应的二进制最大值为 1 << geneSize
    private int maxNumOfGene;

    public GeneticAlgorithmTest(int geneSize) {
        super(geneSize);
        this.geneSize = geneSize;
        maxNumOfGene = 1 << geneSize;
    }

    @Override
    public double changeX(Chromosome chrome) {
        // TODO Auto-generated method stub
        return ((1.0 * chrome.getNum() / maxNumOfGene) * 100) + 6;
    }

    @Override
    public double calculateY(double x) {
        // TODO Auto-generated method stub
        return 100 - Math.log(x);
    }

    public static void main(String[] args) {
        GeneticAlgorithmTest test = new GeneticAlgorithmTest(24);
        test.calculate();
    }
}
