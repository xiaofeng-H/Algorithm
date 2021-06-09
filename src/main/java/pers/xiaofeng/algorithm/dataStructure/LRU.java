package pers.xiaofeng.algorithm.dataStructure;

import java.util.HashMap;

/**
 * @className: pers.xiaofeng.algorithm.binaryTree.traverse
 * @description: LRU 的全称是 Least Recently Used，也就是说我们认为最近使⽤过的数据应该是是「有⽤的」，很久都 没⽤过的数据应该是⽆⽤的，
 * 内存满了就优先删那些很久没⽤过的数据。
 * @author: xiaofeng
 * @create: 2021-04-17 16:48
 */
public class LRU {

    public static void main(String[] args) {
        LRU lru = new LRU(3);
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        lru.put(4, 4);
        lru.put(5, 5);
        lru.print();
        System.out.println("Least recently used node is " + lru.getLeastRecentlyUsed());
        System.out.println("Least recently unused node is " + lru.getLeastRecentlyUnUsed());
        lru.get(3);
        lru.print();
        System.out.println("Least recently used node is " + lru.getLeastRecentlyUsed());
        System.out.println("Least recently unused node is " + lru.getLeastRecentlyUnUsed());
    }

    // key -> Node(key, val)
    private HashMap<Integer, Node> map;
    // Node(k1, v1) <-> Node(k2, v2)...
    private DoubleList cache;
    // 最⼤容量
    private int cap;

    public LRU(int capacity) {
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        int val = map.get(key).val;
        // 利⽤ put ⽅法把该数据提前
        put(key, val);
        return val;
    }

    public void put(int key, int val) {
        // 先把新节点 x 做出来
        Node x = new Node(key, val);
        if (map.containsKey(key)) {
            // 删除旧的节点，新的插到头部
            cache.remove(map.get(key));
        } else if (cap == cache.size()) {
            // 删除链表最后⼀个数据
            Node last = cache.removeLast();
            map.remove(last.key);
        }
        // 直接添加到头部
        cache.addFirst(x);
        // 更新 map 中对应的数据
        map.put(key, x);
    }

    // 获取最近使用的结点
    public Node getLeastRecentlyUsed() {
        return cache.getHead().next;
    }

    // 获取最久未使用的结点
    public Node getLeastRecentlyUnUsed() {
        return cache.getTail().prev;
    }

    // 打印双链表结点
    public void print() {
        cache.print();
    }
}

// 双链表的节点类
class Node {
    public int key, val;
    public Node next, prev;

    public Node(int k, int v) {
        this.key = k;
        this.val = v;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", val=" + val +
                '}';
    }
}

// 构建⼀个双链表
class DoubleList {
    // 头结点
    private Node head;
    // 尾结点
    private Node tail;

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    // 构造一个只包含头尾结点的双链表
    public DoubleList() {
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        this.head.prev = tail;
        this.head.next = tail;
        this.tail.prev = head;
        this.tail.next = head;
    }

    // 在链表头部添加节点 x，时间 O(1)
    public void addFirst(Node x) {
        x.next = head.next;
        head.next.prev = x;
        head.next = x;
        x.prev = head;
    }

    // 删除链表中的 x 节点（x ⼀定存在）
    // 由于是双链表且给的是⽬标 Node 节点，时间 O(1)
    public void remove(Node x) {
        Node p = head.next;
        while (p != tail) {
            // 找到则删除
            if (p.key == x.key) {
                p.prev.next = p.next;
                p.next.prev = p.prev;
                // 删除则跳出循环
                break;
            } else {
                p = p.next;
            }
        }
    }

    // 删除链表中最后⼀个节点，并返回该节点，时间 O(1)
    public Node removeLast() {
        Node last = tail.prev;
        last.prev.next = tail;
        tail.prev = last.prev;
        return last;
    }

    // 返回链表⻓度，时间 O(1)
    public int size() {
        int size = 0;
        Node p = head;
        while (p.next != head.prev) {
            ++size;
            p = p.next;
        }
        return size;
    }

    // 打印双链表
    public void print() {
        Node p = head.next;
        System.out.println("================打印双向链表start==================");
        while (p != tail) {
            System.out.println(p);
            p = p.next;
        }
        System.out.println("================打印双向链表end==================");

    }
}
