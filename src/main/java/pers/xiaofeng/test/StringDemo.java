package pers.xiaofeng.test;


import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @description：
 * @author：xiaofeng
 * @date：2020/11/17/11:53
 */
public class StringDemo {
    public static void main(String[] args) {
    }

    @BeforeTest
    private void beforeTest() {
        System.out.println("=====Java String 练习=====");
    }

    @AfterTest
    private void afterTest() {
        System.out.println("=====Java String 练习=====");
    }

    @Test
    private void test1() {
        StringBuilder res = new StringBuilder();
        res.append("{\"key\":\"");
        res.append("key");
        res.append("\",\"value\":\"");
        res.append("value");
        res.append("\"}");
        String s = res.substring(0, res.length() - 1);

        System.out.println(res);
        System.out.println(s);
    }

    @Test
    private static void test2() {
        int a = 2;
        int b = 011;
        String c = Integer.toBinaryString(b);
        int d = Integer.parseInt(c);
        System.out.println(b);
        System.out.println(a & b);
    }

    @Test
    private void test3() {
        String lx = "LeXin";
        String nb = lx;
        lx = "Fengqile";
        System.out.println(nb);
    }
}
