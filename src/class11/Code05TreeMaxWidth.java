package class11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author yanghl
 * @Description: 求二叉树最大宽度
 * @date 2023/4/3 9:16
 */
public class Code05TreeMaxWidth {
    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int value){
            this.value = value;
        }
    }

    /**
     * 借助Map
     * @param head
     * @return
     */
    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue  = new LinkedList<>();
        queue.add(head);
        int max = 0;
        // 记录结点在哪一层
        HashMap<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        // 正在遍历哪一层
        int curLevel = 1;
        // 计算正在遍历的层的结点数
        int curLevelNodes = 0;
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            // 宽度遍历队列里当前遍历到的结点属于哪一层
            Integer curNodeLevel = levelMap.get(cur);
            if (cur.left != null){
                queue.add(cur.left);
                // cur.left属于下一层的结点: curNodeLevel + 1
                levelMap.put(cur.left, curNodeLevel + 1);
            }
            if (cur.right != null){
                queue.add(cur.right);
                // cur.right 属于下一层的结点: curNodeLevel + 1
                levelMap.put(cur.right, curNodeLevel + 1);
            }
            if (curLevel == curNodeLevel){
                curLevelNodes++;
            }else {
                max = Math.max(max, curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
            // 最后还需要做一遍比较: 因为最后进入的是   curLevel == curNodeLevel
            max = Math.max(max, curLevelNodes);
        }
        return max;
    }

    /**
     * 用有限变量
     * @param head
     * @return
     */
    public static int maxWidthNoMap(Node head){
        if (head == null){
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        int max = 0;
        // 当前层的结点数量
        int curCount = 0;
        // 记录当前层的最后一个结点
        Node curEnd = head;
        // 记录下一层的最后一个结点，用于遍历下一层时 指向 当前层的最后一个结点
        Node nextEnd = null;
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            curCount++;
            if (cur.left != null){
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null){
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            if (cur == curEnd){
                max = Math.max(max, curCount);
                curCount = 0;
                // 当前层结束后，把当前层指向下一层的最后结点
                curEnd = nextEnd;
            }
        }
        return max;
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
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }


}
