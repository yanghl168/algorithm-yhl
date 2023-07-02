package class12;

/**
 * @author yanghl
 * @Description: 判断树是否为满二叉树
 * @date 2023/4/10 9:32
 */
public class Code04IsFull {
    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int value){
            this.value = value;
        }
    }

    /**
     * 左右子树均是二叉树
     * 结点数 = 2 ^ height - 1
     */
    public static class Info1{
        public int height;
        public int nodes;

        public Info1(int height, int nodes){
            this.height = height;
            this.nodes = nodes;
        }
    }

    public static Boolean isFull1(Node head){
        if (head == null){
            return true;
        }
        Info1 info = process01(head);
        return info.nodes == ((1 << info.height) - 1);
    }

    public static Info1 process01(Node node){
        if (node == null){
            return new Info1(0, 0);
        }
        Info1 leftInfo = process01(node.left);
        Info1 rightInfo = process01(node.left);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new Info1(height, nodes);
    }

    /**
     * 收集子树是否是满二叉树
     * 收集子树的高度
     * 左子树是满二叉树 && 右子树是满二叉树 && 左右子树高度一样 ==> 是满二叉树
     */
    public static class Info2{
        public Boolean isFull;
        public int height;

        public Info2(boolean isFull, int height){
            this.isFull = isFull;
            this.height = height;
        }
    }

    public static Boolean isFull2(Node node){
        return process02(node).isFull;
    }

    public static Info2 process02(Node node){
        if (node == null){
            return new Info2(true, 0);
        }
        Info2 leftInfo = process02(node.left);
        Info2 rightInfo = process02(node.left);
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info2(isFull, height);
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
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (!isFull1(head).equals(isFull2(head))) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }



}
