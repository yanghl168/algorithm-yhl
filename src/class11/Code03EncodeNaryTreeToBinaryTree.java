package class11;

import javax.swing.event.TreeWillExpandListener;
import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanghl
 * @Description: 本题测试链接：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
 * @date 2023/4/1 11:32
 */
public class Code03EncodeNaryTreeToBinaryTree {
    // 提交时不要提交这个类
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    // 提交时不要提交这个类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Codec {
        /**
         * 把一个多叉树加工成二叉树
         * @param root
         * @return
         */
        public TreeNode encode(Node root){
            if (root == null){
                return null;
            }
            TreeNode treeNode = new TreeNode(root.val);
            treeNode.left = en(root.children);
            return treeNode;
        }

        private TreeNode en(List<Node> children) {
            // 用于返回
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child: children) {
                TreeNode treeNode = new TreeNode(child.val);
                if (head == null){
                    head = treeNode;
                }else {
                    cur.right = treeNode;
                }
                cur = treeNode;
                cur.left = en(child.children);
            }
            return head;
        }

        /**
         * 把转化后的二叉树转换回多叉树
         * @param root
         * @return
         */
        public Node deCode(TreeNode root){
           if (root == null){
               return null;
           }
            return new Node(root.val, de(root.left));
        }

        public List<Node> de(TreeNode root){
            ArrayList<Node> children = new ArrayList<>();
            while (root != null){
                Node cur = new Node(root.val, de(root.left));
                children.add(cur);
                root = root.right;
            }
            return children;
        }
    }
}
