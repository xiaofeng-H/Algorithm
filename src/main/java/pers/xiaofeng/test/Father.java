package pers.xiaofeng.test;

/**
 * @description：
 * @author：xiaofeng
 * @date：2020/11/11/11:35
 */
public class Father {

    public synchronized void demo1() {
        System.out.println("Father demo1...the thread is : " + Thread.currentThread().getName());
    }

}
