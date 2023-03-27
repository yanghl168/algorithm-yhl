package class10;

import java.util.Stack;

/**
 * @author yanghl
 * @Description: 二叉树的层序遍历(非递归实现)
 * @date 2023/3/27 9:24
 */
public class Code03UnRecursiveTraversalBT {

    public static class Node{
        int value;
        Node left;
        Node right;

        public Node(int value){
            this.value= value;
        }
    }

    // 先序
    public static void pre(Node head){
        System.out.println("pre-Order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }

    // 中序
    public static void in(Node head){
        System.out.println("in-Order: ");
        if (head != null){
            Node cur = head;
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || cur != null){
                if (cur != null){
                    stack.push(cur);
                    cur = cur.left;
                }else {
                    cur = stack.pop();
                    System.out.print(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    // 后序
    public static void pos(Node head){
        System.out.println("pos-Order: ");
        if (head != null){
            // 原先要把结点压入的栈
            Stack<Node> stack1 = new Stack<>();
            // 用于遍历打印的栈
            Stack<Node> stack2 = new Stack<>();
            stack1.push(head);
            while (!stack1.isEmpty()){
                // stack1的弹出顺序 头 右 左
                head = stack1.pop();
                // stack2的弹出顺序 左 右 头
                stack2.push(head);
                if (head.left != null){
                    stack1.push(head.left);
                }
                if (head.right != null){
                    stack1.push(head.right);
                }
            }
            // 打印栈
            while (!stack2.isEmpty()){
                Node cur = stack2.pop();
                System.out.print(cur.value + " ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos(head);
        System.out.println("========");
    }





}
