package pers.xiaofeng.test;

import org.testng.annotations.Test;

/**
 * @className: pers.xiaofeng.test.Demo_2
 * @description: 继承测试
 * @author: xiaofeng
 * @create: 2021-03-10 10:57
 */
public class Test_Extends {

    @Test
    private void test1() throws Exception {

        Runnable runnable1 = () -> {
            try {
                Father son2 = new Son();
                son2.demo1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Runnable runnable2 = () -> {
            try {
                Father son3 = new Son();
                son3.demo1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread thread1 = new Thread(runnable1, "Thread1");
        Thread thread2 = new Thread(runnable2, "Thread2");
        thread2.start();
        thread1.start();

    }
}
