package class13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author yanghl
 * @Description:  查找二叉树的任意两节点最低公共祖先
 * @date 2023/4/19 9:46
 */
public class Code03lowestAncestor {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node lowestAncestor1(Node head, Node a, Node b){
        if (head == null){
            return null;
        }
        // 二叉树的所有结点与父结点的hash表
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParent(head, parentMap);
        // 存放a结点以及a的所有父结点,有可能a是b的父结点
        HashSet<Node> aSet = new HashSet<>();
        aSet.add(a);

        Node cur = a;
        // 从a开始一直向上找它的父结点，并存放在set里
        while (parentMap.get(cur) != null){
            cur = parentMap.get(cur);
            aSet.add(cur);
        }
        cur = b;
        // 查找a的父结点的set是否有b的父结点, 若找不到则b为head,也是a的父结点，因为a，b不为空
        while (!aSet.contains(cur)){
            cur = parentMap.get(cur);
        }
        return cur;
    }

    /**
     * 建立二叉树的所有结点与父结点的hash表
     * @param head
     * @param parentMap
     */
    public static void fillParent(Node head, HashMap<Node, Node> parentMap){
        if (head.left != null){
            parentMap.put(head.left, head);
            fillParent(head.left, parentMap);
        }
        if (head.right != null){
            parentMap.put(head.right, head);
            fillParent(head.right, parentMap);
        }
    }

    public static Node lowestAncestor2(Node head, Node a, Node b){
        return process(head, a, b).ans;
    }

    public static class Info{
        public boolean findA;
        public boolean findB;
        public Node ans;

        public Info(boolean fa, boolean fb, Node a){
            findA = fa;
            findB = fb;
            ans = a;
        }
    }

    public static Info process(Node x, Node a, Node b){
        if (x == null){
            return new Info(false, false, null);
        }
        Info leftInfo = process(x.left, a, b);
        Info rightInfo = process(x.right, a, b);

        // 左右子树是否找到了a, b结点
        boolean findA = (x == a) || leftInfo.findA || rightInfo.findA;
        boolean findB = (x == b) || leftInfo.findB || rightInfo.findB;
        // 结果
        Node ans = null;
        if (leftInfo.ans != null){
            ans = leftInfo.ans;
        }else if (rightInfo.ans != null){
            ans = rightInfo.ans;
        }else {
            if (findA && findB){
                ans = x;
            }
        }
        return new Info(findA, findB, ans);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // for test
    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
