package pers.xiaofeng.algorithm.sort;

/**
 * @description：插入类排序算法的实现
 * @author：晓峰
 * @date：2020/9/15/13:49
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] a = {9, 8, 4, 7, 6, 2, 5, 3, 1, 45, 54, 98, 63, 25, 54, 885, 23687, 15, 2654,
                2656, 31454, -8, -522, 96, 147, 852};
        int[] b = {9, 8, 4, 7, 6, 2, 5, 3, 1};
        System.out.println("排序前的结果为：");
        outPut(a);
        directInsertSort(a);
        halfInsertSort(a);

    }

    /**
     * 直接插入排序（逻辑比较简单，就不做太多的注释）
     *
     * @param a 待排序列
     */
    public static void directInsertSort(int[] a) {
        if (a.length > 0) {
            for (int i = 1; i < a.length; ++i) {
                int j = i;
                while ((j - 1) >= 0) {
                    if (a[j] < a[j - 1]) {
                        int tmp = a[j];
                        a[j] = a[j - 1];
                        a[j - 1] = tmp;
                        --j;
                    } else {
                        --j;
                        continue;
                    }
                }
            }
            System.out.println("直接插入排序后的结果为：");
            outPut(a);
        } else
            System.out.println("进行排序的序列为空，请重新输入！！！");
    }

    /**
     * 折半插入排序
     *
     * @param a 待排序列
     */
    public static void halfInsertSort(int[] a) {
        if (a.length > 0) {
            for (int i = 1; i < a.length; ++i) {
                int j = -1; // 标记位，记录一个比目标值大且最接近目标值的数的数组下标
                int mid = 0;
                int head = 0;
                int tail = i - 1;
                /**
                 * 以下代码用于查找一个最小的且比目标值大的数
                 */
                while (head <= tail) {  // 当head==tail时，也要进行判断
                    mid = (head + tail) / 2;
                    if (a[i] < a[mid]) {
                        // 该循环是找一个比目标值大且最近的数，故只有再目标值小于某一个数的时候做标记
                        j = mid;
                        tail = mid - 1;
                    } else {
                        head = mid + 1;
                    }
                }
                /**
                 * 以下代码用于移动元素操作
                 */
                if (j >= 0) {
                    int tmp = a[i];
                    int k = i;
                    while (k > j) {
                        a[k] = a[k - 1];
                        --k;
                    }
                    a[k] = tmp;
                }
            }
            System.out.println("折半插入排序后的结果为：");
            outPut(a);
        } else
            System.out.println("进行排序的序列为空，请重新输入！！！");
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
