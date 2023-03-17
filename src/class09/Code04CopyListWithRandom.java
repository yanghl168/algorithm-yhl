package class09;

import java.util.HashMap;

/**
 * @author yanghl
 * @Description: 复制带随机指针的链表
 * @date 2023/3/17 21:28
 */
public class Code04CopyListWithRandom {
    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * 借助hashmap解法
     * @param head
     * @return
     */
    public static Node copyRandomList1(Node head){
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null){
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        cur = head;
        while (cur != null){
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }

    public static Node copyRandomList2(Node head){
        if (head == null){
            return null;
        }
        Node cur = head;
        Node next = null;
        // 1->2->3
        // 1->1'->2->2'->3->3'
        while (cur != null){
            next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }
        // 设置1‘ 2’ 3‘的random指针
        cur = head;
        Node copy = null;
        while (cur != null){
            copy = cur.next;
            copy.random = cur.random != null ? cur.random.next : null;
            cur = cur.next.next;
        }
        // 把新老链表分离
        cur = head;
        Node ret = cur.next;
        while (cur != null){
            next = cur.next.next;
            copy = cur.next;
            cur.next = next;
            copy.next = next != null ? next.next : null;
            cur = next;
        }
        return ret;
    }
}
