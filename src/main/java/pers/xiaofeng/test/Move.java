package pers.xiaofeng.test;

/**
 * @description：二进制移位练习
 * @author：晓峰
 * @date：2020/9/8/15:20
 */
public class Move {
    public static void main(String[] args) {
        long deskId = 1197293711314944l;
        long value = 1 << 64 | deskId;
        long desk1 = deskId >> 50;
        String desk2 = Long.toBinaryString(desk1);
        int desk = Integer.parseInt(desk2, 2);
        System.out.println(Long.toBinaryString(value));
        System.out.println(Long.toBinaryString(deskId));
        System.out.println(desk1);
        System.out.println(desk1);
        System.out.println(desk2);
        System.out.println(desk);
    }

}
