package pers.xiaofeng.algorithm.sort;

/**
 * @description：交换类排序算法的实现
 * @author：晓峰
 * @date：2020/9/17/11:43
 */
public class ExchangeSort {
    public static void main(String[] args) {
        int[] a = {9, 8, 4, 7, 6, 2, 5, 3, 1, 45, 54, 98, 63, 25, 54, 885, 23687, 15, 2654,
                2656, 31454, -8, -522, 96, 147, 852};
        int[] b = {9, 8, 4, 7, 6, 2, 5, 3, 1};
        System.out.println("排序前的结果为：");
        outPut(a);
        bubbleSort(a);
        simpleSelectSort(a);
        wholeQuickSort(a);
    }

    /**
     * 冒泡排序
     * @param a
     */
    public static void bubbleSort(int[] a) {
        for (int i = 0; i < a.length; ++i) {
            boolean flag = false;
            for (int j = 0; j < a.length - i - 1; ++j) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    flag = true;
                }
            }
            if (!flag)
                break;
        }
        System.out.println("冒泡排序后的结果为：");
        outPut(a);
    }

    /**
     * 简单选择排序
     *
     * @param a
     */
    public static void simpleSelectSort(int[] a) {
        if (a.length > 0) {
            for (int i = 0; i < a.length; ++i) {
                for (int j = i + 1; j < a.length; ++j) {
                    if (a[i] > a[j]) {
                        int tmp = a[i];
                        a[i] = a[j];
                        a[j] = tmp;
                    }
                }
            }
            System.out.println("简单选择排序后的结果为：");
            outPut(a);
        } else
            System.out.println("进行排序的序列为空，请重新输入！！！");
    }

    public static void wholeQuickSort(int[] a) {
        int head = 0;
        int tail = a.length - 1;
        boolean flag = quickSort(a,head,tail);
        while (flag){

        }

    }

    /**
     * 快速排序
     * @param a
     * @param head
     * @param tail
     * @return
     */
    public static boolean quickSort(int[] a, int head, int tail) {
        boolean flag = false;
        if (a.length > 0) {
            int value = a[(head + tail) / 2];
            int tmp;
            while (head <= tail){
                while (a[head] < value)
                    ++head;
                while (a[tail] > value)
                    --tail;
                if (head > tail)
                    break;
                tmp = a[head];
                a[head] = a[tail];
                a[tail] = tmp;
                ++head;
                --tail;
                flag = true;
            }
            System.out.println("快速排序后的结果为：");
            outPut(a);
            return flag;
        } else{
            System.out.println("进行排序的序列为空，请重新输入！！！");
            return flag;
        }
    }

    /**
     * 遍历数组且输出数值方法
     *
     * @param a 要遍历的数组
     */
    private static void outPut(int[] a) {
        for (int i : a) {
            System.out.printf("%d\t", i);
        }
        System.out.println("");
    }
}
