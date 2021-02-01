package pers.xiaofeng.test;

import com.google.gson.Gson;
import org.testng.annotations.Test;

/**
 * @description：
 * @author：xiaofeng
 * @date：2020/12/28/17:35
 */

public class Base {
    int w, x, y, z;

    public Base(int a, int b) {
        x = a;
        y = b;
    }

    public Base(int a, int b, int c, int d) {
        // assignment x=a, y=b
        // 新建一个对象
        // new Base(a,b);
        // 当前对象
        this(a, b);
        w = d;
        z = c;
    }

    public static void main(String[] args) {
        Base base = new Base(1, 2, 3, 4);
        System.out.println(new Gson().toJson(base));
    }
}

