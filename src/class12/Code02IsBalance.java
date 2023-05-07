package class12;

/**
 * @author yanghl
 * @Description: 判断是否是平衡二叉树
 *               1. 每个结点的左子树、右子树均是平衡的
 *               2. 每个结点的左右子树高度查不超过 1
 * @date 2023/4/7 9:22
 */
public class Code02IsBalance {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 记录每颗子树的信息
     */
    public static class Info{
        public Boolean isBalance;
        public int height;

        public Info(Boolean isBalance, int height){
            this.isBalance = isBalance;
            this.height = height;
        }
    }

    public static Boolean isBalance(Node head){
        return process(head).isBalance;
    }

    public static Info process(Node node){
        if (node == null){
            return new Info(true, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        Boolean isBalance = true;
        if (!leftInfo.isBalance){
            isBalance = false;
        }
        if (!rightInfo.isBalance){
            isBalance = false;
        }
        if (Math.abs(leftInfo.height - rightInfo.height) > 1){
            isBalance = false;
        }
        return new Info(isBalance, height);
    }
}
