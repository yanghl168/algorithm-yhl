package class12;

import java.util.ArrayList;

/**
 * @author yanghl
 * @Description: 判断是否为二叉搜索树
 * @date 2023/4/11 9:18
 */
public class Code03_IsBST {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int value){
            this.value  = value;
        }
    }

    /**
     * 搜索二叉树的中序遍历结果是从小到大排列的
     * @param head
     * @return
     */
    public static Boolean isBST1(Node head){
        if (head == null){
            return true;
        }
        ArrayList<Integer> list = new ArrayList<>();
        in(head, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) <= list.get(i - 1)){
                return false;
            }
        }
        return true;
    }

    public static void in(Node head, ArrayList<Integer> list){
        if (head == null){
            return;
        }
        in(head.left, list);
        list.add(head.value);
        in(head.right, list);
    }

    public static Boolean isBST2(Node head){
        if (head == null){
            return true;
        }
        return process(head).isBST;
    }

    public static class Info{
        public boolean isBST;
        public int min;
        public int max;

        public Info(boolean isBST, int min, int max){
            this.isBST = isBST;
            this.min = min;
            this.max = max;
        }
    }

    public static Info process(Node node){
        if (node == null){
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        boolean isBST = true;
        if (leftInfo != null && !leftInfo.isBST){
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST){
            isBST = false;
        }
        int min = node.value;
        int max = node.value;
        if (leftInfo != null){
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null){
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }
        if (leftInfo != null && leftInfo.max >= node.value){
            isBST = false;
        }
        if (rightInfo != null && rightInfo.min <= node.value){
            isBST = false;
        }
        return new Info(isBST, min, max);
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

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (!isBST1(head).equals(isBST2(head))) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


}
