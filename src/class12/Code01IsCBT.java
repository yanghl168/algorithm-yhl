package class12;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author yanghl
 * @Description: 判断一颗树是否是完全二叉树
 * @date 2023/4/6 9:15
 */
public class Code01IsCBT {
    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int value){
            this.value = value;
        }
    }

    public static Boolean isCBT1(Node head){
        if (head == null){
            return true;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // 是否遇到双孩子结点不全的情况
        Boolean leaf = false;
        Node l;
        Node r;
        while (!queue.isEmpty()){
            head = queue.poll();
            l = head.left;
            r = head.right;

            // 1.遇到了双孩子结点不全而且当前结点不是叶子结点的
            // 2.遇到了右孩子不空，左孩子为空的情况
            if (
                    (leaf && (l != null || r != null))
                    || (l == null && r != null)
            ){
                return false;
            }
            if (l != null){
                queue.add(l);
            }
            if (r != null){
                queue.add(r);
            }
            if (l == null || r == null){
                leaf = true;
            }
        }
        return true;
    }

    public static boolean isCBT2(Node head){
        return process(head).isCbt;
    }

    public static class Info{
        public boolean isFull;
        public boolean isCbt;
        public int height;

        public Info(boolean full, boolean cbt, int h){
            isFull = full;
            isCbt = cbt;
            height = h;
        }
    }

    public static Info process(Node x){
        if (x == null){
            return new Info(true, true, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // 左满右满，高度也相等
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        boolean isCbt = false;
        if (isFull){
            // 如果是满的话，一定是完全
            isCbt = true;
        }else {
            /**
             *  不是满的情况
             *  1.左完全右满, leftHeight = rightHeight + 1
             *  2.左满右满, leftHeight = rightHeight + 1
             *  3.左满右完全, leftHeight = rightHeight
             */
            if (leftInfo.isCbt && rightInfo.isFull && leftInfo.height == rightInfo.height + 1){
                isCbt = true;
            }else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1){
                isCbt = true;
            }else if (leftInfo.isFull && rightInfo.isCbt && leftInfo.height == rightInfo.height){
                isCbt = true;
            }
        }

        return new Info(isFull, isCbt, height);
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }





}
