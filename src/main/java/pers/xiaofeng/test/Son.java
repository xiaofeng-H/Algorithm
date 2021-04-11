package pers.xiaofeng.test;

import com.google.gson.Gson;

/**
 * @description：
 * @author：xiaofeng
 * @date：2020/11/11/11:36
 */
public class Son extends Father {
    public String name;
    private int age;

    public Son(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Son() {

    }

    @Override
    public synchronized void demo1() {
        System.out.println("Son demo1...the thread is : " + Thread.currentThread().getName());
        super.demo1();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) {
        Father father = new Son("hanxiaofeng", 22);
        System.out.println(new Gson().toJson(father));
        System.out.println(father.toString());
    }
}
