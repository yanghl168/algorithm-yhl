package class11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author yanghl
 * @Description: 二叉树的序列化与反序列化
 *     二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
 *     以下代码全部实现了。
 *     但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
 *     因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
 *     比如如下两棵树
 *             __2
 *            /
 *           1
 *            和
 *            1__
 *             \
 *              2
 *      补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
 * @date 2023/3/28 10:21
 */
public class Code02SerializeAndReconstructTree {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 使用前序遍历进行序列化
     * @param head
     * @return
     */
    public static Queue<String> preSerial(Node head){
        Queue<String> ret = new LinkedList<>();
        pres(head, ret);
        return ret;
    }

    public static void pres(Node head, Queue<String> queue){
        if (head == null){
            // 队列允许null
            queue.add(null);
        }else {
            queue.add(String.valueOf(head.value));
            pres(head.left, queue);
            pres(head.right, queue);
        }
    }

    /**
     * 使用前序遍历进行序列化后的反序列化
     * @param preList
     * @return
     */
    public static Node buildByPreQueue(Queue<String> preList){
        if (preList == null || preList.size() == 0){
            return null;
        }
        return preB(preList);
    }

    private static Node preB(Queue<String> preList) {
        String value = preList.poll();
        if (value == null){
            return null;
        }
        Node head = new Node(Integer.parseInt(value));
        head.left = preB(preList);
        head.right = preB(preList);
        return head;
    }


    /**
     * 使用后序遍历进行序列化
     * @param head
     * @return
     */
    public static Queue<String> posSerial(Node head){
        Queue<String> ret = new LinkedList<>();
        posS(head, ret);
        return ret;
    }

    private static void posS(Node head, Queue<String> ret) {
        if (head == null){
            ret.add(null);
        }else {
            posS(head.left,ret);
            posS(head.right,ret);
            ret.add(String.valueOf(head.value));
        }
    }

    /**
     * 使用后序遍历进行序列化后的反序列化
     * @param posList
     * @return
     */
    public static Node buildByPosQueue(Queue<String> posList){
        if (posList == null || posList.size() == 0){
            return null;
        }
        // 把 posList 改成 根结点在前面的序列化顺序，便于反序列化出来: 中左右或中右左
        Stack<String> stack = new Stack<>();
        while (!posList.isEmpty()){
            stack.push(posList.poll());
        }
        // stack 的顺序为: 左右中; 弹出的顺序为: 中右左
        return posB(stack);
    }

    private static Node posB(Stack<String> stack) {
        // stack 的顺序为: 左右中; 弹出的顺序为: 中右左，所以构建时先右再左
        String value = stack.pop();
        if (value == null){
            return null;
        }
        Node head = new Node(Integer.parseInt(value));
        head.right = posB(stack);
        head.left = posB(stack);
        return head;
    }

    /**
     * 按层序序列化二叉树
     * @param head
     * @return
     */
    public static Queue<String> levelSerial(Node head){
        Queue<String> ans = new LinkedList<>();
        if (head == null){
            ans.add(null);
        }else {
            ans.add(String.valueOf(head.value));
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()){
                head = queue.poll();
                if (head.left != null){
                    ans.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                }else {
                    ans.add(null);
                }
                if (head.right != null){
                    ans.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                }else {
                    ans.add(null);
                }
            }
        }
        return ans;
    }

    public static Node buildByLevelQueue(Queue<String> levelList){
        if (levelList == null || levelList.size() == 0){
            return null;
        }
        String value = levelList.poll();
        Node head = generateNode(value);
        Queue<Node> queue = new LinkedList<>();
        if (head != null){
            queue.add(head);
        }
        Node node = null;
        while (!queue.isEmpty()){
            node = queue.poll();
            node.left = generateNode(levelList.poll());
            node.right = generateNode(levelList.poll());
            if (node.left != null){
                queue.add(node.left);
            }
            if (node.right != null){
                queue.add(node.right);
            }
        }
        return head;
    }

    public static Node generateNode(String value){
        if (value == null){
            return null;
        }else {
            return new Node(Integer.parseInt(value));
        }
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
    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    // for test
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<String> pre = preSerial(head);
            Queue<String> pos = posSerial(head);
            Queue<String> level = levelSerial(head);
            Node preBuild = buildByPreQueue(pre);
            Node posBuild = buildByPosQueue(pos);
            Node levelBuild = buildByLevelQueue(level);
            if (!isSameValueStructure(preBuild, posBuild)) {
                System.out.println("preBuild-posBuild-Oops!");
            }
            if (!isSameValueStructure(posBuild, levelBuild)) {
                System.out.println("levelBuild-Oops!");
            }
        }
        System.out.println("test finish!");

    }


}
