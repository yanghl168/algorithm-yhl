package class03;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * @Author: yanghl
 * @CreateTime: 2023-01-09  21:42
 * @Description: HashMap 与 TreeMap
 */

public class HashMapAndSortedMap {
    public static class Node{
        public int value;

        public Node(int value){
            this.value = value;
        }
    }

    public static void main(String[] args) {
        /**
         * 当-128<变量<127 == 比较的是值
         * 当变量大于127 比较的是地址
         * HashMap 如果Key是基本数据类型, 存的是值
         *         如果Key是自己定义的类型, 存的是地址
         */
        HashMap<Integer, String> hashMap = new HashMap<>();
        Integer a1 = -128;
        Integer a2 = -128;
        Integer a = 178374832;
        Integer b = 178374832;
        hashMap.put(a, "a");
        System.out.println(a1==a2);
        System.out.println(a==b);
        System.out.println(hashMap.containsKey(b));
        Node node1 = new Node(2);
        Node node2 = new Node(2);
        HashMap<Node, String> hashMap1 = new HashMap<>();
        hashMap1.put(node1, "node1");
        System.out.println(hashMap1.containsKey(node2));


        System.out.println("==================treeMap===================");
        /**
         * treeMap 具有排序功能, 但如果Key为自己定义的类型，需要提供比较器进行比较，不然会报错
         */
        TreeMap<Integer, Object> treeMap = new TreeMap<>();
        treeMap.put(1, "1");
        treeMap.put(9, "1");
        treeMap.put(3, "1");
        treeMap.put(6, "1");
        treeMap.put(4, "1");

        System.out.println(treeMap.floorKey(5)); // key<=5  最近的key
        System.out.println(treeMap.ceilingKey(5));// key >=5 最近的key

        TreeMap<Node, String> treeMap1 = new TreeMap<>();
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        // 会报错， 因为没有提供比较器
        treeMap1.put(node3, "node3");
        treeMap1.put(node4, "node4");


    }


}
