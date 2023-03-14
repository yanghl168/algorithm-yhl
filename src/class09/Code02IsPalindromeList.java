package class09;

import java.util.Stack;

/**
 * @author yanghl
 * @Description: 判断链表是否是回文
 * @date 2023/3/13 23:02
 */
public class Code02IsPalindromeList {
    public static class Node{
        public int value;
        public Node next;
        public Node(int value){
            this.value = value;
        }
    }

    /**
     * 借助栈实现 空间O(n)
     * @param head
     * @return
     */
    public static boolean isPalindrome1(Node head){
        if (head == null || head.next == null){
            return true;
        }
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null){
            stack.push(cur);
            cur = cur.next;
        }
        cur = head;
        while (!stack.isEmpty()){
            if (stack.pop().value != cur.value){
                return false;
            }
            cur = cur.next;
        }
        return true;
    }

    /**
     * 借助栈 空间O(n)/2
     * @param head
     * @return
     */
    public static boolean isPalindrome2(Node head){
        if (head == null || head.next == null){
            return true;
        }
        Stack<Node> stack = new Stack<>();
        // 先找到中点
        Node mid = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null){
            mid = mid.next;
            fast = fast.next.next;
        }
        mid = mid.next;
        while (mid != null){
            stack.push(mid);
            mid = mid.next;
        }
        while (!stack.isEmpty()){
            if (stack.pop().value != head.value){
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 用有限变量，空间O(1)
     * @param head
     * @return
     */
    public static boolean isPalindrome3(Node head){
        if (head == null || head.next == null){
            return true;
        }
        // 1.先用快慢指针找到中点
        Node n1 = head;
        Node n2 = head;
        while (n2.next != null && n2.next.next != null){
            n1 = n1.next;
            n2 = n2.next.next;
        }
        // 此时n1为中点
        // 2.把n1.next指向空，再把后半部分链表逆序
        n2 = n1.next;
        n1.next = null;
        Node n3 = null;
        while (n2 != null){
            n3 = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = n3;
        }
        // 3.比对两边的数据,此时 n1 在最末尾的结点
        // 先用 n3 保存最后结点的数据
        n3 = n1;
        n2 = head;
        boolean ret = true;
        while (n1 != null && n2 != null){
            if (n1.value != n2.value){
                ret = false;
                break;
            }
            n1 = n1.next;
            n2 = n2.next;
        }
        // 4.恢复链表为原来的样子,在第三步前已经用 n3 保存着最后结点
        n2 = n3.next;
        n3.next = null;
        while (n2 != null){
            n1 = n2.next;
            n2.next = n3;
            n3 = n2;
            n2 = n1;
        }
        return ret;
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {

        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
//        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

    }

}
