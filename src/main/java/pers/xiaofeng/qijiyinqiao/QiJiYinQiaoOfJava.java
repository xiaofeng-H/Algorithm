package pers.xiaofeng.qijiyinqiao;

import com.google.gson.Gson;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description：Java的奇技淫巧
 * @author：xiaofeng
 * @date：2020/12/18/15:11
 */
public class QiJiYinQiaoOfJava {

    public static void main(String[] args) {

    }

    @BeforeTest
    private void beforeTest() {
        System.out.println("=====Java的奇技淫巧 君且拭目以待！=====");
    }

    @AfterTest
    private void afterTest() {
        System.out.println("=====Java的奇技淫巧 君可大开眼界否？=====");
    }

    /**
     * 1、虽然Java没有goto，但它确实作为保留关键字。const也是这样。这意味着您无法使用这两个名称定义变量：
     * int goto = 0;
     * int const = 0;
     * 这样的定义是非法的，无法正常编译！
     * <p>
     * 2、Java允许您使用“_”符号定义数字
     */
    @Test
    private void demo_1() {
        int thousand = 1_000;
        double bigValue = 1_000_000.456_555;
        System.out.println("thousand = " + thousand);
        System.out.println("bigValue = " + bigValue);
    }

    /**
     * 1、Double.MIN_VALUE的值是我们无法设定的
     * 为了展示Double.MAX_VALUE结果比预期的效果要完美，提供以下这样的数值：1.7976931348623157E308。
     * 您认为Double.MIN_VALUE会为您带来什么？4.9E-324！好，执行开始 – 结果这个值大于0！
     * Double.MIN_VALUE是返回大于0的最小Double值（最小正数）。如果您想要最小的Double值，则需要使用：-Double.MAX_VALUE。
     * 他们实际上可以更好地命名这些东西，我想知道这样的做法引起了多少人为的错误！
     */
    @Test
    private void demo_2() {
        System.out.println("Double.MAX_VALUE = " + Double.MAX_VALUE);
        System.out.println("Double.MIN_VALUE = " + Double.MIN_VALUE + "(注：实为最小正数)");
        System.out.println("-Double.MIN_VALUE = " + -Double.MIN_VALUE);
    }

    /**
     * 1、Integer对象的缓存值的大小范围是在[-128 127]区间
     * 这意味着当我们在此数值范围内操作时，“==”比较能正常返回结果。但当数值不在此范围，
     * 对象相等的比较是否正常返回结果就很难说了！想象一下，你可以编写单元测试，且一切都正常运行，只要你没有使用足够大的数字，但这可能会导致严重的错误，
     * 所以为了安全 - 提醒：当你经常使用对象数值比较相等时，请使用“.equals()”，而不是依赖于“==”比较相等，除非你非常肯定这种做法没有错误。
     * 2、反射可以（大多数情况）做任何事情
     * 这不应该作为一个Java奇技淫巧的内容，但通过反射，你可以重写final值（大多数时间）并可访问私有字段......但不一定经常是这样。
     * 在我写How to write horrible Java的文章时，当我重写final值得时候，我发现了无法与预期结果一致的问题。Java中的常量，
     * 当final被内联时（通指内联函数），即使你的代码看起来可以正常运行—没有数值情况也会改变，这太不可思议了（查看我的文章了解详情和Stack Overflow答案）。
     */
    @Test
    private void demo_3() throws IllegalAccessException, InterruptedException, NoSuchFieldException {
        Integer ten = Integer.parseInt("10");
        System.out.println(ten == Integer.valueOf(10));
        //this is true


        Integer thousand = Integer.parseInt("1000");
        System.out.println(thousand == Integer.valueOf(1000));
        //this is false

        notSoFinal();
    }

