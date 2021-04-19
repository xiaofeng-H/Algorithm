package pers.xiaofeng.test;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: pers.xiaofeng.test.IndexOfList
 * @description: 列表下标
 * @author: xiaofeng
 * @create: 2021-04-19 9:28
 */
public class IndexOfList {
    private static final Gson gson = new Gson();

    private static void test1() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println("初始化List，size = " + list.size());

        list.remove(1);
        System.out.println("remove 1, size = " + list.size());
        System.out.println("list = " + gson.toJson(list));
        System.out.println("list.get(1) = " + list.get(1));
    }

    public static void main(String[] args) {
        test1();
    }
}
