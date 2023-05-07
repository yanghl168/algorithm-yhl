package class12;

import java.util.ArrayList;

/**
 * @author yanghl
 * @Description: 最大BST子树
 *               给定一个二叉树，找到其中最大的二叉搜索树（BST）子树，其中最大指的是子树节点数最多的。
 *               注意: 子树必须包含其所有后代。
 * 在线测试链接 : https://leetcode.com/problems/largest-bst-subtree
 * @date 2023/4/17 9:36
 */
public class Code06MaxSubBSTSize {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            val = value;
        }
    }

    public static int largestBSTSubtree(TreeNode head){
        if (head == null){
            return 0;
        }
        return process(head).maxBsTSubTreeSize;
    }

    public static class Info{
        public int maxBsTSubTreeSize;
        public int allSize;
        public int max;
        public int min;

        public Info(int m, int a, int ma, int mi){
            maxBsTSubTreeSize = m;
            allSize = a;
            max = ma;
            min = mi;
        }
    }

    public static Info process(TreeNode x){
        if (x == null){
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        int max = x.val;
        int min = x.val;
        int allSize = 1;

        if (leftInfo != null){
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            allSize += leftInfo.allSize;
        }
        if (rightInfo != null){
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            allSize += rightInfo.allSize;
        }

        // 记录左边最大BST树
        int p1 = -1;
        if (leftInfo != null){
            p1 = leftInfo.maxBsTSubTreeSize;
        }
        // 记录右边最大BST树
        int p2 = -1;
        if (rightInfo != null){
            p2 = rightInfo.maxBsTSubTreeSize;
        }

        // 记录左右边合并的最大BST树
        int p3 = -1;
        // 判断左右子树是否是BST
        boolean leftBsT = leftInfo == null || leftInfo.maxBsTSubTreeSize == leftInfo.allSize;
        boolean rightBsT = rightInfo == null || rightInfo.maxBsTSubTreeSize == rightInfo.allSize;
        if (leftBsT && rightBsT){
            // 判断左子树的最大值是否小于x, 右子树的最小值是否大于x
            boolean leftMaxLessX = leftInfo == null || leftInfo.max < x.val;
            boolean rightMaxLessX = rightInfo == null || rightInfo.min > x.val;
            if (leftMaxLessX && rightMaxLessX){
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }
        int maxBsTSubTreeSize = Math.max(Math.max(p1, p2), p3);
        return new Info(maxBsTSubTreeSize, allSize, max, min);
    }

    // 为了验证
    // 对数器方法
    public static int right(TreeNode head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(right(head.left), right(head.right));
    }

    // 为了验证
    // 对数器方法
    public static int getBSTSize(TreeNode head) {
        if (head == null) {
            return 0;
        }
        ArrayList<TreeNode> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return 0;
            }
        }
        return arr.size();
    }

    // 为了验证
    // 对数器方法
    public static void in(TreeNode head, ArrayList<TreeNode> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    // 为了验证
    // 对数器方法
    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // 为了验证
    // 对数器方法
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // 为了验证
    // 对数器方法
    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (largestBSTSubtree(head) != right(head)) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }



}
