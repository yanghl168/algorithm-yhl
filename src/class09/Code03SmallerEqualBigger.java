package class09;

/**
 * @author yanghl
 * @Description: 按照给定值将链表分为小于区，等于区，大于区三个部分
 * @date 2023/3/14 21:37
 */
public class Code03SmallerEqualBigger {
    public static class Node{
        public int value;
        public Node next;
        public Node(int value){
            this.value = value;
        }
    }

    public static Node listPartition1(Node head, int pivot){
        if (head == null || head.next == null){
            return head;
        }
        int i = 0;
        Node cur = head;
        while (cur != null){
            i++;
            cur = cur.next;
        }
        Node[] nodes = new Node[i];
        cur = head;
        for (int j = 0; j < nodes.length; j++) {
            nodes[j] = cur;
            cur = cur.next;
        }
        refreshPartition(nodes, pivot);
        for (i = 1; i < nodes.length; i++) {
            nodes[i - 1].next = nodes[i];
        }
        nodes[i - 1].next = null;
        return nodes[0];
    }

    public static void refreshPartition(Node[] nodes, int pivot){
        int small = -1;
        int big = nodes.length;
        int index = 0;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[index].value < pivot){
                swap(nodes, ++small, index++);
            }else if(nodes[index].value == pivot){
                index++;
            }else {
                swap(nodes, --big, index);
            }
        }
    }
    public static void swap(Node[] nodes, int i, int j){
        Node temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }

    public static Node listPartition2(Node head, int pivot){
        if (head == null || head.next == null){
            return head;
        }
        // 小于区头结点
        Node sH = null;
        // 小于区尾结点
        Node sT = null;
        // 等于区头结点
        Node eH = null;
        // 等于区尾结点
        Node eT = null;
        // 大于区头结点
        Node mH = null;
        // 大于区尾结点
        Node mT = null;
        // 保存原链表下一结点
        Node next = null;
        while (head != null){
            // 保存下一结点
            next = head.next;
            // 少了此步骤会让链表形成环形
            head.next = null;
            if (head.value < pivot){
                if (sH == null) {
                    sH = head;
                    sT = head;
                }else {
                    sT.next = head;
                    sT = head;
                }
            }else if (head.value == pivot){
                if (eH == null){
                    eH = head;
                    eT = head;
                }else {
                    eT.next = head;
                    eT = head;
                }
            }else {
                if (mH == null){
                    mH = head;
                    mT = head;
                }else {
                    mT.next = head;
                    mT = head;
                }
            }
            head = next;
        }
        // 把小于区、等于区、大于区连起来
        if (sT != null){
            sT.next = eH;
            eT = eT != null ? eT : sT;
        }
        // 此时如果eT为空，则sT也为空，即只有大于区有数据; 如果eT不为空，则可以直接接大于区的头结点
        if (eT != null){
            eT.next = mH;
        }
        return sH != null ? sH : (eH != null ? eH : mH);
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
        Node head = new Node(7);
        head.next = new Node(9);
        head.next.next = new Node(1);
        head.next.next.next = new Node(8);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(2);
        head.next.next.next.next.next.next = new Node(5);
        printLinkedList(head);
//        head = listPartition1(head, 5);
        head = listPartition2(head, 5);
        printLinkedList(head);

    }
}
