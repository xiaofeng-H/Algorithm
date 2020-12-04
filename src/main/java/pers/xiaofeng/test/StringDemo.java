package pers.xiaofeng.test;


/**
 * @description：
 * @author：xiaofeng
 * @date：2020/11/17/11:53
 */
public class StringDemo {
    public static void main(String[] args) {
        test2();
    }

    private void test1(){
        StringBuilder res = new StringBuilder();
        res.append("{\"key\":\"");
        res.append("key");
        res.append("\",\"value\":\"");
        res.append("value");
        res.append("\"}");
        String s = res.substring(0, res.length()-1);

        System.out.println(res);
        System.out.println(s);
    }

    private static void test2(){
        int a = 2;
        int b = 011;
        String  c = Integer.toBinaryString(b);
        int d = Integer.parseInt(c);
        System.out.println(b);
        System.out.println(a & b);
    }
}
