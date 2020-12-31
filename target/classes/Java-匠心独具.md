### 简单记忆线程安全的集合类： **喂！SHE！  **

喂是指：vector；S是指：stack；H是指：hashtable；E是指：Eenumeration

### Collection 是对象集合

Collection 有两个子接口 List 和 Set：

List 可以通过下标 (1,2..) 来取得值，值可以重复,而 Set 只能通过游标来取值，并且值是不能重复的

ArrayList ， Vector ， LinkedList 是 List 的实现类

ArrayList 是线程不安全的， Vector 是线程安全的，这两个类底层都是由数组实现的

LinkedList 是线程不安全的，底层是由链表实现的

### Map 是键值对集合

HashTable 和 HashMap 是 Map 的实现类：

HashTable 是线程安全的，不能存储 null 值

HashMap 不是线程安全的，可以存储 null 值

### MVC

模型(model)它是应用程序的主体部分，主要包括业务逻辑模块（web项目中的Action,dao类）和数据模块（pojo类）。模型与数据格式无关，这样一个模型能为多个视图提供数据。由于应用于模型的代码只需写一次就可以被多个视图重用，所以减少了代码的重复性  

视图(view) 用户与之交互的界面、在web中视图一般由jsp,html组成  

控制器(controller)接收来自界面的请求 并交给模型进行处理 在这个过程中控制器不做任何处理只是起到了一个连接的作用。

### 线程--互斥锁

互斥锁指的是只有一个线程可以访问该对象。

采用synchronized修饰符实现的同步机制叫做互斥锁机制，它所获得的锁叫做互斥锁。每个对象都有一个monitor(锁标记)，当线程拥有这个锁标记时才能访问这个资源，没有锁标记便进入锁池。任何一个对象系统都会为其创建一个互斥锁，这个锁是为了分配给线程的，防止打断原子操作。每个对象的锁只能分配给一个线程，因此叫做互斥锁。

通过继承Thread类或实现Runnable接口，只是创建线程的两种方式。

### Java语言提供了很多修饰符，主要分为以下两类：

1、访问修饰符 

  **default** (即默认，什么也不写）: 在同一包内可见，不使用任何修饰符。使用对象：类、接口、变量、方法。

  **private** : 在同一类内可见。使用对象：变量、方法。 **注意：不能修饰类（外部类）**

  **public** : 对所有类可见。使用对象：类、接口、变量、方法

  **protected** : 对同一包内的类和所有子类可见。使用对象：变量、方法。 **注意：不能修饰类（外部类）**。

2、非访问修饰符，包括但不限于：

  **static** 修饰符，用来修饰类方法和类变量。

  **final** 修饰符，用来修饰类、方法和变量，final 修饰的类不能够被继承，修饰的方法不能被继承类重新定义，修饰的变量为常量，是不可修改的。

  **abstract** 修饰符，用来创建抽象类和抽象方法。

  **synchronized** 和 **volatile** 修饰符，主要用于线程的编程。

### Java标识符

Java标识符由数字，字母和下划线（_），美元符号（$）或人民币符号（￥）组成。在Java中是区分大小写的，而且还要求首位不能是数字。最重要的是，Java关键字不能当作Java标识符。

### 面向对象的基本原则

单一职责原则（Single-Resposibility Principle）：一个类，最好只做一件事，只有一个引起它的变化。单一职责原则可以看做是低耦合、高内聚在面向对象原则上的引申，将职责定义为引起变化的原因，以提高内聚性来减少引起变化的原因。
开放封闭原则（Open-Closed principle）：软件实体应该是可扩展的，而不可修改的。也就是，对扩展开放，对修改封闭的。
Liskov替换原则（Liskov-Substituion Principle）：子类必须能够替换其基类。这一思想体现为对继承机制的约束规范，只有子类能够替换基类时，才能保证系统在运行期内识别子类，这是保证继承复用的基础。
依赖倒置原则（Dependecy-Inversion Principle）：依赖于抽象。具体而言就是高层模块不依赖于底层模块，二者都同依赖于抽象；抽象不依赖于具体，具体依赖于抽象。
接口隔离原则（Interface-Segregation Principle）：使用多个小的专门的接口，而不要使用一个大的总接口。

##### tips：

**s(** Single-Resposibility Principle **):** 单一职责原则

**o(** Open-Closed principle **):** 开放封闭原则

**l(** Liskov-Substituion Principle **):** 里氏原则

**i(** Interface-Segregation Principle **):** 接口隔离原则

**d(** Dependecy-Inversion Principle **):** 依赖倒置原则

一个单词：立方体(solid),很好记!!!

### 调用构造方法要先初始化

