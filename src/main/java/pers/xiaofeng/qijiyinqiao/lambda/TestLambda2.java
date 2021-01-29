package pers.xiaofeng.qijiyinqiao.lambda;

/**
 * @className: pers.xiaofeng.qijiyinqiao.lambda.TestLambda2
 * @description:
 * @author: xiaofeng
 * @create: 2021-01-29 9:50
 */
public class TestLambda2 {

    public static void main(String[] args) {

        ILove love = null;

        // 1、lambda表达式简化
        love = (int a, int b, int c) -> {
            System.out.println("I love you--->" + a);
        };

        // 简化1：去掉参数类型
        love = (a, b, c) -> {
            System.out.println("I love you--->" + a);
        };

        // 简化2：简化括号（当只有一个参数时，包围参数的括号可以去掉）
        love = (a, b, c) -> {
            System.out.println("I love you--->" + a);
        };

        // 简化3：去掉花括号（代码块只有一行时）
        love = (a, b, c) -> System.out.println("I love you--->" + a);

        love.love(520, 502, 250);
    }

    /**
     * 总结：1、lambda表达式只能在有一行代码的情况下才能简化成一行，如果有多行，则用代码块封装；
     *      2、前提是接口是函数式接口；
     *      3、多个参数也可以去掉参数类型，要去掉就都要去掉，必须加上括号
     */
}

/**
 * 函数式接口，只能有一个方法，此时可以使用lambda表达式简化；
 * 如果有多个方法时，则不能使用lambda表达式进行简化
 */
interface ILove {
    void love(int a, int b, int c);
//    void love2(int a);
}

