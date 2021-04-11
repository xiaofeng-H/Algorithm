package pers.xiaofeng.algorithm.genetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: pers.xiaofeng.algorithm.genetic.Chromosome
 * @description: 基因遗传染色体
 * @author: xiaofeng
 * @create: 2021-04-10 13:14
 */
public class Chromosome {
    private boolean[] gene;     // 基因序列（使用Java基本数据类型布尔值来表示0和1）
    private double score;       // 对应的函数得分

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    /**
     * @param size 随机生成基因序列
     */
    public Chromosome(int size) {
        if (size <= 0) {
            return;
        }
        initGeneSize(size);
        for (int i = 0; i < size; i++) {
            gene[i] = Math.random() >= 0.5;
        }
    }

    /**
     * 生成一个新基因
     */
    public Chromosome() {

    }

    /**
     * @param c
     * @return
     * @Description: 克隆基因
     */
    public static Chromosome clone(final Chromosome c) {
        if (c == null || c.gene == null) {
            return null;
        }
        Chromosome copy = new Chromosome();
        copy.initGeneSize(c.gene.length);
        for (int i = 0; i < c.gene.length; i++) {
            copy.gene[i] = c.gene[i];
        }
        return copy;
    }

    /**
     * @param size
     * @Description: 初始化基因长度
     */
    private void initGeneSize(int size) {
        if (size <= 0) {
            return;
        }
        gene = new boolean[size];
    }


    /**
     * @param p1
     * @param p2
     * @Description: 遗传产生下一代（杂交）
     */
    public static List<Chromosome> genetic(Chromosome p1, Chromosome p2) {
        // 染色体有一个为空，不产生下一代
        if (p1 == null || p2 == null) {
            return null;
        }
        // 染色体有一个没有基因序列，不产生下一代
        if (p1.gene == null || p2.gene == null) {
            return null;
        }
        // 染色体基因序列长度不同，不产生下一代
        if (p1.gene.length != p2.gene.length) {
            return null;
        }

        Chromosome c1 = clone(p1);
        Chromosome c2 = clone(p2);
        // 随机产生交叉互换位置
        int size = c1.gene.length;
        // ((int) (Math.random() * size)) % size:可得到一个[0,size)区间的一个随机数
        int a = ((int) (Math.random() * size)) % size;
        int b = ((int) (Math.random() * size)) % size;
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        // 对位置上的基因进行交叉互换
        for (int i = min; i <= max; i++) {
            boolean t = c1.gene[i];
            c1.gene[i] = c2.gene[i];
            c2.gene[i] = t;
        }
        List<Chromosome> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        return list;
    }

    /**
     * @param num
     * @Description: num个基因位置发生变异（染色体变异）
     */
    public void mutation(int num) {
        //允许变异
        int size = gene.length;
        for (int i = 0; i < num; i++) {
            //寻找变异位置
            int at = ((int) (Math.random() * size)) % size;
            //变异后的值
            boolean bool = !gene[at];
            gene[at] = bool;
        }
    }

    /**
     * @return
     * @Description: 将基因转化为对应的数字
     */
    public int getNum() {
        if (gene == null) {
            return 0;
        }
        int num = 0;
        for (boolean bool : gene) {
            // 逻辑左移 = * 2
            num <<= 1;
            if (bool) {
                num += 1;
            }
        }
        return num;
    }
}