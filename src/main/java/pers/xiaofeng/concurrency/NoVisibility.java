package pers.xiaofeng.concurrency;

/**
 * @className: pers.xiaofeng.concurrency.NoVisibillity
 * @description: 在没有同步的情况下共享变量（不要这么做）
 * @author: xiaofeng
 * @create: 2021-03-10 16:33
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            System.out.println("ready is " + ready);
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