    /**
     * 1、Java标签
     * 使用标签可以让你在处理嵌套循环的时候，继续或中断一个特定的循环……在不同语言环境下，这个有点类似goto
     * 2、For循环的灵活性
     * 您知不知道里面所有条件是可选的吗？你不需要初始化一个变量，你也不需要一个条件停止，你也不需要增加任何东西......如果省略所有内容，
     * 你最终会得到一个有趣的无限循环语法
     */
    @Test
    private void demo_4() {
        outerLoop:
        while (true) {
            System.out.println("I'm the outer loop");
            while (true) {
                System.out.println("I am the inner loop");
                // 以下语句可以结束掉外层循环
                break outerLoop;
            }
        }

        // 可以正常编译并且正常运行，因为它只是一个附加注释的标记为http的循环。这会让那些不熟悉标签的人，使得它变得特别有趣！
        int i = 3;
        http://www.e4developer.com
        while (i > 0) {
            System.out.println("http://www.e4developer.com");
            i--;
            if (i == 1)
                break http;
        }

        // 死循环
        // for (; ; ) {
        //     //Infinite loop!
        // }
    }

    /**
     * 1、Java有初始化程序......以防万一...
     * 2、双括号初始化集合
     * 它在Java中被称为双括号初始化，我从未见过这样的写法，被任何人使用过……难道是因为没有人知道使用它吗？
     * 在发表这篇文章之后，很多读者很快告诉我，这是我们应该避免的一个危险行为！比如应当使用辅助方法List.of()代替。
     * (注意，双括号初始化会派生匿名类。派生的类this可以指向外部的类。通常这不是什么大问题，但在某些情况下使用不当会引起悲剧的问题，
     * 例如在序列化或垃圾回收时，应当注意这个问题)
     */
    @Test
    private void demo_5() {
        Gson gson = new Gson();
        System.out.println("initClass【初始化之前】的value值为：" + InitClass.value);
        InitClass initClass = new InitClass();
        System.out.println("initClass = " + gson.toJson(initClass));
        System.out.println("initClass【初始化之后】的value值为：" + initClass.value);
        System.out.println("initClass【初始化之后】的sum值为：" + initClass.sum);

        Map<String, String> map = new HashMap<String, String>() {{
            put("it", "really");
            put("works", "!");
        }};
        map.put("name", "廿柒");

        Set<String> set = new HashSet<String>() {{
            add("It");
            add("works");
            add("with");
            add("other");
            add("collections");
            add("too");
        }};
        set.add("银鞍照白马 飒沓如流星");

        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("功能", "正常map使用");

        System.out.println("map is " + gson.toJson(map));
        System.out.println("map is " + map);
        System.out.println("stringMap is " + gson.toJson(stringMap));
        System.out.println("stringMap is " + stringMap);
        System.out.println("set is " + gson.toJson(set));
        System.out.println("set is " + set);
    }

    /**
     * Final值的可以放在后面初始化
     * 这是一个小事情，但有些人认为，你在定义它们时时候，必须初始化常量值。实际情况不是这样，您只需要初始化它们一次就够了，你可以使用以下有效代码核对一下
     * 当我们混合初始化代码块和其他构造函数时，这么做会变得棘手。
     */
    @Test
    private void demo_6() {
        final int a;
        int condition = 1;
        if (condition == 1) {
            a = 1;
        } else {
            a = 2;
        }
        System.out.println(a);
    }

    public static void notSoFinal() throws NoSuchFieldException, IllegalAccessException, InterruptedException {

        ExampleClass example = new ExampleClass(10);

        System.out.println("Final value was: " + example.finalValue);

        Field f = example.getClass().getDeclaredField("finalValue");

        Field modifiersField = Field.class.getDeclaredField("modifiers");

        modifiersField.setAccessible(true);

        modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);

        f.setInt(example, 77);

        System.out.println("Final value was: " + example.finalValue);

    }
}

/**
 * 在Java中，您可以写在类加载（静态初始化程序）或构造函数（标准初始化程序）之前运行的代码块
 */
class InitClass {
    // 标准初始化程序：
    int sum = 0;

    {
        for (int i = 0; i < 5; i++) {
            sum += 1;
        }
    }

    // 静态初始化程序：
    static double value = 0;

    static {
        for (int i = 0; i < 3; i++) {
            value += 1;
        }
    }
}

/**
 * 泛型扩展的桥接
 * 尽管存在可疑的实现（类型擦除），但泛型在Java中还是非常强大。我吃惊的是，我们可以允许定义我们需要的泛型类型。看看这个例子：
 * 你特别需要注意你定义的T，这特性会对你非常有用！
 *
 * @param <T>
 */

// public class SomeClass<T extends ClassA & InterfaceB & InterfaceC> {
// }

class SomeClass extends ClassA implements InterfaceB {
}
