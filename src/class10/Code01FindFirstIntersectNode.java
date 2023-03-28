package class10;
import java.util.HashSet;

/**
 * @author yanghl
 * @Description: 给定两个可能有环也可能无环的单链表，头节点head1和head2。
 *               请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交，返回null
 *               如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。
 * @date 2023/3/21 21:30
 */
public class Code01FindFirstIntersectNode {
    public static class Node{
        public int value;
        public Node next;
        public Node(int value){
            this.value = value;
        }
    }

    public static Node getIntersectNode(Node head1, Node head2){
        if (head1 == null || head2 == null){
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        // 两个均无环
        if (loop1 == null && loop2 == null){
            return noLoop(head1, head2);
        }else if (loop1 != null && loop2 != null){
            // 两个均有环
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;
    }


    /**
     * 如果链表有环则返回入环结点，无环则返回null
     * @param head
     * @return
     */
    public static Node getLoopNode(Node head){
        if (head == null || head.next == null || head.next.next == null){
            return null;
        }
        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast){
            if (fast.next == null || fast.next.next == null){
                return null;
            }
            slow = slow.next;
            fast  = fast.next.next;
        }
        fast = head;
        while (slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 如果两个链表都无环，返回第一个相交节点，如果不相交，返回null
     * @param head1
     * @param head2
     * @return
     */
    public static Node noLoop(Node head1, Node head2){
        if (head1 == null || head2 == null){
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1.next != null){
            n++;
            cur1  = cur1.next;
        }
        while (cur2.next != null){
            n--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2){
            return null;
        }
        // 长的头结点
        cur1 = n > 0 ? head1 : head2;
        // 短的头结点
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while (n > 0){
            n--;
            cur1 = cur1.next;
        }
        // 找到相交结点
        while (cur1 != cur2){
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 两个有环链表，返回第一个相交节点，如果不相交，返回null
     * @param head1
     * @param head2
     * @return
     */
    public static Node bothLoop(Node head1,Node loop1, Node head2, Node loop2){
        Node cur1 = null;
        Node cur2 = null;
        if (loop1 == loop2){
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1.next != loop1){
                n++;
                cur1 = cur1.next;
            }
            while (cur2.next != loop2){
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n > 0){
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2){
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        }else {
            cur1 = loop1.next;
            while (cur1 != loop1){
                if (cur1 == loop2){
                    return loop1;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }
    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        HashSet<Node> nodeSet = new HashSet<>();
        while (node != null && !nodeSet.contains(node)) {
            nodeSet.add(node);
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }


    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        printLinkedList(head1);
        printLinkedList(head2);
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        printLinkedList(head1);
        printLinkedList(head2);
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        printLinkedList(head1);
        printLinkedList(head2);
        System.out.println(getIntersectNode(head1, head2).value);
    }

}
