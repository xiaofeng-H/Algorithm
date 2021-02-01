package pers.xiaofeng.test;

import org.testng.annotations.Test;

import java.util.Date;

/**
 * @description：
 * @author：xiaofeng
 * @date：2021/1/5/17:43
 */
public class DateTime {

    @Test
    private void demo(){
        Date date = new Date();
        System.out.println(date);
    }
}
